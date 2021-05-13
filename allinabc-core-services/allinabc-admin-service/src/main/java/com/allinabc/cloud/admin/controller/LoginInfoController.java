package com.allinabc.cloud.admin.controller;


import com.allinabc.cloud.admin.mapper.LoginInfoMapper;
import com.allinabc.cloud.admin.pojo.dto.LoginInfoDTO;
import com.allinabc.cloud.admin.pojo.dto.LoginInfoSearchDTO;
import com.allinabc.cloud.admin.pojo.po.LoginInfo;
import com.allinabc.cloud.admin.service.LoginInfoService;
import com.allinabc.cloud.common.auth.annotation.HasPermissions;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.core.utils.bean.BeanUtils;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.common.web.annotation.BizClassification;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.log.annotation.OperLog;
import com.allinabc.cloud.starter.log.enums.BusinessType;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import com.allinabc.common.mybatis.util.PageHelper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-17
 */
@Api(tags = "访问记录")
@RestController
@RequestMapping("/loginInfo")
@BizClassification(serviceName = "monitor",modelName = "logininfor",bizName = "访问记录")
public class LoginInfoController extends MybatisBaseCrudController<LoginInfo> {

    @Autowired
    private LoginInfoService loginInfoService;
    @Resource
    private LoginInfoMapper loginInfoMapper;

    @PostMapping("/add")
    public Result<Void> insertLoginLog(@RequestBody LoginInfoDTO loginInfoDto) {
        LoginInfo loginInfo = new LoginInfo();
        BeanUtils.copyBeanProp(loginInfo,loginInfoDto);
        LoginInfo  lastInfo = loginInfoMapper.lastSuccessInfo(loginInfo.getUserId(), loginInfo.getUserType());
        if (Constants.SUCCESS.equals(loginInfoDto.getStatus())) {
            if (lastInfo != null) {
                loginInfo.setLastLoginTime(lastInfo.getLoginTime());
            }
        }
        if (Constants.LOGOUT_SUCCESS.equals(loginInfoDto.getStatus())) {
            if (lastInfo != null) {
                loginInfoMapper.updateLogoutTime(lastInfo.getId());
            }
        }
        loginInfoService.save(loginInfo);
        return Result.success("添加成功");
    }

    @DeleteMapping("/remove/{ids}")
    @HasPermissions("monitor:logininfor:remove")
    @OperLog(title = "访问日志", businessType = BusinessType.DELETE)
    public Result<Void> delete(@PathVariable String ids) {
        loginInfoService.deleteByIds(ids);
        return Result.success("删除成功");
    }

    @DeleteMapping("/clean")
    @HasPermissions("monitor:logininfor:remove")
    @OperLog(title = "访问日志", businessType = BusinessType.CLEAN)
    public Result<Void> clean() {
        loginInfoService.cleanLoginInfo();
        return Result.success("清空访问日志成功");
    }

    /**
     * 查询所有日志列表
     * @param searchDTO
     * @return
     */
    @ApiOperation(value = "查询所有日志列表")
    @PostMapping("/page/list")
    public Result<Page<LoginInfo>> pageList(@RequestBody LoginInfoSearchDTO searchDTO) {
        IPage<LoginInfo> page = loginInfoService.pageList(searchDTO);
        return Result.success(PageHelper.convert(page), "查询成功");
    }

    /**
     * 查询当前用户登录成功后的上一次记录
     * @return
     */
    @ApiOperation(value = "查询当前用户登录成功后的上一次记录")
    @GetMapping("/get_last")
    public Result<LoginInfo> getByUsername() {
        return loginInfoService.getLastByUsername();
    }



    @Override
    protected CommonService<LoginInfo> getService() {
        return loginInfoService;
    }

}
