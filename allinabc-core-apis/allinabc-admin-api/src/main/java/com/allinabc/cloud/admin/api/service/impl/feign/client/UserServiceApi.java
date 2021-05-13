package com.allinabc.cloud.admin.api.service.impl.feign.client;

import com.allinabc.cloud.admin.pojo.dto.UserDTO;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description 用户服务Api接口
 * @Author wangtaifeng
 * @Date 2020/12/29 16:02
 **/
@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE)
public interface UserServiceApi {

    @GetMapping("/user/get/id/{id}")
    Result<SysUser> get(@PathVariable(value = "id") String userId);

    @GetMapping("/user/get/name/{userName}")
    Result<SysUser> getUserByName(@PathVariable(value="userName") String userName);

    @PutMapping("/user/update/login")
    Result<Void> updateLoginRecord(@RequestBody UserDTO user);

}
