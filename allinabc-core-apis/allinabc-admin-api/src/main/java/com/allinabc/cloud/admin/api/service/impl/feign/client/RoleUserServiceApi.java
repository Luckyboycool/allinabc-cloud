package com.allinabc.cloud.admin.api.service.impl.feign.client;

import com.allinabc.cloud.admin.pojo.po.RoleUser;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/24/21 11:30 PM
 **/
@FeignClient(value = ServiceNameConstants.ADMIN_SERVICE)
public interface RoleUserServiceApi {
    /**
     * 批量绑定角色和用户
     * @param roleUserList
     * @return
     */
    @RequestMapping(value = "/role/user/insert/batch", method = RequestMethod.POST)
    Result<Void> insertBatch(@RequestBody List<RoleUser> roleUserList);

    /**
     * 更新绑定角色和用户
     * @param roleUsers
     * @return
     */
    @RequestMapping(value = "/role/user/update/batch", method = RequestMethod.PUT)
    Result<Void> updateBatch(@RequestBody List<RoleUser> roleUsers);
}
