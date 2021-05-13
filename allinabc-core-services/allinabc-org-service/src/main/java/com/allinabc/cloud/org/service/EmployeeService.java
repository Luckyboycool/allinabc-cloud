package com.allinabc.cloud.org.service;

import com.allinabc.cloud.admin.pojo.vo.EmployeeDetailVO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.dto.EmployeeDeptDTO;
import com.allinabc.cloud.org.pojo.po.Employee;
import com.allinabc.cloud.org.pojo.vo.EmployeeDeptVO;
import com.allinabc.common.mybatis.service.MybatisCommonService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author QQF
 * @date 2020/12/24 10:32
 */

public interface EmployeeService extends MybatisCommonService<Employee> {

    /**
     * 查询部门下的员工
     * @param employeeDeptDTO
     * @return
     */
    IPage<EmployeeDeptVO> findCusList(EmployeeDeptDTO employeeDeptDTO);

    /**
     * 查询员工的信息
     * @param username
     * @return
     */
    Result<Employee> getByUsername(String username);

    /**
     * 查询员工所有列表
     * @return
     */
    Result<List<EmployeeDetailVO>> findAll(String username);

    /**
     * 查询员工上两级部门员工
     * @param userId
     * @return
     */
    Result<List<Employee>> findThirdParent(String userId);

    /**
     * 查询员工列表
     * @param userIds
     * @return
     */
    List<Employee> findEmployees(List<String> userIds);

    /**
     * 查询用户名
     * @param userId
     * @return
     */
    Result<String> getNameByUserId(String userId);
}
