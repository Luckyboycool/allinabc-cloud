package com.allinabc.cloud.org.mapper;

import com.allinabc.cloud.org.pojo.vo.DepartmentTreeVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.cloud.org.pojo.po.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author QQF
 * @date 2020/12/24 10:30
 */

public interface DepartmentMapper extends MybatisCommonBaseMapper<Department> {

    /**
     * 根据pid查询子部门
     * @param id pid
     * @return
     */
    List<DepartmentTreeVO> findListByPid(@Param("id") String id);

    /**
     * 查询所有部门
     * @return
     */
    List<Department> findList();

    /**
     * 查询部门列表(带搜索)
     * @param name
     * @return
     */
    List<Department> findListByName(@Param("name") String name);

    /**
     * 查询部门下的所有部门ID
     * @param id
     * @return
     */
    List<String> selectChild(@Param("id") String id);
}
