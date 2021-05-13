package com.allinabc.cloud.admin.service;


import com.allinabc.cloud.admin.pojo.po.RoleUser;
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
public interface RoleUserService extends MybatisCommonService<RoleUser> {

    /**
     * 更新角色
     * @param roleUsers
     */
    void updateBatch(List<RoleUser> roleUsers);

    /**
     * 批量插入
     * @param roleUserList
     */
    void insertBath(List<RoleUser> roleUserList);
}
