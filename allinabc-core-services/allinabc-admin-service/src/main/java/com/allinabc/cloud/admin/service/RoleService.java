package com.allinabc.cloud.admin.service;

import com.allinabc.cloud.admin.pojo.dto.RoleAddDTO;
import com.allinabc.cloud.admin.pojo.dto.RoleAuthorizeDTO;
import com.allinabc.cloud.admin.pojo.dto.RoleSearchDTO;
import com.allinabc.cloud.admin.pojo.dto.RoleUpdateDTO;
import com.allinabc.cloud.admin.pojo.po.Role;
import com.allinabc.cloud.admin.pojo.vo.RoleBaseVO;
import com.allinabc.cloud.admin.pojo.vo.RoleDetailVO;
import com.allinabc.cloud.admin.pojo.vo.RoleVO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.service.MybatisCommonService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2020-12-16
 */
public interface RoleService extends MybatisCommonService<Role> {

    /**
     * 更新角色状态
     *
     * @param id
     * @param status
     * @return
     */
    Role updateStatus(String id, String status);

    /**
     * 根据用户查询角色列表
     *
     * @param userId
     * @return
     */
    List<Role> selectByUser(String userId);

    /**
     * 查询角色
     * @param userId
     * @return
     */
    Result<List<RoleBaseVO>> getRoleBaseInfo(String userId);

    /**
     * 添加角色
     * @param addDTO
     * @return
     */
    Result<Void> cusInsert(RoleAddDTO addDTO);

    /**
     * 查询角色列表
     * @param searchDTO
     * @return
     */
    IPage<RoleVO> cusPageList(RoleSearchDTO searchDTO);

    /**
     * 更新角色
     * @param updateDTO
     * @return
     */
    Result<Void> cusUpdate(RoleUpdateDTO updateDTO);

    /**
     * 角色授权
     * @param authorizeDTO
     * @return
     */
    Result<Void> cusAuthorize(RoleAuthorizeDTO authorizeDTO);

    /**
     * 查询角色列表(未分页)
     * @return
     */
    Result<List<RoleVO>> cusAllList();

    /**
     * 查询多个用户角色ID
     * @param userIds
     * @return
     */
    Result<List<RoleBaseVO>> getBatchRoleBaseInfo(List<String> userIds);

    /**
     * 查询角色详情
     * @param id
     * @return
     */
    Result<RoleDetailVO> getRoleById(String id);
}
