package com.allinabc.cloud.notice.pojo.dto;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/8 19:11
 **/
public class Attachment implements Serializable {

    private String attachmentId;

    private String url;

    private String fileName;

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
