package com.allinabc.cloud.admin.api.service.api;

import com.allinabc.cloud.admin.pojo.vo.RoleBaseVO;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/25/21 1:00 PM
 **/
public interface ApiRoleService {
    /**
     * 查询角色列表
     * @param userId
     * @return
     */
    List<RoleBaseVO> getRoleBaseInfo(String userId);

    /**
     * 查询多个用户的角色列表
     * @param userIds
     * @return
     */
    List<RoleBaseVO> getBatchBaseInfo(List<String> userIds);
}
