package com.allinabc.cloud.notice.controller;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.notice.pojo.dto.ModifyNoticeTemplateDTO;
import com.allinabc.cloud.notice.pojo.dto.QueryNoticeTemplateDTO;
import com.allinabc.cloud.notice.pojo.vo.NoticeTemplateVO;
import com.allinabc.cloud.notice.service.NoticeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/20 11:29
 **/
@RestController
@RequestMapping("/template")
@Api(tags = "通知模板配置")
@Slf4j
public class NoticeTemplateController {


    @Autowired
    private NoticeTemplateService noticeTemplateService;


    /**
     * 模板列表查询
     */
    @PostMapping("/list")
    @ApiOperation(value = "模板查询",notes = "模板列表查询")
    public Result<List<NoticeTemplateVO>> findTemplateList(@RequestBody(required = false) QueryNoticeTemplateDTO noticeTemplateDTO) {
        log.info("查询模板列表请求参数="+ JSON.toJSONString(noticeTemplateDTO));
        return noticeTemplateService.findTemplateList(noticeTemplateDTO);
    }

    /**
     * 模板新增
     */
    @PostMapping("/add")
    @ApiOperation(value = "模板新增",notes = "模板新增")
    public Result<String> addTemplate(@RequestBody @Validated ModifyNoticeTemplateDTO noticeTemplateDTO) {
        log.info("新增模板列表请求参数="+ JSON.toJSONString(noticeTemplateDTO));
        return noticeTemplateService.addTemplate(noticeTemplateDTO);
    }

    /**
     * 模板修改
     */
    @PostMapping("/modify")
    @ApiOperation(value = "模板修改",notes = "模板修改")
    public Result<String> modifyTemplate(@RequestBody @Validated ModifyNoticeTemplateDTO noticeTemplateDTO) {
        if(StringUtils.isEmpty(noticeTemplateDTO.getId())){
            return Result.failed("id can not be null");
        }
        log.info("修改模板列表请求参数="+ JSON.toJSONString(noticeTemplateDTO));
        return noticeTemplateService.modifyTemplate(noticeTemplateDTO);
    }

    /**
     * 模板删除
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "模板删除",notes = "模板删除")
    public Result<String> removeTemplate(@PathVariable String id) {
        log.info("删除模板请求参数id="+ id);
        return noticeTemplateService.removeTemplate(id);
    }

}
