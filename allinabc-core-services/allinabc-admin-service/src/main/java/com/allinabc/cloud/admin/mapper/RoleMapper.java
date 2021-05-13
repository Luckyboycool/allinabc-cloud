package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.dto.RoleSearchDTO;
import com.allinabc.cloud.admin.pojo.po.Role;
import com.allinabc.cloud.admin.pojo.vo.RoleBaseVO;
import com.allinabc.cloud.admin.pojo.vo.RoleVO;
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
 * @since 2020-12-16
 */
public interface RoleMapper extends MybatisCommonBaseMapper<Role> {


     List<Role> selectRolesByUserId(@Param("userId")String userId);

    /**
     * 查询角色列表
      * @param userId 用户id
     * @return
     */
    List<RoleBaseVO> getRoleBaseInfo(@Param("userId") String userId);

    /**
     * 查询角色列表(带搜索)
     * @param page
     * @param searchDTO
     * @return
     */
    IPage<RoleVO> pageBySearch(Page page, @Param("searchDTO") RoleSearchDTO searchDTO);

    /**
     * 查询多个用户的角色列表
     * @param userIds
     * @return
     */
    List<RoleBaseVO> getBatchRoleBaseInfo(@Param("userIds") List<String> userIds);
}
