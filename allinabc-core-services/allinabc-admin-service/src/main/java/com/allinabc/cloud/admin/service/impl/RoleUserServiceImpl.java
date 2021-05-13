package com.allinabc.cloud.admin.service.impl;


import cn.hutool.core.lang.Assert;
import com.allinabc.cloud.admin.mapper.RoleUserMapper;
import com.allinabc.cloud.admin.pojo.po.RoleUser;
import com.allinabc.cloud.admin.service.RoleUserService;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class RoleUserServiceImpl extends MybatisCommonServiceImpl<RoleUserMapper, RoleUser> implements RoleUserService {

    @Resource
    private RoleUserMapper roleUserMapper;

    @Override
    protected MybatisCommonBaseMapper<RoleUser> getRepository() {
        return roleUserMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(List<RoleUser> roleUsers) {
        Assert.notEmpty(roleUsers, "List must has element");
        roleUsers = roleUsers.stream().filter(roleUser ->
                StringUtils.isNotEmpty(roleUser.getRoleId()) && StringUtils.isNotEmpty(roleUser.getUserId())
        ).collect(Collectors.toList());

        String userId = roleUsers.stream()
                .map(RoleUser::getUserId).findFirst().get();
        Map<String, Object> map = new HashMap<>(16);
        map.put("USER_ID", userId);
        roleUserMapper.deleteByMap(map);
        roleUserMapper.batchInsert(roleUsers);
    }

    @Override
    public void insertBath(List<RoleUser> roleUserList) {
        roleUserMapper.batchInsert(roleUserList);
    }
}
