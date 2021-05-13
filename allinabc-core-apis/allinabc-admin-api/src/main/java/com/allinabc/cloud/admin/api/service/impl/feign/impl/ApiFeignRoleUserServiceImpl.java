package com.allinabc.cloud.admin.api.service.impl.feign.impl;

import com.allinabc.cloud.admin.api.service.api.ApiRoleUserService;
import com.allinabc.cloud.admin.api.service.impl.feign.client.RoleUserServiceApi;
import com.allinabc.cloud.admin.pojo.po.RoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/24/21 11:38 PM
 **/
@Service
public class ApiFeignRoleUserServiceImpl implements ApiRoleUserService {

    @Autowired
    private RoleUserServiceApi roleUserServiceApi;
    @Override
    public void insertBatch(List<RoleUser> roleUserList) {
        roleUserServiceApi.insertBatch(roleUserList);
    }

    @Override
    public void updateBatch(List<RoleUser> roleUserList) {
        roleUserServiceApi.updateBatch(roleUserList);
    }
}
