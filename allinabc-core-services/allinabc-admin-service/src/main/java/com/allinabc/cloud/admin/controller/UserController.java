package com.allinabc.cloud.admin.controller;


import com.allinabc.cloud.admin.pojo.dto.UserChangePwd;
import com.allinabc.cloud.admin.pojo.dto.UserDTO;
import com.allinabc.cloud.admin.pojo.po.SysUser;
import com.allinabc.cloud.admin.service.ResourceService;
import com.allinabc.cloud.admin.service.UserService;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.core.utils.bean.BeanUtils;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import io.swagger.annotations.Api;
import org.assertj.core.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2020-12-16
 */
@Api(tags = "用户服务")
@RestController
@RequestMapping("/user")
@BizClassification(serviceName = "admin",modelName = "user",bizName = "用户管理")
public class UserController extends MybatisBaseCrudController<SysUser> {

    @Resource
    private UserService     userService;
    @Resource
    private ResourceService sysResourceService;


//    @PutMapping("/update/login")
//    public Result<?> updateLogin(@RequestBody User entity) {
//        sysUserService.updateLoginInfo(entity);
//        return Result.success("修改成功");
//    }

    @PutMapping("/update/user/{id}")
    public Result<?> updateUser(@RequestBody SysUser entity, @PathVariable String id) {
        SysUser sysUser = userService.updateUser(id, entity);
        return Result.success("用户" + sysUser.getUserName() + "密码修改成功");
    }

    @PutMapping("/update/password/{id}")
    public Result updatePassword(@RequestBody UserChangePwd userPwd, @PathVariable String id) {
        SysUser entity = userService.updatePassword(id, userPwd);
        return Result.success("用户" + entity.getUserName() + "密码修改成功");
    }

    @PutMapping("/update/status/{id}/{status}")
    public Result updateStatus(@PathVariable String status, @PathVariable String id) {
        userService.updateStatus(id, status);
        return Result.success("修改成功");
    }

//    @GetMapping("/get/name/{userName}")
//    public Result getByName(@PathVariable String userName) {
//        User entity = sysUserService.selectByUserName(userName);
//        return Result.success(entity);
//    }

    @GetMapping("/get/role/{roleIds}")
    public Result getIdByRole(@PathVariable String roleIds) {
        List<String> userIds = userService.selectByRoleIds(roleIds);
        return Result.success(userIds);
    }

    @GetMapping("/get/info/{appCode}")
    public Result listPermission(@PathVariable("appCode")String appCode) {
        com.allinabc.cloud.common.core.domain.User user = userService.getCurrentUser();
        if(null == user || Strings.isNullOrEmpty(user.getId())) {
            return Result.failed("can not find user with token");
        }
        List<String> permissions = sysResourceService.selectPermissionByUser(appCode, user.getId());
        if(null == permissions || permissions.size() == 0) {
            return Result.failed("can not find any permission");
        }
        return Result.success(permissions, "load permission success");
    }

    @Override
    public Result remove(@PathVariable("ids") String ids) {
        userService.remove(ids);
        return Result.success("delete success");
    }

    /*api接口*/
    @GetMapping("/get/id/{id}")
    public Result<SysUser> get(@PathVariable("id") String userId) {
        return super.get(userId);
    }

    @GetMapping("/get/name/{userName}")
    public Result<SysUser> getUserByName(@PathVariable("userName") String userName) {
        SysUser entity = userService.selectByUserName(userName);
        return Result.success(entity);
    }

    @PutMapping("/update/login")
    public Result<Void> updateLoginRecord(@RequestBody UserDTO userDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyBeanProp(sysUser,userDTO);
        userService.updateLoginInfo(sysUser);
        return Result.success("修改成功");
    }

    @Override
    protected CommonService<SysUser> getService() {
        return userService;
    }
}
