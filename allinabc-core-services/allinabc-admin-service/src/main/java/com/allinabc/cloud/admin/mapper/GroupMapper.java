package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.dto.GroupSearchDTO;
import com.allinabc.cloud.admin.pojo.po.Group;
import com.allinabc.cloud.admin.pojo.vo.GroupListVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public interface GroupMapper extends MybatisCommonBaseMapper<Group> {

    /*
    * 代码迁移，以及改造
    */
    List<Group> selectGroupInfoByUserId(@Param("userId") String userId);

    /*
     * 代码迁移，以及改造
     */
    long countByUser(@Param("userId")String userId);

    /**
     * 查询群组列表
     * @param page
     * @param searchDTO
     * @return
     */
    IPage<GroupListVO> findPage(Page<GroupListVO> page, @Param("searchDTO") GroupSearchDTO searchDTO);
}
