package com.allinabc.cloud.org.api.service.api;

import com.allinabc.cloud.org.pojo.po.Employee;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/4 10:06 上午
 **/
public interface ApiEmployeeService {
    /**
     * 查询员工信息
     * @param username
     * @return
     */
    Employee getByUsername(String username);

    /**
     * 查询员工的部门编号
     * @param userId
     * @return
     */
    String getByUserId(String userId);

    /**
     * 查询员工上两级部门员工列表
     * @date 2021-03-09 13:18:56
     * @param userId
     * @return
     */
    List<Employee> findThirdParent(String userId);

    /**
     * 查询员工列表
     * @param userIds
     * @return
     */
    List<Employee> findEmployees(List<String> userIds);

    /**
     * 查询员工名字
     * @param userId 员工ID或客户ID
     * @return
     */
    String getUsernameByUserId(String userId);
}
