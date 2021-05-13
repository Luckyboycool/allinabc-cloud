package com.allinabc.cloud.admin.service;

import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.admin.pojo.dto.UserChangePwd;
import com.allinabc.common.mybatis.service.MybatisCommonService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2020-12-16
 */
public interface UserService extends MybatisCommonService<SysUser> {


    /**
     * 更新用户登录状态 只更新最后登录地址以及最后更新时间
     * @param entity
     * @return
     */
    SysUser updateLoginInfo(SysUser entity);

    /**
     * 仅更新用户信息，不更新其它数据
     * @param userId
     * @param entity
     * @return
     */
    SysUser updateUser(String userId, SysUser entity);

    /**
     * 更新用户密码 只更新用户密码
     * @param userId
     * @param userPwd
     * @return
     */
    SysUser updatePassword(String userId, UserChangePwd userPwd);

    /**
     * 更新用户状态 只更新用户状态
     * @param userId
     * @param status
     * @return
     */
    SysUser updateStatus(String userId, String status);

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    SysUser selectByUserName(String userName);

    /**
     * 根据电话号码查询用户
     * @param phone
     * @return
     */
    SysUser selectByPhone(String phone);

    /**
     * 根据Email查询用户
     * @param email
     * @return
     */
    SysUser selectByEmail(String email);

    /**
     * 根据群组查询用户列表
     * @param groupId
     * @return
     */
    List<SysUser> selectByGroup(String groupId);

    /**
     * 根据角色ID获取用户
     * @param roleIds
     * @return
     */
    List<String> selectByRoleIds(String roleIds);

    /**
     * 校验用户名唯一
     * @param sysUser
     * @return
     */
    String checkUserNameUnique(SysUser sysUser);

    /**
     * 校验手机号唯一
     * @param sysUser
     * @return
     */
    String checkPhoneUnique(SysUser sysUser);

    /**
     * 校验Email唯一
     * @param sysUser
     * @return
     */
    String checkEmailUnique(SysUser sysUser);

    /**
     * 校验是否为管理员账号
     * @param userId
     * @return
     */
    boolean checkIsAdmin(String userId);

    /**
     * remove 修改为删除状态
     */
    void remove(String ids);

}
