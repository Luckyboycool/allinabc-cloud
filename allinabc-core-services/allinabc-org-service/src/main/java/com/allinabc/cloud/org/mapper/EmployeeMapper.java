package com.allinabc.cloud.org.mapper;

import com.allinabc.cloud.org.pojo.po.Employee;
import com.allinabc.cloud.org.pojo.vo.EmployeeDeptVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author QQF
 * @date 2020/12/24 10:30
 */

public interface EmployeeMapper extends MybatisCommonBaseMapper<Employee> {
    /**
     * 分页查询员工列表信息
     * @param page
     * @param keyword
     * @param deptIdList
     * @return
     */
    IPage<EmployeeDeptVO> selectPageVo(Page page, @Param("keyword") String keyword,
                                       @Param("deptIdList") List<String> deptIdList);

    /**
     * 查询员工上两级部门员工
     * @param deptIds
     * @return
     */
    List<Employee> selectBatchPid(@Param("deptIds") List<String> deptIds);
}
