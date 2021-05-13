package com.allinabc.cloud.admin.controller;


import cn.hutool.core.lang.tree.Tree;
import com.allinabc.cloud.admin.pojo.dto.ResourceSearchDTO;
import com.allinabc.cloud.admin.pojo.po.Resource;
import com.allinabc.cloud.admin.service.ResourceService;
import com.allinabc.cloud.common.auth.annotation.HasPermissions;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.common.web.enums.ApiResultCode;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import com.allinabc.common.mybatis.util.PageHelper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2020-12-16
 */
@Api(tags = "功能项管理")
@RestController
@RequestMapping("/resources")
@BizClassification(serviceName = "admin",modelName = "menu",bizName = "功能项管理")
public class ResourceController extends MybatisBaseCrudController<Resource> {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/list/perms/{userId}/{appCode}")
    public Result<List<String>> listPermissions(@PathVariable("appCode") String appCode, @PathVariable("userId") String userId) {
        List<String> permissions = resourceService.selectPermissionByUser(appCode, userId);
        return Result.success(permissions, "查询成功");
    }

    @GetMapping("/list/user/{appCode}")
    public Result<List<Resource>> listResources(@PathVariable("appCode") String appCode) {
        User user = resourceService.getCurrentUser();
        List<Resource> resources = resourceService.selectByUser(appCode, user);
        return Result.success(resources, "查询成功");
    }

    @GetMapping("/list/role/{roleId}/{appCode}")
    @HasPermissions("system:menu:list")
    public Result listResources(@PathVariable("appCode") String appCode, @PathVariable("roleId") String roleId) {
        if (Strings.isNullOrEmpty(roleId)) {
            return Result.failed(ApiResultCode.FAILED);
        }
        List<Resource> resources = resourceService.selectByRole(appCode, roleId);
        return Result.success(resources, "查询成功");
    }

    /**
     * 更新默认功能项
     * @param id
     * @param defaulted
     * @return
     */
    @ApiOperation(value = "更新默认功能项")
    @PutMapping("/update_default")
    public Result<Void> updateDefault(@RequestParam("id") String id,
                                      @RequestParam("defaulted") String defaulted) {
        return resourceService.updateDefault(id, defaulted);
    }

    /**
     * 查询所有功能项(Tree)
     * @return
     */
    @ApiOperation(value = "查询所有功能项(Tree)")
    @PostMapping("/find_result_tree")
    public Result<List<Tree<String>>> findResultTree() {
        return resourceService.findResultTree();
    }

    /**
     * 查询登录下的资源列表
     * 用于页面加载时初始化的资源列表
     * @return
     */
    @ApiOperation("查询登录下的资源列表")
    @GetMapping("/list_emp")
    public Result<List<Tree<String>>> listEmpByUid() {
        return resourceService.listEmpByUid(resourceService.getCurrentUserId());
    }

    /**
     * 查询功能项列表
     * @param searchDTO
     * @return
     */
    @ApiOperation(value = "查询功能项列表")
    @PostMapping("/list_page")
    public Result<Page<Resource>> listPage(@RequestBody ResourceSearchDTO searchDTO) {
        IPage<Resource> page = resourceService.listPage(searchDTO);
        return Result.success(PageHelper.convert(page), "查询成功");
    }

    /**
     * 查询功能项列表(非默认)TREE
     * @return
     */
    @ApiOperation(value = "查询功能项列表(非默认)")
    @GetMapping("/list_page_no_default")
    public Result<List<Tree<String>>> listPageNoDefault(@RequestParam("name") String name) {
        return resourceService.listPageNoDefault(name);
    }


    /**
     * 查询功能项列表(用于客户授权使用)
     * 管理员下 查看所有资源
     * 客户下 查看对应资源
     * @param userId
     * @return
     */
    @ApiOperation(value = "查询资源列表(用于客户授权使用)")
    @GetMapping("/list_by_uid/{userId}")
    public Result<List<Tree<String>>> listByUid(@PathVariable("userId") String userId) {
        return resourceService.listByUid(resourceService.getCurrentUserId());
    }

    @Override
    protected CommonService<Resource> getService() {
        return resourceService;
    }

}
