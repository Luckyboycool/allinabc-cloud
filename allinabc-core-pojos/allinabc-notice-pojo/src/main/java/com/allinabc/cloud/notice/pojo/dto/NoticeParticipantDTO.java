package com.allinabc.cloud.notice.pojo.dto;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/8 11:32
 **/
public class NoticeParticipantDTO implements Serializable {

    private String id;

    private String noticeId;

    private String participantType;

    private String objectType;
    /**
     * 当objectType为email时，
     * objectId保存EMAIL
     */
    private String objectId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
        this.participantType = participantType;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

}
