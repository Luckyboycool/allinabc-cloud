package com.allinabc.cloud.authority.factory;

import com.allinabc.cloud.admin.api.service.impl.feign.client.UserServiceApi;
import com.allinabc.cloud.admin.pojo.dto.UserDTO;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.common.core.exception.ApiException;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<UserServiceApi> {

    @Override
    public UserServiceApi create(Throwable throwable) {
        return new UserServiceApi() {
            @Override
            public Result<SysUser> get(String userId) {
                throw new ApiException("system user getUserById");
            }

            @Override
            public Result<SysUser> getUserByName(String userName) {
                throw new ApiException("system user getUserByName");
            }

            @Override
            public Result<Void> updateLoginRecord(UserDTO user) {
                throw new ApiException("system user updateLoginRecord");
            }
        };
    }

}
