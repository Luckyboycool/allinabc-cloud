package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.po.RoleGroup;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/2 5:35 下午
 **/
public interface RoleGroupMapper extends MybatisCommonBaseMapper<RoleGroup> {
    /**
     * 批量插入
     * @param roleGroups
     */
    void batchInsert(@Param("roleGroups") List<RoleGroup> roleGroups);
}
