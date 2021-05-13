package com.allinabc.cloud.org.controller;

import cn.hutool.core.lang.tree.Tree;
import com.allinabc.cloud.admin.pojo.vo.EmployeeDetailVO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.po.Department;
import com.allinabc.cloud.org.pojo.vo.DepartmentTreeVO;
import com.allinabc.cloud.org.pojo.vo.DeptBaseVO;
import com.allinabc.cloud.org.pojo.vo.EmployeeDeptVO;
import com.allinabc.cloud.org.service.DepartmentService;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import com.allinabc.common.mybatis.service.MybatisCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author QQF
 * @date 2020/12/24 10:33
 */
@Api(tags = "部门")
@RestController
@RequestMapping("/dept")
public class DepartmentController extends MybatisBaseCrudController<Department> {

    @Autowired
    private DepartmentService departmentService;

    @Override
    public MybatisCommonService<Department> getService() {
        return departmentService;
    }

    @ApiOperation(value = "查询部门组织架构", notes = "查询根部门请传递0，其它为部门id")
    @ApiImplicitParam(name = "id", value = "部门id", required = true)
    @GetMapping("/list")
    public Result<List<DepartmentTreeVO>> findList(@RequestParam("id") String id) {
        List<DepartmentTreeVO> departmentTreeVOList = departmentService.findList(id);
        return Result.success(departmentTreeVOList, "查询成功");
    }

    @ApiOperation(value = "查询部门组织架构(带搜索)", notes = "")
    @GetMapping("/list/search/name")
    public Result<List<Tree<String>>> findListByName(@RequestParam(value = "name", required = false) String name) {
        List<Tree<String>> departmentTreeVOList = departmentService.findListByName(name);
        return Result.success(departmentTreeVOList, "查询成功");
    }

    /**
     * 查询部门列表
     * 远程接口
     * @param ids
     * @return
     */
    @PostMapping("/find/base/list")
    public Result<List<DeptBaseVO>> findBaseList(@RequestBody List<String> ids) {
        return Result.success(departmentService.findBaseList(ids), "查询成功");
    }


    /**
     * 查询部门下的员工列表(包含子部门的员工)
     * @param code
     * @return
     */
    @ApiOperation(value = "查询部门下的员工列表(包含子部门的员工)")
    @GetMapping("/get_employees")
    public Result<List<EmployeeDetailVO>> getDeptEmployees(@RequestParam("code") String code) {
        return departmentService.getDeptEmployees(code);
    }

    /**
     * 查询部门下的员工列表
     * 远程接口
     * @param ids
     * @return
     */
    @PostMapping("/get_employee_by_deptid")
    public Result<List<EmployeeDeptVO>> getEmployeeByDeptIds(@RequestBody List<String> ids) {
        return departmentService.getEmployeeByDeptIds(ids);
    }





}