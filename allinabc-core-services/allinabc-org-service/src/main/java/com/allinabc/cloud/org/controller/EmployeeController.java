package com.allinabc.cloud.org.controller;

import com.allinabc.cloud.admin.pojo.vo.EmployeeDetailVO;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.dto.EmployeeDeptDTO;
import com.allinabc.cloud.org.pojo.po.Employee;
import com.allinabc.cloud.org.pojo.vo.EmployeeDeptVO;
import com.allinabc.cloud.org.service.EmployeeService;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import com.allinabc.common.mybatis.service.MybatisCommonService;
import com.allinabc.common.mybatis.util.PageHelper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author QQF
 * @date 2020/12/24 10:33
 */

@Api(tags = "人员")
@RestController
@RequestMapping("/emp")
public class EmployeeController extends MybatisBaseCrudController<Employee> {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "查询人员列表", notes = "")
    @PostMapping("/cus/list")
    public Result<Page<EmployeeDeptVO>> findCusList(@RequestBody EmployeeDeptDTO employeeDeptDTO) {
        IPage<EmployeeDeptVO> page = employeeService.findCusList(employeeDeptDTO);
        return Result.success(PageHelper.convert(page), "查询成功");
    }

    /**
     * 查询员工信息
     * @param username
     * @return
     */
    @GetMapping("/get_by_username")
    public Result<Employee> getByUsername(@RequestParam("username") String username) {
        return employeeService.getByUsername(username);
    }

    @ApiOperation(value = "查询所有员工列表(不分页)")
    @GetMapping("/find_all")
    public Result<List<EmployeeDetailVO>> findAll(@RequestParam(value = "username", required = false) String username) {
        return employeeService.findAll(username);
    }


    @ApiOperation(value = "查询员工上两级部门员工(包含本部门)")
    @GetMapping("/find_third_parent/{userId}")
    public Result<List<Employee>> findThirdParent(@PathVariable("userId") String userId) {
        return employeeService.findThirdParent(userId);
    }

    /**
     * 查询员工的部门ID
     * 提供给远程调用
     * @param userId
     * @return
     */
    @GetMapping("/get_employee_dept_id/{userId}")
    public Result<String> getEmployeeDeptId(@PathVariable("userId") String userId) {
        Employee employee = employeeService.getById(userId);
        return Result.success(employee == null ? "" : employee.getDeptId());
    }

    /**
     * 查询员工列表
     * 远程调用使用
     * @param userIds
     * @return
     */
    @PostMapping("/find_employees")
    public Result<List<Employee>> findEmployees(@RequestBody List<String> userIds) {
        List<Employee> employees = employeeService.findEmployees(userIds);
        return Result.success(employees);
    }

    /**
     * 查询用户姓名
     * @param userId 员工 、客户 id
     * @return
     */
    @GetMapping("/api/get_username/{userId}")
    public Result<String> getNameByUserId(@PathVariable("userId") String userId) {
        return employeeService.getNameByUserId(userId);
    }

    @Override
    public MybatisCommonService<Employee> getService() {
        return employeeService;
    }

}