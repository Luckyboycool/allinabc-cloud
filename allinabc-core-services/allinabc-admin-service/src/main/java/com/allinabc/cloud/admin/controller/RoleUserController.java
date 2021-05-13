package com.allinabc.cloud.admin.controller;

import com.allinabc.cloud.admin.pojo.po.RoleUser;
import com.allinabc.cloud.admin.service.RoleUserService;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/24/21 10:56 PM
 **/
@RestController
@RequestMapping("/role/user")
public class RoleUserController extends MybatisBaseCrudController<RoleUser> {
    @Autowired
    private RoleUserService roleUserService;


    @PostMapping("/insert/batch")
    public Result<Void> insertBath(@RequestBody List<RoleUser> roleUserList) {
        roleUserService.insertBath(roleUserList);
        return Result.success("添加成功");
    }


    @PutMapping("/update/batch")
    public Result<Void> updateBatch(@RequestBody List<RoleUser> roleUsers) {
        roleUserService.updateBatch(roleUsers);
        return Result.success("更新成功");
    }


    @Override
    protected CommonService<RoleUser> getService() {
        return roleUserService;
    }
}
