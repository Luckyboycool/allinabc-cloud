package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.RoleUserMapper;
import com.allinabc.cloud.admin.mapper.UserMapper;
import com.allinabc.cloud.admin.pojo.dto.UserChangePwd;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.admin.service.UserService;
import com.allinabc.cloud.admin.util.PasswordUtil;
import com.allinabc.cloud.common.core.utils.RandomUtil;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.common.core.utils.constant.UserConstants;
import com.allinabc.cloud.starter.web.util.BeanMergeUtils;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @since 2020-12-16
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends MybatisCommonServiceImpl<UserMapper, SysUser> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleUserMapper sysRoleUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser updateLoginInfo(SysUser entity) {
        Assert.isTrue(!Strings.isNullOrEmpty(entity.getId()), "id is null");
        SysUser old = userMapper.selectById(entity.getId());
        Assert.notNull(old, "user is null with id: " + entity.getId());

        old.setLastLoginIp(entity.getLastLoginIp());
        old.setLastLoginTime(entity.getLastLoginTime());
        old.setUpdatedBy(getCurrentUser().getId());
        old.setUpdateTm(new Date());
        this.update(old.getId(),old);
        return this.selectById(entity.getId());
    }

    @Override
    @Transactional
    public SysUser updateUser(String userId, SysUser entity) {
        Assert.isTrue(!Strings.isNullOrEmpty(userId), "id is null");
        SysUser sysUser = this.selectById(userId);
        Assert.notNull(sysUser, "user is null with id: " + userId);
        BeanMergeUtils.merge(entity, sysUser);
        sysUser.setPassword(PasswordUtil.encryptPassword(sysUser.getUserName(), sysUser.getPassword(), sysUser.getSalt()));
        this.update(sysUser.getId(), sysUser);
        return this.selectById(userId);
    }

    @Override
    @Transactional
    public SysUser updatePassword(String userId, UserChangePwd userPwd) {
        Assert.isTrue(!Strings.isNullOrEmpty(userId), "id is null");
        SysUser sysUser = this.selectById(userId);
        Assert.notNull(sysUser, "user is null with id: " + userId);

        String oldSaltPwd = PasswordUtil.encryptPassword(sysUser.getUserName(), userPwd.getOldPwd(), sysUser.getSalt());
        Assert.isTrue(StringUtils.equals(oldSaltPwd, sysUser.getPassword()), "原密码验证失败");

        sysUser.setSalt(RandomUtil.randomStr(6));
        sysUser.setPassword(PasswordUtil.encryptPassword(sysUser.getUserName(), userPwd.getNewPwd(), sysUser.getSalt()));

        this.save(sysUser);

        return this.selectById(userId);
    }

    @Override
    @Transactional
    public SysUser updateStatus(String userId, String status) {
        Assert.isTrue(!this.checkIsAdmin(userId), "不允许修改超级管理员用户");

        SysUser old = this.selectById(userId);
        Assert.notNull(old, "用户不存在");

        old.setStatus(status);
        old.setUpdateTm(new Date());
        this.save(old);

        return this.selectById(userId);
    }

    @Override
    public SysUser selectByUserName(String userName) {
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("USER_NAME", userName);
        List<SysUser> sysUsers = userMapper.selectByMap(columnMap);
        return null != sysUsers && sysUsers.size() > 0 ? sysUsers.get(0) : null;
    }

    @Override
    public SysUser selectByPhone(String phone) {
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("PHONE", phone);
        List<SysUser> sysUsers = userMapper.selectByMap(columnMap);
        return null != sysUsers && sysUsers.size() > 0 ? sysUsers.get(0) : null;
    }

    @Override
    public SysUser selectByEmail(String email) {
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("EMAIL", email);
        List<SysUser> sysUsers = userMapper.selectByMap(columnMap);
        return null != sysUsers && sysUsers.size() > 0 ? sysUsers.get(0) : null;
    }

    @Override
    public List<SysUser> selectByGroup(String groupId) {
        if (Strings.isNullOrEmpty(groupId)) {
            return null;
        }
        return userMapper.selectByGroup(groupId);

    }

    @Override
    public List<String> selectByRoleIds(String roleIds) {
        if (Strings.isNullOrEmpty(roleIds) || roleIds.split(",").length == 0) {
            return Lists.newArrayList();
        }
        Joiner.on("','").skipNulls().join(roleIds.split(","));

        return sysRoleUserMapper.selectUserIdByRoleIds(Arrays.asList(roleIds.split(",")));
    }

    @Override
    public String checkUserNameUnique(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.select("id").eq("user_name", sysUser.getUserName()).ne("status", "2");
        List<SysUser> list = userMapper.selectList(queryWrapper);
        list = null != list ? list.stream().filter(i -> !StringUtils.equals(sysUser.getId(), i.getId())).collect(Collectors.toList()) : null;

        if (null != list && list.size() > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    @Override
    public String checkPhoneUnique(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.select("id").eq("phone", sysUser.getPhone());
        List<SysUser> list = userMapper.selectList(queryWrapper);
        list = null != list ? list.stream().filter(i -> !StringUtils.equals(sysUser.getId(), i.getId())).collect(Collectors.toList()) : null;

        if (null != list && list.size() > 0) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    @Override
    public String checkEmailUnique(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.select("id").eq("email", sysUser.getPhone());
        List<SysUser> list = userMapper.selectList(queryWrapper);
        list = null != list ? list.stream().filter(i -> !StringUtils.equals(sysUser.getId(), i.getId())).collect(Collectors.toList()) : null;

        if (null != list && list.size() > 0) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    @Override
    public boolean checkIsAdmin(String userId) {
        return StringUtils.equals(Constants.USER_ADMIN, userId);
    }

    @Override
    @Transactional(readOnly = false)
    public void remove(String ids) {
        if (Strings.isNullOrEmpty(ids) || ids.split(",").length == 0) {
            return;
        }
        Joiner.on("','").skipNulls().join(ids.split(","));
        userMapper.updateStatusById("2", Arrays.asList(ids.split(",")));
    }

    @Override
    protected MybatisCommonBaseMapper<SysUser> getRepository() {
        return userMapper;
    }

}
