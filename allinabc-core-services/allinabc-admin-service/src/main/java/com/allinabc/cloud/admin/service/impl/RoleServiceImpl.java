package com.allinabc.cloud.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.admin.mapper.*;
import com.allinabc.cloud.admin.pojo.dto.RoleAddDTO;
import com.allinabc.cloud.admin.pojo.dto.RoleAuthorizeDTO;
import com.allinabc.cloud.admin.pojo.dto.RoleSearchDTO;
import com.allinabc.cloud.admin.pojo.dto.RoleUpdateDTO;
import com.allinabc.cloud.admin.pojo.po.*;
import com.allinabc.cloud.admin.pojo.vo.ResourceRoleVO;
import com.allinabc.cloud.admin.pojo.vo.RoleBaseVO;
import com.allinabc.cloud.admin.pojo.vo.RoleDetailVO;
import com.allinabc.cloud.admin.pojo.vo.RoleVO;
import com.allinabc.cloud.admin.service.RoleResourceService;
import com.allinabc.cloud.admin.service.RoleService;
import com.allinabc.cloud.common.core.utils.bean.BeanUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.api.service.api.ApiEmployeeService;
import com.allinabc.cloud.org.pojo.po.Employee;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @since 2020-12-16
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl extends MybatisCommonServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleResourceMapper roleResourceMapper;
    @Resource
    private RoleResourceService roleResourceService;
    @Resource
    private RoleUserMapper roleUserMapper;
    @Resource
    private RoleGroupMapper roleGroupMapper;
    @Resource
    private GroupMapper groupMapper;
    @Resource
    private ApiEmployeeService apiEmployeeService;
    @Resource
    private ResourceMapper resourceMapper;

    @Override
    public Role updateStatus(String id, String status) {
        Role role = this.selectById(id);
        role.setStatus(status);
        role.setUpdateTm(new Date());
        role.setUpdatedBy(getCurrentUser().getId());
        this.update(id,role);
        return this.selectById(id);
    }

    @Override
    public List<Role> selectByUser(String userId) {
        return roleMapper.selectRolesByUserId(userId);
    }

    @Override
    public Result<List<RoleBaseVO>> getRoleBaseInfo(String userId) {
        return Result.success(roleMapper.getRoleBaseInfo(userId), "查询成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> cusInsert(RoleAddDTO addDTO) {
        log.info("start create role, info = {}", JSONObject.toJSON(addDTO));

        if (this.checkRoleKeyIsExist(addDTO.getRoleKey())) {
            log.warn("功能角色代码已存在, roleKey = {}", addDTO.getRoleKey());
            return Result.success("功能角色代码已存在");
        }

        Role role = new Role();
        BeanUtils.copyBeanProp(role, addDTO);
        List<String> resourceIds = addDTO.getResourceIds();
        resourceIds.removeIf(Objects::isNull);
        role.setId(IdUtil.getSnowflake(0, 0).nextIdStr());
        role.setCreatedBy(getCurrentUserId());
        role.setCreateTm(new Date());
        // 添加角色
        Role roleResult = this.insert(role);

        if (!resourceIds.isEmpty()) {
            List<RoleResource> roleResources = resourceIds.stream().distinct()
                    .map(i -> {
                        RoleResource roleResource = new RoleResource();
                        roleResource.setRoleId(roleResult.getId());
                        roleResource.setResourceId(i);
                        return roleResource;
                    }).collect(Collectors.toList());
            roleResourceService.batchInsert(roleResources);
        }
        return Result.success("添加成功");
    }

    /**
     * 查询roleKey是否存在
     * @param roleKey
     * @return true 存在  false 不存在
     */
    private boolean checkRoleKeyIsExist(String roleKey) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("ROLE_KEY", roleKey);
        Role role = roleMapper.selectOne(wrapper);
        if (null != role) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IPage<RoleVO> cusPageList(RoleSearchDTO searchDTO) {
        log.info("start query role list, param = {}", JSONObject.toJSON(searchDTO));
        // TODO 暂时采用公共方法
        Page<RoleVO> page = new Page();
        page.setCurrent(searchDTO.getPageNum());
        page.setSize(searchDTO.getPageSize());
        IPage<RoleVO> roleVOIPage = roleMapper.pageBySearch(page, searchDTO);
        List<String> roleIds = roleVOIPage.getRecords().stream().map(RoleVO::getId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(roleIds)) {
            return roleVOIPage;
        }
        List<ResourceRoleVO> resourceRoleVOS = resourceMapper.findByRoleIds(roleIds);
        if (CollectionUtil.isEmpty(resourceRoleVOS)) {
            return roleVOIPage;
        }

        roleVOIPage.getRecords().forEach(roleVO -> {
            List<RoleVO.ResourceVO> collect = resourceRoleVOS.stream().filter(a -> roleVO.getId().equals(a.getRoleId())).map(b -> {
                RoleVO.ResourceVO resourceVO = new RoleVO.ResourceVO();
                resourceVO.setId(b.getId());
                resourceVO.setName(b.getName());
                return resourceVO;
            }).collect(Collectors.toList());
            roleVO.setResources(collect);
        });
        return roleVOIPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> cusUpdate(RoleUpdateDTO updateDTO) {
        String id = updateDTO.getId();
        Role role = roleMapper.selectById(id);
        if (null == role) {
            log.error("没有该信息, roleId ={}", id);
            return Result.failed("没有该信息");
        }
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("ROLE_KEY", updateDTO.getRoleKey());
        Role roleResult = roleMapper.selectOne(wrapper);
        // id 不相等 代表存在相同的roleKey
        if (null != roleResult && !role.getId().equals(roleResult.getId())) {
            log.warn("功能角色代码已存在, roleKey = {}", updateDTO.getRoleKey());
            return Result.failed("功能角色代码已存在");
        }

        BeanUtils.copyBeanProp(role, updateDTO);
        List<String> resourceIds = updateDTO.getResourceIds();
        resourceIds.removeIf(Objects::isNull);
        Map<String, Object> map = new HashMap<>(1);
        map.put("ROLE_ID", id);
        roleResourceMapper.deleteByMap(map);
        if (CollUtil.isNotEmpty(resourceIds)) {
            List<RoleResource> roleResources = resourceIds.stream().distinct()
                    .map(i -> {
                        RoleResource roleResource = new RoleResource();
                        roleResource.setRoleId(id);
                        roleResource.setResourceId(i);
                        return roleResource;
                    }).collect(Collectors.toList());
            roleResourceMapper.batchInsert(roleResources);
        }
        role.setUpdatedBy(getCurrentUserId());
        role.setUpdateTm(new Date());

        roleMapper.updateById(role);
        return Result.success("更新成功");
    }

    @Override
    public Result<Void> cusAuthorize(RoleAuthorizeDTO authorizeDTO) {
        String id = authorizeDTO.getId();
        Role role = roleMapper.selectById(id);
        if (null == role) {
            log.error("没有该信息, roleId = {}", id);
            return Result.failed("没有该信息");
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("ROLE_ID", id);
        roleUserMapper.deleteByMap(map);
        roleGroupMapper.deleteByMap(map);

        List<String> userIds = authorizeDTO.getUserIds();
        userIds.removeIf(Objects::isNull);
        if (CollectionUtil.isNotEmpty(userIds)) {
            List<RoleUser> roleUsers = userIds.stream().map(i -> {
                RoleUser roleUser = new RoleUser();
                roleUser.setUserId(i);
                roleUser.setRoleId(id);
                return roleUser;
            }).collect(Collectors.toList());
            roleUserMapper.batchInsert(roleUsers);
        }

        List<String> groupIds = authorizeDTO.getGroupIds();
        userIds.removeIf(Objects::isNull);
        if (CollectionUtil.isNotEmpty(groupIds)) {
            List<RoleGroup> roleGroups = groupIds.stream().map(i -> {
                RoleGroup roleGroup = new RoleGroup();
                roleGroup.setRoleId(id);
                roleGroup.setGroupId(i);
                return roleGroup;
            }).collect(Collectors.toList());
            roleGroupMapper.batchInsert(roleGroups);
        }
        return Result.success("授权成功");
    }

    @Override
    public Result<List<RoleVO>> cusAllList() {
        List<Role> roles = roleMapper.selectList(null);
        List<RoleVO> collect = roles.stream().map(r -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyBeanProp(roleVO, r);
            return roleVO;
        }).collect(Collectors.toList());
        return Result.success(collect);
    }

    @Override
    public Result<List<RoleBaseVO>> getBatchRoleBaseInfo(List<String> userIds) {
        userIds.removeIf(Objects::isNull);
        List<RoleBaseVO> roleBaseVOS = roleMapper.getBatchRoleBaseInfo(userIds);
        return Result.success(roleBaseVOS);
    }

    @Override
    public Result<RoleDetailVO> getRoleById(String id) {
        Role role = roleMapper.selectById(id);
        RoleDetailVO roleDetailVO = new RoleDetailVO();
        BeanUtils.copyBeanProp(roleDetailVO, role);

        Map<String, Object> map = Maps.newHashMap();
        map.put("ROLE_ID", id);
        List<RoleUser> roleUsers = roleUserMapper.selectByMap(map);
        if (CollectionUtil.isNotEmpty(roleUsers)) {
            List<String> userId = roleUsers.stream().map(RoleUser::getUserId).collect(Collectors.toList());
            List<Employee> employees = apiEmployeeService.findEmployees(userId);
            List<RoleDetailVO.RUserVO> rUserVOS = employees.stream().map(e -> {
                RoleDetailVO.RUserVO rUserVO = new RoleDetailVO.RUserVO();
                rUserVO.setName(e.getName());
                rUserVO.setId(e.getId());
                return rUserVO;
            }).collect(Collectors.toList());
            roleDetailVO.setUsers(rUserVOS);
        } else {
            roleDetailVO.setUsers(new ArrayList<>());
        }

        List<RoleGroup> roleGroups = roleGroupMapper.selectByMap(map);
        if (CollectionUtil.isNotEmpty(roleGroups)) {
            List<String> groupIds = roleGroups.stream().map(RoleGroup::getGroupId).collect(Collectors.toList());
            List<Group> groups = groupMapper.selectBatchIds(groupIds);
            List<RoleDetailVO.RGroupVO> rGroupVOList = groups.stream().map(g -> {
                RoleDetailVO.RGroupVO rGroupVO = new RoleDetailVO.RGroupVO();
                rGroupVO.setId(g.getId());
                rGroupVO.setGroupName(g.getGroupName());
                return rGroupVO;
            }).collect(Collectors.toList());
            roleDetailVO.setGroups(rGroupVOList);
        } else {
            roleDetailVO.setGroups(new ArrayList<>());
        }
        return Result.success(roleDetailVO);
    }

    @Override
    protected MybatisCommonBaseMapper<Role> getRepository() {
        return roleMapper;
    }

}
