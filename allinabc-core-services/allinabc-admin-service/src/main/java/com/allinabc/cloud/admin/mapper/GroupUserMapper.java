package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.po.GroupUser;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-17
 */
public interface GroupUserMapper extends MybatisCommonBaseMapper<GroupUser> {

    /**
     * 批量插入
     * @param groupUsers
     */
    void batchInsert(@Param("groupUsers") List<GroupUser> groupUsers);
}
