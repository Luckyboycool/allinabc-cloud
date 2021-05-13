package com.allinabc.cloud.attach.adaptimpl.upload;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;
import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.attach.adapter.UploadAdapt;
import com.allinabc.cloud.attach.mapper.AttachmentMapper;
import com.allinabc.cloud.attach.pojo.po.Attachment;
import com.allinabc.cloud.attach.pojo.po.AttachmentType;
import com.allinabc.cloud.attach.pojo.vo.AttachResponse;
import com.allinabc.cloud.attach.util.FtpUtil;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.s3.util.S3Util;
import com.allinabc.cloud.starter.web.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/16 15:12
 **/
@Slf4j
@Service(value = "upload_Adapt_Ftp")
public class FtpUploadAdaptService implements UploadAdapt {

    @Autowired
    private S3Util s3Util;

    @Resource
    private AttachmentMapper attachmentMapper;

//    @Value("${ftp.host}")
//    private String host;
//
//    @Value("${ftp.port}")
//    private int port;
//
//    @Value("${ftp.user}")
//    private String user;
//
//    @Value("${ftp.password}")
//    private String password;

    @Autowired
    private FtpUtil ftpUtil;


    @Override
    public Result<AttachResponse> uploadFile(HttpServletRequest request, MultipartFile file, AttachmentType attachmentType, String fileUploadType, String fileName, String fileType, String fileUrl, Attachment attachment, String bizType, String bizId, long size, String originalFilename) throws Exception{
        //判断附件类型，分文件夹存储
        log.info("ftp文件上传");
        String ftpPath = s3Util.buildPath(attachmentType.getSysCode(), attachmentType.getResCode(), attachmentType.getBizType(), fileUploadType,fileName+"."+fileType);
        //fileUrl = bucketName+"/"+s3Path;
         fileUrl = ftpPath.toLowerCase();
        String fileUrlNoNamePath = s3Util.buildFtpPathNoName(attachmentType.getSysCode(), attachmentType.getResCode(), attachmentType.getBizType(), fileUploadType);
        //上传之前先入库
        attachment = insertAttachment(bizType, bizId, fileType, fileName,originalFilename, fileUrl, size);
       // Ftp ftp = new Ftp(host, port, user, password, Charset.defaultCharset(),FtpMode.Passive);
        Ftp ftp = ftpUtil.getFtpBean();
        boolean upload =ftp.upload(("/"+attachmentType.getFileRootName()+fileUrlNoNamePath).toLowerCase(), fileName+"."+fileType, file.getInputStream());
        if(upload){
            log.info("ftp文件上传成功");
            AttachResponse attachResponse = new AttachResponse(attachmentType.getStorageCode(),attachment.getId(),attachment.getBizId(),attachment.getBizType(),attachmentType.getFileRootName()+"/"+attachment.getFileUrl(),attachment.getFileName()+"."+attachment.getFileType(),originalFilename);
            log.info("文件上传成功返回到前端的数据为="+ JSON.toJSONString(attachResponse));
            ftp.close();
            return Result.success(attachResponse);
        }else{
            log.info("ftp文件上传失败");
            ftp.close();
            throw new RuntimeException("ftp文件上传失败");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public Attachment insertAttachment(String bizType, String bizId,String fileType,String fileName,String originalFileName,String fileUrl,Long size){
        //描述:入库如果传入的bizId为空，那么默认生成一个bizId给前端，不为空那么直接入库
        User currentUser = CurrentUserUtil.getCurrentUser();
        Date now = new Date();
        Attachment attachment = new Attachment(currentUser.getId(), now, currentUser.getId(), now);
        //Attachment attachment = new Attachment("9999", now, "9999", now);
        Snowflake snowflake = IdUtil.getSnowflake(0, 0);
        if (StringUtils.isEmpty(bizId)) {
            attachment.setBizId(snowflake.nextIdStr());
        }else{
            attachment.setBizId(bizId);
        }
        //设置主键,不设置默认mybatis-plus生成主键
        attachment.setId(snowflake.nextIdStr());
        attachment.setBizType(bizType);
        attachment.setFileType(fileType);
        attachment.setFileName(fileName);
        attachment.setOriginalFileName(originalFileName);
        attachment.setFileSize(BigDecimal.valueOf(size));
        attachment.setFileUrl(fileUrl);
        log.info("入库数据="+ JSON.toJSONString(attachment));
        int var = attachmentMapper.insert(attachment);
        if (var != 1) {
            log.error("模板入库失败，失败数据=" + JSON.toJSONString(attachment));
            throw new RuntimeException("模板入库失败");
        }
        log.info("数据入库成功");
        return attachment;
    }
}
