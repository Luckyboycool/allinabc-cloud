package com.allinabc.cloud.org.api.service.impl.feign.client;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.po.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/4 10:07 上午
 **/
@FeignClient(value = "allinabc-org")
public interface EmployeeServiceApi {

    /**
     * 查询员工信息
     * @param username
     * @return
     */
    @RequestMapping(value = "/emp/get_by_username", method = RequestMethod.GET)
    Result<Employee> getByUsername(@RequestParam("username") String username);

    /**
     * 查询员工的部门ID
     * @param userId
     * @return
     */
    @RequestMapping(value = "/emp/get_employee_dept_id/{userId}", method = RequestMethod.GET)
    Result<String> getByUserId(@PathVariable("userId") String userId);

    /**
     * 查询员工上两级部门下的员工列表(包含本部门下的员工)
     * @date 2021-03-09 13:20:59
     * @param userId
     * @return
     */
    @RequestMapping(value = "/emp/find_third_parent/{userId}", method = RequestMethod.GET)
    Result<List<Employee>> findThirdParent(@PathVariable("userId") String userId);

    /**
     * 查询员工列表
     * @param userIds
     * @return
     */
    @RequestMapping(value = "/emp/find_employees", method = RequestMethod.POST)
    Result<List<Employee>> findEmployees(@RequestBody List<String> userIds);

    /**
     * 查询用户名字
     * @param userId
     * @return
     */
    @RequestMapping(value = "/emp/api/get_username/{userId}", method = RequestMethod.GET)
    Result<String> getNameByUserId(@PathVariable("userId") String userId);
}
