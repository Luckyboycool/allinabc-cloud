package com.allinabc.cloud.org.api.service.impl.feign.client;

import com.allinabc.cloud.admin.pojo.vo.EmployeeDetailVO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.vo.DeptBaseVO;
import com.allinabc.cloud.org.pojo.vo.EmployeeDeptVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/2 3:16 下午
 **/
@FeignClient(value = "allinabc-org")
public interface OrgServiceApi {

    /**
     * 查询部门列表
     * @param ids
     * @return
     */
    @RequestMapping(value = "/dept/find/base/list", method = RequestMethod.POST)
    Result<List<DeptBaseVO>> findBaseList(@RequestBody List<String> ids);

    /**
     * 查询部门列表下的员工
     * @param ids
     * @return
     */
    @RequestMapping(value = "/dept/get_employee_by_deptid", method = RequestMethod.POST)
    Result<List<EmployeeDeptVO>> getEmployeeByDeptIds(@RequestBody List<String> ids);

    /**
     * 查询部门下的员工列表(包含子部门)
     * @param code
     * @return
     */
    @RequestMapping(value = "/dept/get_employees", method = RequestMethod.GET)
    Result<List<EmployeeDetailVO>> getDeptEmployees(@RequestParam("code") String code);
}
