package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.po.RoleUser;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-16
 */
public interface RoleUserMapper extends MybatisCommonBaseMapper<RoleUser> {

    /**
     * 更据 roleIds 获取 user id
     *
     * @param roleIdList
     * @return
     */
    List<String> selectUserIdByRoleIds(@Param("roleIdList") List<String> roleIdList);

    /**
     * 批量插入
     * @param roleUsers
     */
    void batchInsert(@Param("roleUsers") List<RoleUser> roleUsers);
}
