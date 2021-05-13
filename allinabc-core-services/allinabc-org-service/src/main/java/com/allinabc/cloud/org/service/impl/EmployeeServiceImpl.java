package com.allinabc.cloud.org.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.admin.pojo.vo.EmployeeDetailVO;
import com.allinabc.cloud.common.core.utils.bean.BeanUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.mapper.CustomerAccountMapper;
import com.allinabc.cloud.org.mapper.DepartmentMapper;
import com.allinabc.cloud.org.mapper.EmployeeMapper;
import com.allinabc.cloud.org.pojo.dto.EmployeeDeptDTO;
import com.allinabc.cloud.org.pojo.po.CustomerAccount;
import com.allinabc.cloud.org.pojo.po.Department;
import com.allinabc.cloud.org.pojo.po.Employee;
import com.allinabc.cloud.org.pojo.vo.EmployeeDeptVO;
import com.allinabc.cloud.org.service.DepartmentService;
import com.allinabc.cloud.org.service.EmployeeService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author QQF
 * @date 2020/12/24 10:32
 */

@Slf4j
@Service
@Transactional(readOnly = true)
public class EmployeeServiceImpl extends MybatisCommonServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private CustomerAccountMapper customerAccountMapper;


    @Override
    public MybatisCommonBaseMapper<Employee> getRepository() {
        return employeeMapper;
    }

    @Override
    public IPage<EmployeeDeptVO> findCusList(EmployeeDeptDTO employeeDeptDTO) {
        Page<EmployeeDeptVO> page = new Page<>();
        page.setCurrent(employeeDeptDTO.getPageNum());
        page.setSize(employeeDeptDTO.getPageSize());

        String departId = employeeDeptDTO.getDepartId();
        List<String> deptIdList;
        if (StringUtils.isBlank(departId) || departId.equals("0")) {
            deptIdList = Collections.EMPTY_LIST;
        } else {
            deptIdList = departmentService.findListDepartId(departId);
        }
        IPage<EmployeeDeptVO> employeeDeptVOIPage = employeeMapper.selectPageVo(page, employeeDeptDTO.getKeyword(), deptIdList);
        return employeeDeptVOIPage;
    }

    /**
     * 登录时查询员工信息
     * @param username
     * @return
     */
    @Override
    public Result<Employee> getByUsername(String username) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("LOGIN_ID", username);
        List<Employee> employees = employeeMapper.selectByMap(map);
        if (employees.isEmpty()) {
            return Result.success("查询成功");
        }
        Employee employee = employees.stream().findFirst().get();
        return Result.success(employee, "查询成功");
    }

    @Override
    public Result<List<EmployeeDetailVO>> findAll(String username) {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("STATUS", 1);
        if (StringUtils.isNotBlank(username)) {
            wrapper.like("UPPER(NAME)", username.toUpperCase());
        }
        List<Employee> employees = employeeMapper.selectList(wrapper);
        List<EmployeeDetailVO> employeeDetailVOS = employees.stream().map(e -> {
            EmployeeDetailVO employeeDetailVO = new EmployeeDetailVO();
            BeanUtils.copyBeanProp(employeeDetailVO, e);
            return employeeDetailVO;
        }).collect(Collectors.toList());
        return Result.success(employeeDetailVOS, "查询成功");
    }

    @Override
    public Result<List<Employee>> findThirdParent(String userId) {

        Employee employee = employeeMapper.selectById(userId);
        if (employee == null) {
            log.warn("数据不存在，userId = {}", userId);
            return Result.success(Collections.EMPTY_LIST);
        }

        if (StringUtils.isBlank(employee.getDeptId())) {
            return Result.success(Collections.EMPTY_LIST);
        }

        Department department = departmentMapper.selectById(employee.getDeptId());
        if (null == department) {
            return Result.success(Collections.EMPTY_LIST);
        }
        List<String> deptIds = new ArrayList<>();
        deptIds.add(String.valueOf(department.getId()));
        if ("0".equals(department.getPid())) {
            deptIds.add(department.getPid());
        } else {
            List<Department> departments = departmentMapper.findList();
            Long parentId = departments.stream().filter(d -> department.getPid().equals(String.valueOf(d.getId())))
                    .map(a -> a.getId()).findFirst().get();
            deptIds.add(String.valueOf(parentId));

            String parParentId = departments.stream().filter(d -> d.getId().equals(Long.valueOf(parentId)))
                    .map(a -> a.getPid()).findFirst().get();
            deptIds.add(parParentId);
        }
        log.info("deptIds = {}", JSONObject.toJSON(deptIds));
        List<Employee> employees = employeeMapper.selectBatchPid(deptIds);
        return Result.success(employees, "查询成功");
    }

    @Override
    public List<Employee> findEmployees(List<String> userIds) {
        Assert.notEmpty(userIds, "userIds must not null");
        List<Employee> employees = employeeMapper.selectBatchIds(userIds);
        return employees;
    }

    @Override
    public Result<String> getNameByUserId(String userId) {
        log.info("查询用户ID的姓名，userId = {}", userId);
        Employee employee = employeeMapper.selectById(userId);
        if (employee != null) {
            return Result.success(employee.getName(), "查询成功");
        }

        CustomerAccount customerAccount = customerAccountMapper.selectById(userId);
        if (customerAccount != null) {
            return Result.success(customerAccount.getUsername(), "查询成功");
        }
        log.info("查询成功，没有该用户ID的信息，userId = {}", userId);
        return Result.success("查询成功");
    }
}