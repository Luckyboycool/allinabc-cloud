package com.allinabc.cloud.attach.controller;

import com.allinabc.cloud.attach.pojo.dto.AttachmentQueryParam;
import com.allinabc.cloud.attach.pojo.dto.AttachmentRequest;
import com.allinabc.cloud.attach.pojo.dto.BizAttachmentListParam;
import com.allinabc.cloud.attach.pojo.vo.AttachResponse;
import com.allinabc.cloud.attach.pojo.vo.AttachmentInfoResponse;
import com.allinabc.cloud.attach.service.AttachmentService;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 附件表 前端控制器
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-11-16
 */
@Api(tags = "通用附件上传下载")
@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 功能描述 通用单个附件上传,bizType 必传，bizId（业务主键字段，不传，默认生成此字段值）
     * @author wangtaifeng
     * @date 2020/11/16
     */
    @ApiOperation(value = "通用单个附件上传",notes = "通用单个附件上传")
    @PostMapping("/upload")
    public Result<AttachResponse> attachmentUpload(@RequestParam("file") MultipartFile file, @RequestParam("bizType") String bizType, @RequestParam("bizId") String bizId, HttpServletRequest request){
        return attachmentService.attachmentUpload(file,bizId,bizType,request);
    }

    /**
     * 功能描述 通用附件下载，根据传入的文件ID
     * @author wangtaifeng
     * @date 2020/11/17
     */
    @ApiOperation(value = "通用附件下载",notes = "通用附件下载")
    @GetMapping("/download/{attachmentId}")
    public void attachmentDownload(@PathVariable String attachmentId, HttpServletRequest request, HttpServletResponse response){
         attachmentService.attachmentDownload(attachmentId,request,response);
    }

    /**
     * 功能描述 根据传入的bizType以及bizId查询附件信息
     * @author wangtaifeng
     * @date 2020/11/18
     */
    @ApiOperation(value = "据传入的bizType以及bizId查询附件信息",notes = "据传入的bizType以及bizId查询附件信息")
    @PostMapping("/list")
    public Result<List<AttachmentInfoResponse>> findAttachmentList(@RequestBody AttachmentQueryParam attachmentQueryParam){
        return attachmentService.findAttachmentList(attachmentQueryParam);
    }


    /**
     * 功能描述 查询单条附件信息(根据attachmentId)
     * @author wangtaifeng
     * @date 2020/11/18
     */
    @ApiOperation(value = "查询单条附件信息(根据attachmentId)",notes = "查询单条附件信息(根据attachmentId)")
    @PostMapping("/single")
    public Result<AttachmentInfoResponse> findSingleAttachment(@RequestBody AttachmentQueryParam attachmentQueryParam){
        return attachmentService.findSingleAttachment(attachmentQueryParam);
    }

    /**
     * 功能描述 根据传入的附件Id删除对应的附件(逻辑删除)
     * @author wangtaifeng
     * @date 2020/11/18
     */
    @ApiOperation(value = "根据传入的附件Id删除对应的附件(逻辑删除)",notes = "根据传入的附件Id删除对应的附件(逻辑删除)")
    @PostMapping("/remove")
    public Result<Void> removeAttachment(@RequestBody AttachmentRequest attachmentRequest){
        return attachmentService.removeAttachment(attachmentRequest);
    }

    @ApiOperation(value = "批量更新bizId")
    @PostMapping("/bizids")
    Result<Void> updateBizIds(@RequestBody BizAttachmentListParam bizAttachmentListParam){
        if(bizAttachmentListParam.getAttachmentParams()==null||bizAttachmentListParam.getAttachmentParams().size()==0){
            return Result.failed("参数为空");
        }
        attachmentService.updateBizIds(bizAttachmentListParam);
        return Result.success();
    }

}
