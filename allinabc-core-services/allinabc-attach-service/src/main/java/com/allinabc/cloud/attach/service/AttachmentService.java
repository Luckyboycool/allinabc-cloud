package com.allinabc.cloud.attach.service;

import com.allinabc.cloud.attach.pojo.dto.AttachmentQueryParam;
import com.allinabc.cloud.attach.pojo.dto.AttachmentRequest;
import com.allinabc.cloud.attach.pojo.dto.BizAttachmentListParam;
import com.allinabc.cloud.attach.pojo.vo.AttachResponse;
import com.allinabc.cloud.attach.pojo.vo.AttachmentInfoResponse;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/28 10:46
 **/
public interface AttachmentService {

    /**
     * 通用附件上传
     */
    Result<AttachResponse> attachmentUpload(MultipartFile file, String bizId, String bizType, HttpServletRequest request);

    /**
     * 通用附件下载
     */
    void attachmentDownload(String attachmentId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 据传入的bizType以及bizId查询附件信息
     */
    Result<List<AttachmentInfoResponse>> findAttachmentList(AttachmentQueryParam attachmentQueryParam);

    /**
     * 根据传入的附件Id删除对应的附件(逻辑删除)
     */
    Result<Void> removeAttachment(AttachmentRequest attachmentRequest);

    /**
     * 查询单条附件信息(根据attachmentId)
     */
    Result<AttachmentInfoResponse> findSingleAttachment(AttachmentQueryParam attachmentQueryParam);

    /**
     * 批量更新bizId
     */
    void updateBizIds(BizAttachmentListParam bizAttachmentListParam);
}
