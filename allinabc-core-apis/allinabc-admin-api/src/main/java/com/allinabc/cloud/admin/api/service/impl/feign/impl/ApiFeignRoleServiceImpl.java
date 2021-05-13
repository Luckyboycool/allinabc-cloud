package com.allinabc.cloud.admin.api.service.impl.feign.impl;

import com.allinabc.cloud.admin.api.service.api.ApiRoleService;
import com.allinabc.cloud.admin.api.service.impl.feign.client.RoleServiceApi;
import com.allinabc.cloud.admin.pojo.vo.RoleBaseVO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/25/21 1:08 PM
 **/
@Service
public class ApiFeignRoleServiceImpl implements ApiRoleService {
    @Autowired
    private RoleServiceApi roleServiceApi;

    @Override
    public List<RoleBaseVO> getRoleBaseInfo(String userId) {
        Result<List<RoleBaseVO>> roleBaseInfo = roleServiceApi.getRoleBaseInfo(userId);
        return null != roleBaseInfo && null != roleBaseInfo.getData()? roleBaseInfo.getData(): null;
    }

    @Override
    public List<RoleBaseVO> getBatchBaseInfo(List<String> userIds) {
        Result<List<RoleBaseVO>> result = roleServiceApi.getBatchRoleBaseInfo(userIds);
        return null != result && null != result.getData()? result.getData(): null;
    }
}
