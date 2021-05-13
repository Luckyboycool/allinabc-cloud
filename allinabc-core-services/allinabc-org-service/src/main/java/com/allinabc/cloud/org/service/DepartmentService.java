package com.allinabc.cloud.org.service;

import cn.hutool.core.lang.tree.Tree;
import com.allinabc.cloud.admin.pojo.vo.EmployeeDetailVO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.po.Department;
import com.allinabc.cloud.org.pojo.vo.DepartmentTreeVO;
import com.allinabc.cloud.org.pojo.vo.DeptBaseVO;
import com.allinabc.cloud.org.pojo.vo.EmployeeDeptVO;
import com.allinabc.common.mybatis.service.MybatisCommonService;

import java.util.List;

/**
 * @author QQF
 * @date 2020/12/24 10:31
 */

public interface DepartmentService extends MybatisCommonService<Department> {

    /**
     * 查询部门列表
     * @param id 部门id
     * @return
     */
    List<DepartmentTreeVO> findList(String id);

    /**
     * 查询父部门下所有部门id
     * @param pid
     * @return 所有部门id
     */
    List<String> findListDepartId(String pid);

    /**
     * 查询部门列表(带搜索)
     * @param name 部门名称
     * @return
     */
    List<Tree<String>> findListByName(String name);

    /**
     * 查询部门列表
     * @param ids
     * @return
     */
    List<DeptBaseVO> findBaseList(List<String> ids);

    /**
     * 查询部门下的员工列表(包含子部门的员工)
     * @param code
     * @return
     */
    Result<List<EmployeeDetailVO>> getDeptEmployees(String code);

    /**
     * 查询部门下的员工列表(不包含子部门的员工)
     * @param ids
     * @return
     */
    Result<List<EmployeeDeptVO>> getEmployeeByDeptIds(List<String> ids);
}
