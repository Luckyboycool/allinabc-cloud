package com.allinabc.cloud.admin.mapper;


import com.allinabc.cloud.admin.pojo.po.SysUser;
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
public interface UserMapper extends MybatisCommonBaseMapper<SysUser> {

    List<SysUser> selectByGroup(@Param("groupId") String groupId);

    /**
     * 更新 用户状态ById
     *
     * @param idList
     * @return
     */
    Integer updateStatusById(@Param("status") String status, @Param("idList") List<String> idList);
}
