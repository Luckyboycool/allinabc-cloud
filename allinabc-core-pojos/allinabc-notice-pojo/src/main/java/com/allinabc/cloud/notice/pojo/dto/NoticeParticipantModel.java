package com.allinabc.cloud.notice.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/9 11:17
 **/
@Data
public class NoticeParticipantModel {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "通知ID")
    private String noticeId;

    @ApiModelProperty(value = "NOTICE_CC/NOTICE_TO")
    private String participantType;

    @ApiModelProperty(value = "参与对象类型 USER/GROUP/DEPARTMENT/COMPANY/")
    private String objectType;

    @ApiModelProperty(value = "参与对象主键")
    private String objectId;

    //private List<SendTypeModel> sendTypeModelList;
}
