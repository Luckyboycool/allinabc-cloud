package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.po.GroupDept;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/2 3:43 下午
 **/
public interface GroupDeptMapper extends MybatisCommonBaseMapper<GroupDept> {
    /**
     * 批量插入
     * @param groupDepts
     */
    void batchInsert(@Param("groupDepts") List<GroupDept> groupDepts);
}
