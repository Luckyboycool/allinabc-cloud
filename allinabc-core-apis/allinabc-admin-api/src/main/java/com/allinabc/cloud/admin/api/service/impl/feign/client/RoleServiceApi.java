package com.allinabc.cloud.admin.api.service.impl.feign.client;

import com.allinabc.cloud.admin.pojo.vo.RoleBaseVO;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/25/21 1:03 PM
 **/
@FeignClient(value = ServiceNameConstants.ADMIN_SERVICE)
public interface RoleServiceApi {

    /**
     * 查询角色列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/role/get_role_base_info/{userId}", method = RequestMethod.GET)
    Result<List<RoleBaseVO>> getRoleBaseInfo(@PathVariable("userId") String userId);


    /**
     * 查询多个用户的角色列表
     * @param userIds
     * @return
     */
    @RequestMapping(value = "/role/get_batch_role_base_info", method = RequestMethod.GET)
    Result<List<RoleBaseVO>> getBatchRoleBaseInfo(@RequestParam("userIds") List<String> userIds);
}
