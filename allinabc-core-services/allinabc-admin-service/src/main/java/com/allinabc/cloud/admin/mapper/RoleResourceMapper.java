package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.po.RoleResource;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/2 6:14 下午
 **/
public interface RoleResourceMapper extends MybatisCommonBaseMapper<RoleResource> {
    /**
     * 批量插入
     * @param roleResources
     */
    void batchInsert(@Param("roleResources") List<RoleResource> roleResources);
}
