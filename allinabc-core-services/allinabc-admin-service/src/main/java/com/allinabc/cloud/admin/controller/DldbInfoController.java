package com.allinabc.cloud.admin.controller;

import com.allinabc.cloud.admin.pojo.dto.DldbSendDTO;
import com.allinabc.cloud.admin.pojo.dto.DldbUpdateDTO;
import com.allinabc.cloud.admin.pojo.vo.DldbSingleVO;
import com.allinabc.cloud.admin.pojo.vo.DldbVO;
import com.allinabc.cloud.admin.service.DldbInfoService;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.util.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Simon.Xue
 * @date 2021/4/6 2:29 下午
 **/
@Api(tags = "产品信息")
@RestController
@RequestMapping(value = "/dldb")
public class DldbInfoController {

    @Resource
    private DldbInfoService dldbInfoService;


    /**
     * 查询产品信息列表
     * @param deviceName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询产品信息列表")
    @GetMapping("/find/list/{pageNum}/{pageSize}")
    public Result<Page<DldbVO>> findList(@RequestParam(value = "deviceName",required = false) String deviceName,
                                         @RequestParam(value = "productId", required = false) String productId,
                                         @PathVariable("pageNum") Integer pageNum,
                                         @PathVariable("pageSize") Integer pageSize) {
        return Result.success(PageHelper.convert(dldbInfoService.findList(pageNum, pageSize, deviceName, productId)),
                "查询成功");
    }

    /**
     * 查询产品信息详情
     * @param id
     * @return
     */
    @ApiOperation(value = "查询产品信息详情")
    @GetMapping("/get")
    public Result<DldbSingleVO> getById(@RequestParam("id") String id) {
        return dldbInfoService.getById(id);
    }


    /**
     * 更新产品信息维护信息
     * @param dldbUpdateDTO
     * @return
     */
    @ApiOperation(value = "更新产品信息维护信息")
    @PostMapping("/update")
    public Result update(@RequestBody @Valid DldbUpdateDTO dldbUpdateDTO) {
        return dldbInfoService.update(dldbUpdateDTO);
    }

    /**
     * 发送通知邮件
     * 修改Product Status为RELEASE 调用
     * @param dldbSendDTO
     * @return
     */
    @ApiOperation(value = "发送通知邮件")
    @PostMapping("/sendNoticeMail")
    public Result sendNoticeMail(@RequestBody DldbSendDTO dldbSendDTO) {
        return dldbInfoService.sendNoticeMail(dldbSendDTO);
    }

}
