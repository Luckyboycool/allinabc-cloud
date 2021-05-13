package com.allinabc.cloud.attach.pojo.vo;

import lombok.Data;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/11/17 13:24
 **/
@Data
public class AttachResponse {
    /**
     * 文件ID
     */
    private String attachmentId;
    /**
     * 业务Id
     */
    private String bizId;
    /**
     * 附件类型配置ID
     */
    private String bizType;

    /**
     * 文件路径（相对位置）
     */
    private String fileUrl;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件原始名称
     */
    private String originalFileName;

    /**
     * 文件存储方式
     */
    private String storageCode;

    public AttachResponse() {
    }

    public AttachResponse(String storageCode, String attachmentId, String bizId, String bizType, String fileUrl,String fileName,String originalFileName) {
        this.storageCode = storageCode;
        this.attachmentId = attachmentId;
        this.bizId = bizId;
        this.bizType = bizType;
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
    }
}
