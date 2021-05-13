package com.allinabc.cloud.attach.adapter;


import com.allinabc.cloud.attach.pojo.dto.AttachmentInfoDTO;
import com.allinabc.cloud.attach.pojo.po.AttachmentType;
import com.allinabc.cloud.attach.pojo.vo.AttachResponse;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 文件上传适配器
 * @Author wangtaifeng
 * @Date 2020/11/13 15:46
 **/
public interface AttachmentAdapterService {
    /**附件上传*/
    Result<AttachResponse> uploadAttachment(MultipartFile file, String bizType, String bizId, AttachmentType attachmentType, HttpServletRequest request);

    /**附件下载*/
    void downloadAttachment(AttachmentInfoDTO attachmentInfoDTO, HttpServletRequest request, HttpServletResponse response);

}
