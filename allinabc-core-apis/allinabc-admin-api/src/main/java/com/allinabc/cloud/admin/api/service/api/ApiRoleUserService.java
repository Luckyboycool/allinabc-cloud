package com.allinabc.cloud.admin.api.service.api;

import com.allinabc.cloud.admin.pojo.po.RoleUser;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/24/21 11:01 PM
 **/
public interface ApiRoleUserService {

    void insertBatch(List<RoleUser> roleUserList);

    void updateBatch(List<RoleUser> roleUserList);
}
