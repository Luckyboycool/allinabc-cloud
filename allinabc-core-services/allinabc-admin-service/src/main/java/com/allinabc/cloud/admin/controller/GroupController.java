package com.allinabc.cloud.admin.controller;


import com.allinabc.cloud.admin.mapper.GroupMapper;
import com.allinabc.cloud.admin.pojo.dto.GroupInsertDTO;
import com.allinabc.cloud.admin.pojo.dto.GroupSearchDTO;
import com.allinabc.cloud.admin.pojo.dto.GroupUpdateDTO;
import com.allinabc.cloud.admin.pojo.po.Group;
import com.allinabc.cloud.admin.pojo.vo.GroupDetailVO;
import com.allinabc.cloud.admin.pojo.vo.GroupListVO;
import com.allinabc.cloud.admin.service.GroupService;
import com.allinabc.cloud.common.auth.annotation.HasPermissions;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.vo.EmployeeBasicVO;
import com.allinabc.cloud.starter.log.annotation.OperLog;
import com.allinabc.cloud.starter.log.enums.BusinessType;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import com.allinabc.common.mybatis.util.PageHelper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  代码迁移以及改造
 * </p>
 *
 * @since 2020-12-17
 */
@Api(tags = "群组管理")
@RestController
@RequestMapping("/group")
@BizClassification(serviceName = "admin",modelName = "group",bizName = "群组管理")
public class GroupController extends MybatisBaseCrudController<Group> {

    @Autowired
    private GroupService groupService;
    @Resource
    private GroupMapper groupMapper;


    /**
     * 添加群组
     * @date 2021-03-09 16:53:04
     * @param insertDTO
     * @return
     */
    @ApiOperation(value = "添加群组")
    @PostMapping("/cus/insert")
    public Result<Void> cusInsert(@RequestBody @Validated GroupInsertDTO insertDTO) {

        return groupService.cusInsert(insertDTO);
    }



    /**
     * 更新群组
     * @date 2021-03-09 17:11:48
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "更新群组")
    @PutMapping("/cus/update")
    public Result<Void> cusUpdate(@RequestBody @Validated GroupUpdateDTO updateDTO) {

        return groupService.cusUpdate(updateDTO);
    }



    @PutMapping("/update/user/{id}")
    @HasPermissions("system:group:edit")
    @OperLog(title = "群组管理", businessType = BusinessType.UPDATE)
    public Result<Void> updateUser(@PathVariable String id, @RequestBody Group entity){
        groupService.updateUser(id,entity);
        return Result.success("修改成功");
    }

    @GetMapping("/select/user/{userId}")
    public List<Group> selectByUser(@PathVariable String userId) {
        return groupService.selectByUser(userId);
    }

    /**
     * 查询群组详情
     * @param id
     * @return
     */
    @ApiOperation(value = "查询群组详情")
    @GetMapping("/get_by_group_id")
    public Result<GroupDetailVO> getByGroupId(@RequestParam("id") String id) {
        return groupService.getByGroupId(id);
    }


    @ApiOperation(value = "群组列表(分页)", notes = "")
    @PostMapping("/cus/list")
    public Result<Page<GroupListVO>> findCusList(@RequestBody GroupSearchDTO searchDTO) {
        IPage<GroupListVO> page = groupService.findCusList(searchDTO);
        return Result.success(PageHelper.convert(page), "查询成功");
    }

    /**
     * 群组列表(不分页)
     * @return
     */
    @ApiOperation(value = "群组列表(不分页)")
    @GetMapping("/cus/all_list")
    public Result<List<Group>> findAllList(@RequestParam(required = false) String name) {
        return groupService.findAllList(name);
    }


    /**
     * 查询群组下的员工列表
     * @param groupName
     * @return
     */
    @ApiOperation(value = "查询群组下的员工列表")
    @GetMapping("/find_employees")
    public Result<List<EmployeeBasicVO>> findEmployeeByGroupName(@RequestParam("groupName")String groupName) {
        return groupService.findEmployeeByGroupName(groupName);
    }

    @Override
    protected CommonService<Group> getService() {
        return groupService;
    }

}
