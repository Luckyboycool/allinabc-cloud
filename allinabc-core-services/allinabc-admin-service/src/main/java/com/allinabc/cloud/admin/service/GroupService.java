package com.allinabc.cloud.admin.service;

import com.allinabc.cloud.admin.pojo.dto.GroupInsertDTO;
import com.allinabc.cloud.admin.pojo.dto.GroupSearchDTO;
import com.allinabc.cloud.admin.pojo.dto.GroupUpdateDTO;
import com.allinabc.cloud.admin.pojo.po.Group;
import com.allinabc.cloud.admin.pojo.vo.GroupDetailVO;
import com.allinabc.cloud.admin.pojo.vo.GroupListVO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.vo.EmployeeBasicVO;
import com.allinabc.common.mybatis.service.MybatisCommonService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 *  group（代码迁移）
 * </p>
 *
 * @since 2020-12-17
 */
public interface GroupService extends MybatisCommonService<Group> {

    /**
     * 更新群组 只更新群组信息
     * @param id
     * @param entity
     * @return
     */
    Group updateGroup(String id, Group entity);

    /**
     * 更新群组 只更新用户信息
     * @param id
     * @param entity
     * @return
     */
    Group updateUser(String id, Group entity);

    /**
     * 带条件列表查询群组
     * @param entity
     * @return
     */
    List<Group> selectList(Group entity);

    /**
     * 根据用户查询群组
     * @param userId
     * @return
     */
    List<Group> selectByUser(String userId);

    /**
     * 查询群组详情
     * @param id 群组ID
     * @return
     */
    Result<GroupDetailVO> getByGroupId(String id);

    /**
     * 分页查询
     * @param searchDTO
     * @return
     */
    IPage<GroupListVO> findCusList(GroupSearchDTO searchDTO);

    /**
     * 群组列表(未分页)
     * @param name
     * @return
     */
    Result<List<Group>> findAllList(String name);

    /**
     * 查询群组下的员工列表
     * @param groupName
     * @return
     */
    Result<List<EmployeeBasicVO>> findEmployeeByGroupName(String groupName);

    /**
     * 添加群组
     * @param insertDTO
     * @return
     */
    Result<Void> cusInsert(GroupInsertDTO insertDTO);

    /**
     * 更新群组
     * @param updateDTO
     * @return
     */
    Result<Void> cusUpdate(GroupUpdateDTO updateDTO);
}
