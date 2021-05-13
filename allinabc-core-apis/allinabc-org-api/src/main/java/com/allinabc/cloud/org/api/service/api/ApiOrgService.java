package com.allinabc.cloud.org.api.service.api;

import com.allinabc.cloud.admin.pojo.vo.EmployeeDetailVO;
import com.allinabc.cloud.org.pojo.vo.DeptBaseVO;
import com.allinabc.cloud.org.pojo.vo.EmployeeDeptVO;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/2 3:16 下午
 **/
public interface ApiOrgService {

    /**
     * 查询部门信息
     * @param ids
     * @return
     */
    List<DeptBaseVO> findBaseList(List<String> ids);

    /**
     * 查询部门下的员工列表
     * @param ids
     * @return
     */
    List<EmployeeDeptVO> getEmployeeByDeptIds(List<String> ids);

    /**
     * 查询部门下的员工列表(包含子部门)
     * @param code
     * @return
     */
    List<EmployeeDetailVO> getDeptEmployees(String code);
}
