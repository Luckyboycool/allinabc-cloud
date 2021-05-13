package com.allinabc.cloud.activiti.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 批注
 * @Author wangtaifeng
 * @Date 2021/2/26 16:55
 **/
@Data
public class CommentModel implements Serializable {

    private static final long serialVersionUID = 5490494897873106008L;

    @ApiModelProperty(value = "commentID")
    private String id;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "流程实例ID")
    private String instanceId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "批注时间")
    private Date commentTm;

    @ApiModelProperty(value = "批注")
    private String comment;

    @ApiModelProperty(value = "审批动作")
    private String action;

    @ApiModelProperty(value = "姓名和工号")
    private String nameAndJobNumber;

}
