package com.allinabc.cloud.admin.api.service.impl.feign.impl;

import com.allinabc.cloud.admin.api.service.api.ApiUserService;
import com.allinabc.cloud.admin.api.service.impl.feign.client.UserServiceApi;
import com.allinabc.cloud.admin.pojo.dto.UserDTO;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApiFeignUserServiceImpl implements ApiUserService {

    @Resource
    private UserServiceApi userServiceApi;

    @Override
    public SysUser get(String userId) {
        Result<SysUser> result = userServiceApi.get(userId);
        return null != result && null != result.getData() ? result.getData() : null;
    }

    @Override
    public SysUser getUserByName(String userName) {
        Result<SysUser> result = userServiceApi.getUserByName(userName);
        return null != result && null != result.getData() ? result.getData() : null;
    }

    @Override
    public void updateLoginRecord(UserDTO user) {
        userServiceApi.updateLoginRecord(user);
    }

    @Override
    public List<String> selectUserIdsHasRoles(List<String> roleIds) {
        return null;
    }

    @Override
    public List<String> selectUserIdsInDeparts(List<String> departIds) {
        return null;
    }

}
