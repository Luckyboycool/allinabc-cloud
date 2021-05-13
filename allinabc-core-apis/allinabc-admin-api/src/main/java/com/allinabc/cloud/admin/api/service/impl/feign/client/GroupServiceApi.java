package com.allinabc.cloud.admin.api.service.impl.feign.client;

import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.vo.EmployeeBasicVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/29 10:59 上午
 **/
@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE)
public interface GroupServiceApi {
    /**
     * 查询群组下的员工列表
     * @param groupName
     * @return
     */
    @RequestMapping(value = "/group/find_employees", method = RequestMethod.GET)
    Result<List<EmployeeBasicVO>> findEmployeeByGroupName(@RequestParam("groupName") String groupName);
}
