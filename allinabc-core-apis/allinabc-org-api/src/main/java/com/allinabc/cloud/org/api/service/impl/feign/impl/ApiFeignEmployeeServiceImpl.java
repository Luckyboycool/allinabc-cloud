package com.allinabc.cloud.org.api.service.impl.feign.impl;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.api.service.api.ApiEmployeeService;
import com.allinabc.cloud.org.api.service.impl.feign.client.EmployeeServiceApi;
import com.allinabc.cloud.org.pojo.po.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/4 11:22 上午
 **/
@Service
public class ApiFeignEmployeeServiceImpl implements ApiEmployeeService {
    @Autowired
    private EmployeeServiceApi employeeServiceApi;

    @Override
    public Employee getByUsername(String username) {
        Result<Employee> result = employeeServiceApi.getByUsername(username);
        return null!=result && null!=result.getData() ? result.getData() : null;
    }

    @Override
    public String getByUserId(String userId) {
        Result<String> result = employeeServiceApi.getByUserId(userId);
        return null != result && null!= result.getData() ? result.getData(): null;
    }

    @Override
    public List<Employee> findThirdParent(String userId) {
        Result<List<Employee>> result = employeeServiceApi.findThirdParent(userId);
        return null != result && null!= result.getData() ? result.getData(): null;
    }

    @Override
    public List<Employee> findEmployees(List<String> userIds) {
        Result<List<Employee>> result = employeeServiceApi.findEmployees(userIds);
        return null != result && null!= result.getData() ? result.getData(): null;
    }

    @Override
    public String getUsernameByUserId(String userId) {
        Result<String> result = employeeServiceApi.getNameByUserId(userId);
        return null != result && null != result.getData() ? result.getData() : null;
    }
}
