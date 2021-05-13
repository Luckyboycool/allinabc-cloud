package com.allinabc.cloud.admin.controller;


import com.allinabc.cloud.admin.pojo.dto.RoleAddDTO;
import com.allinabc.cloud.admin.pojo.dto.RoleAuthorizeDTO;
import com.allinabc.cloud.admin.pojo.dto.RoleSearchDTO;
import com.allinabc.cloud.admin.pojo.dto.RoleUpdateDTO;
import com.allinabc.cloud.admin.pojo.po.Role;
import com.allinabc.cloud.admin.pojo.vo.RoleBaseVO;
import com.allinabc.cloud.admin.pojo.vo.RoleDetailVO;
import com.allinabc.cloud.admin.pojo.vo.RoleVO;
import com.allinabc.cloud.admin.service.RoleService;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import com.allinabc.common.mybatis.util.PageHelper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
@BizClassification(serviceName = "admin",modelName = "role",bizName = "角色管理")
public class RoleController extends MybatisBaseCrudController<Role> {

    @Autowired
    private RoleService roleService;

    @PutMapping("/update/status/{id}/{status}")
    public Result<Void> updateStatus(@PathVariable String status, @PathVariable String id) {
        roleService.updateStatus(id, status);
        return Result.success("修改状态成功");
    }

    /**
     * 查询用户的角色列表
     * 远程提供接口
     * @param userId
     * @return
     */
    @GetMapping("/get_role_base_info/{userId}")
    public Result<List<RoleBaseVO>> getRoleBaseInfo(@PathVariable("userId") String userId) {
        return roleService.getRoleBaseInfo(userId);
    }

    /**
     * 查询多个用户的角色列表
     * 远程调用
     * @param userIds
     * @return
     */
    @GetMapping("/get_batch_role_base_info")
    public Result<List<RoleBaseVO>> getBatchRoleBaseInfo(@RequestParam("userIds") List<String> userIds) {
        return roleService.getBatchRoleBaseInfo(userIds);
    }

    /**
     * 添加角色
     * @param addDTO
     * @return
     */
    @ApiOperation(value = "新增角色信息")
    @PostMapping("/cus/insert")
    public Result<Void> cusInsert(@RequestBody @Validated RoleAddDTO addDTO) {
        return roleService.cusInsert(addDTO);
    }

    /**
     * 更新角色
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "更新角色信息")
    @PutMapping("/cus/update")
    public Result<Void> cusUpdate(@RequestBody @Validated RoleUpdateDTO updateDTO) {
        return roleService.cusUpdate(updateDTO);
    }
    /**
     * 查询角色列表
     * @param searchDTO
     * @return
     */
    @ApiOperation(value = "查询角色")
    @PostMapping("/cus/page")
    public Result<Page<RoleVO>> cusPageList(@RequestBody RoleSearchDTO searchDTO) {
        IPage<RoleVO> iPage = roleService.cusPageList(searchDTO);
        Page page = PageHelper.convert(iPage);
        return Result.success(page, "查询成功");
    }

    @ApiOperation(value = "查询角色列表(不分页)")
    @GetMapping("/cus/all_list")
    public Result<List<RoleVO>> cusAllList() {
        return roleService.cusAllList();
    }

    /**
     * 角色授权
     * @param authorizeDTO
     * @return
     */
    @ApiOperation(value = "角色授权")
    @PostMapping("/cus/authorize")
    public Result<Void> cusAuthorize(@RequestBody @Validated RoleAuthorizeDTO authorizeDTO) {
        return roleService.cusAuthorize(authorizeDTO);
    }

    /**
     * 查询角色详情
     * @param id
     * @return
     */
    @ApiOperation(value = "查询角色详情")
    @GetMapping("/get_role_id")
    public Result<RoleDetailVO> getRoleById(@RequestParam("id") String id) {
        return roleService.getRoleById(id);
    }
    @Override
    protected CommonService<Role> getService() {
        return roleService;
    }

}
