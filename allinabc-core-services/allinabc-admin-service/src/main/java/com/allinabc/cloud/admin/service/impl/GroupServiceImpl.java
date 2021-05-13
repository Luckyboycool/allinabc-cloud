package com.allinabc.cloud.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.allinabc.cloud.admin.mapper.GroupDeptMapper;
import com.allinabc.cloud.admin.mapper.GroupMapper;
import com.allinabc.cloud.admin.mapper.GroupUserMapper;
import com.allinabc.cloud.admin.pojo.dto.*;
import com.allinabc.cloud.admin.pojo.po.Group;
import com.allinabc.cloud.admin.pojo.po.GroupDept;
import com.allinabc.cloud.admin.pojo.po.GroupUser;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.admin.pojo.vo.GroupDetailVO;
import com.allinabc.cloud.admin.pojo.vo.GroupListVO;
import com.allinabc.cloud.admin.service.GroupService;
import com.allinabc.cloud.admin.service.UserService;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.common.core.utils.ListHelper;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.core.utils.bean.BeanUtils;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.api.service.api.ApiEmployeeService;
import com.allinabc.cloud.org.api.service.api.ApiOrgService;
import com.allinabc.cloud.org.pojo.po.Employee;
import com.allinabc.cloud.org.pojo.vo.DeptBaseVO;
import com.allinabc.cloud.org.pojo.vo.EmployeeBasicVO;
import com.allinabc.cloud.starter.web.util.BeanMergeUtils;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  代码迁移 + mybatis-plus修改sql
 * </p>
 *
 * @since 2020-12-17
 */
@Service
@Slf4j
public class GroupServiceImpl extends MybatisCommonServiceImpl<GroupMapper, Group> implements GroupService {

    @Resource
    private GroupMapper groupMapper;
    @Resource
    private UserService userService;

    @Resource
    private GroupUserMapper groupUserMapper;

    @Resource
    private ApiOrgService apiOrgService;

    @Resource
    private GroupDeptMapper groupDeptMapper;

    @Resource
    private ApiEmployeeService apiEmployeeService;




    @Override
    @Transactional(rollbackFor = Exception.class)
    public Group updateGroup(String id, Group entity) {
        Group dbEntity = this.selectById(id);
        BeanMergeUtils.merge(entity, dbEntity);
        dbEntity.setUpdateTm(new Date());
        dbEntity.setUpdatedBy(getCurrentUserId());
        //处理中间表数据 --start
        handleUpdateGroupUser(dbEntity);
        //处理中间表数据 --end
        //改造成mybatis updateDate：2020-12-17
        int var1 = groupMapper.updateById(dbEntity);
        if(var1!=1){
            throw new BusinessException("修改group失败");
        }
        return dbEntity;
    }


    public void handleUpdateGroupUser(Group entity) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("GROUP_ID", entity.getId());
        groupUserMapper.deleteByMap(map);
        this.batchInsertGroupUser(entity);

        groupDeptMapper.deleteByMap(map);
        this.batchInsertGroupDept(entity);
    }

    private void batchInsertGroupUser(Group entity) {
        List<SysUserDTO> members = entity.getMembers();
        if (CollectionUtil.isNotEmpty(members)) {
            members.removeIf(a -> StringUtils.isEmpty(a.getName())||StringUtils.isEmpty(a.getId()));
            List<GroupUser> groupUsers = members.stream().map(i -> {
                GroupUser groupUser = new GroupUser();
                groupUser.setGroupId(entity.getId());
                groupUser.setUserId(i.getId());
                groupUser.setGroupName(entity.getGroupName());
                return groupUser;
            }).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(groupUsers)) {
                groupUserMapper.batchInsert(groupUsers);
            }
        }

    }

    private void batchInsertGroupDept(Group entity) {
        List<GroupDeptDTO> depts = entity.getDepts();
        if (CollectionUtil.isNotEmpty(depts)) {
            depts.removeIf(a -> StringUtils.isEmpty(a.getId()) || StringUtils.isEmpty(a.getName()));
            List<GroupDept> groupDepts = depts.stream().map(i -> {
                GroupDept groupDept = new GroupDept();
                groupDept.setGroupId(entity.getId());
                groupDept.setDeptId(i.getId());
                groupDept.setGroupName(entity.getGroupName());
                return groupDept;
            }).collect(Collectors.toList());
            groupDeptMapper.batchInsert(groupDepts);
        }
    }

    /**
     * 需要重写
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Group updateUser(String id, Group entity) {
        Group dbEntity = this.selectById(id);
        List<SysUserDTO> members = entity.getMembers();
        if (CollectionUtil.isNotEmpty(members)) {
            List<String> memberIdList = members.stream().map(SysUserDTO::getId).collect(Collectors.toList());
            List<String> memberIds = ListHelper.singleton(memberIdList);
            List<SysUser> sysUsers = userService.selectByIds(memberIds);
            List<SysUserDTO> collect1 = sysUsers.stream().map(a -> {
                SysUserDTO sysUserDTO = new SysUserDTO();
                sysUserDTO.setId(a.getId());
                sysUserDTO.setName(a.getUserName());
                return sysUserDTO;
            }).collect(Collectors.toList());
            dbEntity.setMembers(collect1);
        }
        dbEntity.setUpdateTm(new Date());
        dbEntity.setUpdatedBy(getCurrentUserId());
        handleUpdateGroupUser(dbEntity);
        //重新此方法 updateDate：2020-12-17
        int var = groupMapper.updateById(dbEntity);
        if(var!=1){
            log.error("更新用户失败");
            throw new BusinessException("更新用户失败");
        }
        return dbEntity;
    }

    @Override
    public List<Group> selectList(Group entity) {
       // Specification<SysGroup> specification = this.createSpecification(entity);
        //改造成mybatis updateDate：2020-12-17
        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(entity.getGroupName())) {
            queryWrapper.like("UPPER(GROUP_NAME)", entity.getGroupName().toUpperCase());
        }
        return groupMapper.selectList(queryWrapper);
    }

    @Override
    public List<Group> selectByUser(String userId) {
        if(Strings.isNullOrEmpty(userId)) {
            return null;
        }
        //return groupDao.selectByUser(userId);
        //改造成mybatis updateDate：2020-12-17
        return  groupMapper.selectGroupInfoByUserId(userId);
    }

    // TODO 跨服务调用
    @Override
    public Result<GroupDetailVO> getByGroupId(String id) {
        Group group = groupMapper.selectById(id);
        if (null == group) {
            return Result.failed("没有该信息");
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("GROUP_ID", id);
        List<GroupDept> groupDepts = groupDeptMapper.selectByMap(map);
        List<GroupUser> groupUsers = groupUserMapper.selectByMap(map);

        GroupDetailVO groupDetailVO = new GroupDetailVO();
        BeanUtils.copyBeanProp(groupDetailVO, group);

        if (CollectionUtil.isNotEmpty(groupDepts)) {
            List<String> deptIds = groupDepts.stream().map(GroupDept::getDeptId).collect(Collectors.toList());
            List<DeptBaseVO> deptList = apiOrgService.findBaseList(deptIds);
            groupDetailVO.setDeptList(deptList.stream().map(a-> {
                GroupDetailVO.GroupDeptDetailVO deptDetailVO = new GroupDetailVO.GroupDeptDetailVO();
                deptDetailVO.setId(a.getDepartId());
                deptDetailVO.setName(a.getDepartName());
                return deptDetailVO;
            }).collect(Collectors.toList()));
        }

        if (CollectionUtil.isNotEmpty(groupUsers)) {
            List<String> userIds = groupUsers.stream().map(GroupUser::getUserId).collect(Collectors.toList());
            List<Employee> employees = apiEmployeeService.findEmployees(userIds);
            groupDetailVO.setUserList(employees.stream().map(a-> {
                GroupDetailVO.GroupUserDetailVO userDetailVO = new GroupDetailVO.GroupUserDetailVO();
                userDetailVO.setId(a.getId());
                userDetailVO.setName(a.getName()+"("+a.getJobNumber()+")");
                return userDetailVO;
            }).collect(Collectors.toList()));
        }

        return Result.success(groupDetailVO, "查询成功");
    }

    @Override
    public IPage<GroupListVO> findCusList(GroupSearchDTO searchDTO) {
        Page<GroupListVO> page = new Page<>();
        page.setCurrent(searchDTO.getPageNum());
        page.setSize(searchDTO.getPageSize());
        IPage<GroupListVO> iPage = groupMapper.findPage(page, searchDTO);
        iPage.getRecords().stream().forEach(a-> {
            String usernames = a.getUsernames();
            if (StringUtils.isNotEmpty(usernames)) {
                a.setUsernameList(Arrays.asList(usernames.split(",")));
            }
            String depts = a.getDepts();
            if (StringUtils.isNotEmpty(depts)) {
                a.setDeptList(Arrays.asList(depts.split(",")));
            }
        });
        return iPage;
    }

    @Override
    public Result<List<Group>> findAllList(String name) {
        QueryWrapper<Group> wrapper = new QueryWrapper();
        wrapper.eq("IS_AVAILABLE", "Y");
        wrapper.eq("IS_HIDDEN", "N");
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("UPPER(GROUP_NAME)", name.toUpperCase());
        }
        List<Group> groups = groupMapper.selectList(wrapper);
        // 添加一个群组All,此群组包含所有员工
        Group group = new Group();
        group.setId(Constants.GROUP_ALL);
        group.setGroupName(Constants.GROUP_ALL_NAME);
        groups.add(group);

        return Result.success(groups, "查询成功");
    }

    @Override
    public Result<List<EmployeeBasicVO>> findEmployeeByGroupName(String groupName) {
        QueryWrapper<Group> groupWrapper = new QueryWrapper<>();
        groupWrapper.eq("GROUP_NAME", groupName);
        Group group = groupMapper.selectOne(groupWrapper);
        if (group == null) {
            return Result.success(Collections.EMPTY_LIST);
        }
        QueryWrapper<GroupUser> groupUserWrapper = new QueryWrapper<>();
        groupUserWrapper.eq("GROUP_ID", group.getId());
        List<GroupUser> groupUsers = groupUserMapper.selectList(groupUserWrapper);
        if (CollectionUtil.isEmpty(groupUsers)) {
            return Result.success(Collections.EMPTY_LIST);
        }

        List<String> userIds = groupUsers.stream().map(GroupUser::getUserId).collect(Collectors.toList());
        List<Employee> employees = apiEmployeeService.findEmployees(userIds);
        List<EmployeeBasicVO> employeeList = employees.stream().map(e -> {
            EmployeeBasicVO employeeBasicVO = new EmployeeBasicVO();
            employeeBasicVO.setUserId(e.getId());
            employeeBasicVO.setUsername(e.getName());
            return employeeBasicVO;
        }).collect(Collectors.toList());

        return Result.success(employeeList);
    }

    /**
     * 检查群组是否存在
     * @param groupName
     * @return
     */
    private boolean checkGroupNameIsExist(String groupName) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("GROUP_NAME", groupName);
        Group group = groupMapper.selectOne(wrapper);
        if (null != group) {
            return true;
        }
        return false;
    }
    @Transactional
    @Override
    public Result<Void> cusInsert(GroupInsertDTO insertDTO) {
        Group group = new Group();
        BeanUtils.copyBeanProp(group, insertDTO);
        group.setMembers(insertDTO.getMembers());
        group.setDepts(insertDTO.getDeptDtoList());
        String groupName = group.getGroupName();
        if (this.checkGroupNameIsExist(groupName)) {
            return Result.failed("群组名已存在");
        }

        List<SysUserDTO> members = group.getMembers();
        if (CollectionUtil.isNotEmpty(members)) {
            List<String> collect = members.stream().map(SysUserDTO::getId).collect(Collectors.toList());
            List<String> memberIds = ListHelper.singleton(collect);
            List<Employee> employees = apiEmployeeService.findEmployees(memberIds);
            List<SysUserDTO> collect1 = employees.stream().map(a -> {
                SysUserDTO sysUserDTO = new SysUserDTO();
                sysUserDTO.setId(a.getId());
                sysUserDTO.setName(a.getName());
                return sysUserDTO;
            }).collect(Collectors.toList());
            group.setMembers(collect1);
        }

        group.setCreatedBy(getCurrentUserId());
        group.setCreateTm(new Date());
        group.setId(IdUtil.getSnowflake(0, 0).nextIdStr());

        // 处理 GroupUser
        this.batchInsertGroupUser(group);

        // 处理GroupDept
        this.batchInsertGroupDept(group);
        groupMapper.insert(group);
        return Result.success("添加成功");
    }

    @Transactional
    @Override
    public Result<Void> cusUpdate(GroupUpdateDTO updateDTO) {
        Group group = new Group();
        BeanUtils.copyBeanProp(group, updateDTO);
        group.setMembers(updateDTO.getMembers());
        group.setDepts(updateDTO.getDepts());
        String id = group.getId();

        Group groupRes = groupMapper.selectById(id);
        if (null == groupRes) {
            return Result.failed("数据不存在");
        }
        group.setGroupName(groupRes.getGroupName());
        List<SysUserDTO> members = group.getMembers();
        if (CollectionUtil.isNotEmpty(members)) {
            List<String> collect = members.stream().map(SysUserDTO::getId).collect(Collectors.toList());
            List<String> memberIds = ListHelper.singleton(collect);
            List<Employee> employees = apiEmployeeService.findEmployees(memberIds);
            List<SysUserDTO> collect1 = employees.stream().map(a -> {
                SysUserDTO sysUserDTO = new SysUserDTO();
                sysUserDTO.setId(a.getId());
                sysUserDTO.setName(a.getName());
                return sysUserDTO;
            }).collect(Collectors.toList());
            group.setMembers(collect1);
        }


        group.setUpdateTm(new Date());
        group.setUpdatedBy(getCurrentUserId());
        //处理中间表数据 --start
        handleUpdateGroupUser(group);
        //处理中间表数据 --end
        //改造成mybatis updateDate：2020-12-17
        int var1 = groupMapper.updateById(group);
        if(var1!=1){
            throw new BusinessException("修改group失败");
        }
        return Result.success("更新成功");
    }

    @Override
    public boolean beforeDeleteById(String id) {
        super.beforeDeleteById(id);

        long userCount = groupMapper.countByUser(id);
        Assert.isTrue(userCount == 0, "群组已分配用户，不能删除");
        return true;
    }

    @Override
    protected MybatisCommonBaseMapper<Group> getRepository() {
        return groupMapper;
    }

}
