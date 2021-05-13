package com.allinabc.cloud.attach.adapter;

import com.allinabc.cloud.attach.pojo.po.Attachment;
import com.allinabc.cloud.attach.pojo.po.AttachmentType;
import com.allinabc.cloud.attach.pojo.vo.AttachResponse;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/16 15:17
 **/
public interface UploadAdapt {

    Result<AttachResponse> uploadFile(HttpServletRequest request, MultipartFile file, AttachmentType attachmentType, String fileUploadType, String fileName, String fileType, String fileUrl, Attachment attachment, String bizType, String bizId, long size, String originalFilename) throws Exception;
}
