package com.allinabc.cloud.attach.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.extra.ftp.Ftp;
import com.allinabc.cloud.attach.adapter.AttachmentAdapterService;
import com.allinabc.cloud.attach.adapter.UploadAdapt;
import com.allinabc.cloud.attach.config.UploadManager;
import com.allinabc.cloud.attach.enums.StorageTypeEnum;
import com.allinabc.cloud.attach.mapper.AttachmentMapper;
import com.allinabc.cloud.attach.pojo.dto.AttachmentInfoDTO;
import com.allinabc.cloud.attach.pojo.po.Attachment;
import com.allinabc.cloud.attach.pojo.po.AttachmentType;
import com.allinabc.cloud.attach.pojo.vo.AttachResponse;
import com.allinabc.cloud.attach.util.FtpUtil;
import com.allinabc.cloud.attach.util.LocalAttachmentUtil;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Description 通用模板上传与下载(与业务系统无交互)
 * @Author wangtaifeng
 * @Date 2020/11/16 17:52
 **/
@Service
@Slf4j
public class CommonAttachmentServiceImpl implements AttachmentAdapterService {


    @Resource
    private AttachmentMapper attachmentMapper;

    @Autowired
    private LocalAttachmentUtil localAttachmentUtil;

    @Value("${attachment.fileUploadType.zero}")
    private String fileUploadTypeZero;

    @Value("${attachment.fileUploadType.one}")
    private String fileUploadTypeOne;

    @Value("${attachment.fileUploadType.two}")
    private String fileUploadTypeTwo;

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.user}")
    private String user;

    @Value("${ftp.password}")
    private String password;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private FtpUtil ftpUtil;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<AttachResponse> uploadAttachment(MultipartFile file, String bizType, String bizId, AttachmentType attachmentType, HttpServletRequest request) {
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String fileUrl = null;
        String fileUploadType = null;
        long size = file.getSize();
        int begin = originalFilename.lastIndexOf(".");
        String fileType = originalFilename.substring(begin + 1);

        if (attachmentType.getUploadType().equals("0")) {
            fileUploadType = fileUploadTypeZero;
        } else if (attachmentType.getUploadType().equals("1")) {
            fileUploadType = fileUploadTypeOne;
        } else {
            fileUploadType = fileUploadTypeTwo;
        }

        Attachment attachment = null;
        UploadAdapt uploadService = uploadManager.getUploadService("upload_Adapt_" + attachmentType.getStorageCode());
        try {
            return uploadService.uploadFile(request, file, attachmentType, fileUploadType, fileName, fileType, fileUrl, attachment, bizType, bizId, size, originalFilename);
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败");
        }
        //描述：上传文件,判断存储类型
        // if (attachmentType.getStorageCode().equals(StorageTypeEnum.STORAGE_TYPE_S3.getCode())) {
        //判断附件类型，分文件夹存储
//            String s3Path = s3Util.buildPath(attachmentType.getSysCode(), attachmentType.getResCode(), attachmentType.getBizType(), fileUploadType, fileName + "." + fileType);
//            //fileUrl = bucketName+"/"+s3Path;
//            fileUrl = (bucketName+"/"+s3Path).toLowerCase();
//            //上传之前先入库
//            attachment = insertAttachment(bizType, bizId, fileType, fileName,originalFilename, fileUrl, size);
//            try {
//                s3Util.upload(bucketName, s3Path, file.getInputStream(), Math.toIntExact(size));
//            } catch (Exception e) {
//                log.error("S3文件上传失败", e);
//                throw new RuntimeException("文件上传失败");
//            }

        // } else if (attachmentType.getStorageCode().equals(StorageTypeEnum.STORAGE_TYPE_LOCAL.getCode())) {
        //本地存储
//            fileUrl = localAttachmentUtil.buildPath(request,attachmentType.getSysCode(), attachmentType.getResCode(), attachmentType.getBizType(), fileUploadType, fileName + "." + fileType);
//            attachment = insertAttachment(bizType, bizId, fileType, fileName,originalFilename, fileUrl, size);
//            File targetFile = new File(fileUrl);
//            //判断文件父目录是否存在
//            if(!targetFile.getParentFile().exists()){
//                //不存在就创建一个
//                targetFile.getParentFile().mkdirs();
//            }
//            try {
//                file.transferTo(targetFile);
//            } catch (IOException e) {
//                log.error("文件上传失败",e);
//                e.printStackTrace();
//            }
//            log.info("文件上传成功");
        //  }
//        AttachResponse attachResponse = new AttachResponse(attachmentType.getStorageCode(),attachment.getId(),attachment.getBizId(),attachment.getBizType(),attachmentType.getS3BucketName()+"/"+attachment.getFileUrl(),attachment.getFileName()+"."+attachment.getFileType(),originalFilename);
//
//        log.info("文件上传成功返回到前端的数据为="+ JSON.toJSONString(attachResponse));
//        return Result.success(attachResponse);
    }

    @Override
    public void downloadAttachment(AttachmentInfoDTO attachmentInfoDTO, HttpServletRequest request, HttpServletResponse response) {
        //描述：通用文件下载
        if (attachmentInfoDTO.getStorageCode().equals(StorageTypeEnum.STORAGE_TYPE_S3.getCode())) {
            //s3下载
            //S3Util.downloadAttachment(bucketName,attachmentInfoDTO,response);

        } else if (attachmentInfoDTO.getStorageCode().equals(StorageTypeEnum.STORAGE_TYPE_LOCAL.getCode())) {
            //其它下载
            localAttachmentUtil.downLoad(attachmentInfoDTO, response);

        } else if (attachmentInfoDTO.getStorageCode().equals(StorageTypeEnum.STORAGE_TYPE_FTP.getCode())) {
            log.info("ftp下载");
            //Ftp ftp = new Ftp(host, port, user, password, Charset.defaultCharset(),FtpMode.Passive);
            ServletOutputStream outputStream = null;
            try {
                outputStream = response.getOutputStream();
                String path = "/" + attachmentInfoDTO.getFileRootName() + "/" + attachmentInfoDTO.getFileUrl().substring(0, attachmentInfoDTO.getFileUrl().lastIndexOf("/"));
                String fileName = attachmentInfoDTO.getFileName() + "." + attachmentInfoDTO.getFileType();
                log.info("path=" + path + "  ,fileName= " + fileName);
                response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(fileName, "UTF-8"));
                Ftp ftpBean = ftpUtil.getFtpBean();
                ftpBean.download(path, fileName, outputStream);

            } catch (IOException e) {
                e.printStackTrace();
                log.error("ftp文件下载失败");
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


//    @Transactional(rollbackFor = Exception.class)
//    public Attachment insertAttachment(String bizType, String bizId,String fileType,String fileName,String originalFileName,String fileUrl,Long size){
//        //描述:入库如果传入的bizId为空，那么默认生成一个bizId给前端，不为空那么直接入库
//        User currentUser = CurrentUserUtil.getCurrentUser();
//        Date now = new Date();
//        Attachment attachment = new Attachment(currentUser.getId(), now, currentUser.getId(), now);
//        //Attachment attachment = new Attachment("9999", now, "9999", now);
//        Snowflake snowflake = IdUtil.getSnowflake(0, 0);
//        if (StringUtils.isEmpty(bizId)) {
//            attachment.setBizId(snowflake.nextIdStr());
//        }else{
//            attachment.setBizId(bizId);
//        }
//        //设置主键,不设置默认mybatis-plus生成主键
//        attachment.setId(snowflake.nextIdStr());
//        attachment.setBizType(bizType);
//        attachment.setFileType(fileType);
//        attachment.setFileName(fileName);
//        attachment.setOriginalFileName(originalFileName);
//        attachment.setFileSize(BigDecimal.valueOf(size));
//        attachment.setFileUrl(fileUrl);
//        log.info("入库数据="+ JSON.toJSONString(attachment));
//        int var = attachmentMapper.insert(attachment);
//        if (var != 1) {
//            log.error("模板入库失败，失败数据=" + JSON.toJSONString(attachment));
//            throw new RuntimeException("模板入库失败");
//        }
//        log.info("数据入库成功");
//        return attachment;
//    }


}
