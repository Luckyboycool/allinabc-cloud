package com.allinabc.cloud.attach.controller;


import com.allinabc.cloud.attach.pojo.dto.AttachmentTypeQueryParam;
import com.allinabc.cloud.attach.pojo.dto.AttachmentTypeRequest;
import com.allinabc.cloud.attach.pojo.po.AttachmentType;
import com.allinabc.cloud.attach.pojo.vo.QueryTableResult;
import com.allinabc.cloud.attach.service.AttachmentTypeService;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 附件类型表 前端控制器
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-11-16
 */
@Api(tags = "附件类型管理控制器")
@RestController
@RequestMapping("/attachment-type")
public class AttachmentTypeController {

    @Autowired
    private AttachmentTypeService attachmentTypeService;


    /**
     * 功能描述 查询附件类型配置
     * @author wangtaifeng
     * @date 2020/11/18
     */
    @ApiOperation(value = "查询附件类型配置",notes = "查询附件类型配置")
    @PostMapping("/find/{pageNum}/{pageSize}")
    public Result<QueryTableResult<AttachmentType>> findAttachmentType(@RequestBody AttachmentTypeQueryParam attachmentTypeQueryParam, @PathVariable Long pageNum, @PathVariable Long pageSize){
        return attachmentTypeService.findAttachmentType(attachmentTypeQueryParam,pageNum,pageSize);
    }



    /**
     * 功能描述 新增单个附件类型配置
     * @author wangtaifeng
     * @date 2020/11/18
     */
    @ApiOperation(value = "新增单个附件类型配置",notes = "新增单个附件类型配置")
    @PostMapping("/add")
    public Result<String> addAttachmentType(@RequestBody AttachmentTypeRequest attachmentTypeRequest){
        if(StringUtils.isEmpty(attachmentTypeRequest.getSysCode())){
            return Result.failed("sysCode不能为空");
        }
        if(StringUtils.isEmpty(attachmentTypeRequest.getResCode())){
            return Result.failed("resCode不能为空");
        }
        return attachmentTypeService.addAttachmentType(attachmentTypeRequest);
    }


    /**
     * 功能描述 删除单个附件类型配置(物理删除)
     * @author wangtaifeng
     * @date 2020/11/18
     */
    @ApiOperation(value = "删除单个附件类型配置",notes = "删除单个附件类型配置")
    @PostMapping("/remove")
    public Result<Void> removeAttachmentType(@RequestBody AttachmentTypeRequest attachmentTypeRequest){
        if(StringUtils.isEmpty(attachmentTypeRequest.getId())){
            return Result.failed("附件类型Id不能为空");
        }
        return attachmentTypeService.removeAttachmentType(attachmentTypeRequest);
    }

}
