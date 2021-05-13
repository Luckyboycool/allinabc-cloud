package com.allinabc.cloud.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.allinabc.cloud.admin.mapper.ResourceMapper;
import com.allinabc.cloud.admin.pojo.dto.ResourceSearchDTO;
import com.allinabc.cloud.admin.pojo.po.Resource;
import com.allinabc.cloud.admin.service.ResourceService;
import com.allinabc.cloud.admin.service.UserService;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.enums.UserLoginType;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.api.service.api.ApiEmployeeService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
public class ResourceServiceImpl extends MybatisCommonServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;
    @javax.annotation.Resource
    private UserService sysUserService;
    @javax.annotation.Resource
    private ApiEmployeeService apiEmployeeService;

    @Override
    public List<String> selectPermissionByUser(String appCode, String userId) {
        appCode = Strings.isNullOrEmpty(appCode) ? Constants.DEFAULT_APP : appCode;
        List<Resource> functions = resourceMapper.selectResourcesByUserId(appCode, userId);
        List<String> perms = null != functions && functions.size() > 0 ?
                functions.stream().map(Resource::getPerms).collect(Collectors.toList()) : Lists.newArrayList();

        List<String> newList = Lists.newArrayList();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                newList.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        newList = Lists.newArrayList(Sets.newHashSet(newList));
        return newList;

    }

    @Override
    public List<Resource> selectByUser(String appCode, User user) {

        appCode = Strings.isNullOrEmpty(appCode) ? Constants.DEFAULT_APP : appCode;
        List<Resource> resources;
        // 管理员显示所有菜单信息
        if (sysUserService.checkIsAdmin(user.getId())) {
            resources = resourceMapper.selectResourcesNormalAll(appCode);
        } else {
            resources = resourceMapper.selectResourcesByUserId(appCode, user.getId());
        }
        return resources;
    }

    @Override
    public List<Resource> selectByRole(String appCode, String roleId) {
        appCode = Strings.isNullOrEmpty(appCode) ? Constants.DEFAULT_APP : appCode;
        return resourceMapper.selectResourcesByRoleId(appCode, roleId);
    }

    @Override
    public Result<Void> updateDefault(String id, String defaulted) {
        Resource resource = resourceMapper.selectById(id);
        if (null == resource) {
            log.info("没有该信息，id = {}", id);
            return Result.failed("没有该信息");
        }

        resource.setDefaulted(defaulted);
        resource.setUpdateTm(new Date());
        resource.setUpdatedBy(getCurrentUserId());
        resourceMapper.updateById(resource);
        return Result.success("更新成功");
    }

    @Override
    public Result<List<Tree<String>>> findResultTree() {
        List<Resource> resources = resourceMapper.selectList(null);
        if (CollectionUtil.isEmpty(resources)) {
            return Result.success("查询成功");
        }
        List<Tree<String>> trees = this.handleCustomerTree(resources);
        return Result.success(trees);
    }

    /**
     * 处理自定义树
     * @param resources
     * @return
     */
    private List<Tree<String>> handleCustomerTree(List<Resource> resources) {
        Assert.notEmpty(resources, "resources must not null");

        List<TreeNode<String>> nodeList = resources.stream().map(resource -> {
            TreeNode<String> node = new TreeNode<>();
            node.setId(resource.getId());
            node.setName(resource.getResName());
            node.setParentId(resource.getParentId());
            // Bean转Map
            Map<String, Object> map = BeanUtil.beanToMap(resource);
            node.setExtra(map);
            return node;
        }).collect(Collectors.toList());
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("id");
        config.setNameKey("resName");
        config.setWeightKey("sortNo");

        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(nodeList, "0", config,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setName(treeNode.getName());
                    tree.putExtra("resKey", treeNode.getExtra().get("resKey"));
                    tree.putExtra("resType", treeNode.getExtra().get("resType"));
                    tree.putExtra("path", treeNode.getExtra().get("path"));
                    tree.putExtra("perms", treeNode.getExtra().get("perms"));
                    tree.putExtra("icon", treeNode.getExtra().get("icon"));
                });
        return treeNodes;
    }

    @Override
    public Result<List<Tree<String>>> listEmpByUid(String userId) {
        User currentUser = getCurrentUser();
        String accountType = currentUser.getAccountType();
        userId = currentUser.getId();
        List<Resource> resources = new ArrayList<>();

        if (UserLoginType.SYSTEM.getCode().equals(accountType)) {
            resources = resourceMapper.listResUserByUid(userId);
        }

        if (UserLoginType.EMPLOYEE.getCode().equals(accountType)) {
            resources = resourceMapper.listResUserByUid(userId);
            // 部门下的权限
            String deptId = apiEmployeeService.getByUserId(userId);
            if (StringUtils.isNotEmpty(deptId)) {
                List<Resource> resourcesDept = resourceMapper.listResDeptById(deptId);
                resources.addAll(CollectionUtils.isEmpty(resourcesDept) ? Collections.EMPTY_LIST: resourcesDept);
            }
            //群组下的权限
            List<Resource> resourcesGroup = resourceMapper.listResGroupByUserId(userId);
            resources.addAll(CollectionUtils.isEmpty(resourcesGroup) ? Collections.EMPTY_LIST : resourcesGroup);

            //群组All的权限
            List<Resource> resourcesGroupAll = this.handleGroupAll();
            resources.addAll(CollectionUtils.isEmpty(resourcesGroupAll) ? Collections.EMPTY_LIST : resourcesGroupAll);

            // 去重
            resources = resources.stream().distinct().collect(Collectors.toList());
        }


        if (UserLoginType.ACCOUNT.getCode().equals(accountType)) {
            resources = resourceMapper.listResUserByUid(userId);
        }
        List<Tree<String>> trees = this.handleCustomerTree(resources);
        return Result.success(trees);
    }

    /**
     * 处理GroupAll的权限
     * @return
     */
    private List<Resource> handleGroupAll() {
        return resourceMapper.searchGroupAll(Constants.GROUP_ALL);
    }

    @Override
    public IPage<Resource> listPage(ResourceSearchDTO searchDTO) {
        Page<Resource> page = new Page<>();
        page.setCurrent(searchDTO.getPageNum());
        page.setSize(searchDTO.getPageSize());
        IPage<Resource> iPage = resourceMapper.pageBySearch(page, searchDTO.getResKey(), searchDTO.getResName(), searchDTO.getPath());
        return iPage;
    }

    @Override
    public Result<List<Tree<String>>> listByUid(String userId) {
        User currentUser = getCurrentUser();
        String accountType = currentUser.getAccountType();
        List<Resource> resources = new ArrayList<>();
        if (UserLoginType.SYSTEM.getCode().equals(accountType)
                || UserLoginType.EMPLOYEE.getCode().equals(accountType)) {
            QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("VISIBLE", 0);
            resources = resourceMapper.selectList(queryWrapper);
        }
        if (UserLoginType.ACCOUNT.getCode().equals(accountType)) {
            resources = resourceMapper.listResUserByUid(userId);
        }
        List<Tree<String>> trees = this.handleCustomerTree(resources);
        return Result.success(trees);
    }

    @Override
    public Result<List<Tree<String>>> listPageNoDefault(String name) {
        QueryWrapper<Resource> wrapper = new QueryWrapper();
        wrapper.eq("VISIBLE", 0);
        wrapper.eq("DEFAULTED", 0);
        if (StrUtil.isNotBlank(name)) {
            wrapper.like("UPPER(RES_NAME)", name.toUpperCase());
        }
        List<Resource> resources = resourceMapper.selectList(wrapper);
        List<Resource> resourcesAll = resourceMapper.selectList(null);
        List<Resource> resourceResult = new ArrayList<>();
        this.childParentList(resourcesAll, resources, resourceResult);
        resources.addAll(resourceResult);
        resources = resources.stream().distinct().collect(Collectors.toList());
        List<Tree<String>> trees = this.handleCustomerTree(resources);
        return Result.success(trees);
    }


    /**
     * 递归获得 所有上级资源列表
     * @param resourceAllList 所有资源列表
     * @param resourceChildList 子资源列表
     * @param result 最后结果
     * @return
     */
    public void childParentList(List<Resource> resourceAllList,
                                List<Resource> resourceChildList,
                                List<Resource> result) {

        for (Resource child : resourceChildList) {
            if (child.getParentId().equals("0")) {
                result.add(child);
                continue;
            }

            Resource resource = resourceAllList.stream()
                    .filter(department -> child.getParentId().equals(String.valueOf(department.getId())))
                    .findFirst()
                    .get();

            result.add(resource);

            if (!resource.getParentId().equals("0")) {
                childParentList(resourceAllList, Arrays.asList(resource), result);
            }
        }

    }

    @Override
    protected MybatisCommonBaseMapper<Resource> getRepository() {
        return resourceMapper;
    }

}
