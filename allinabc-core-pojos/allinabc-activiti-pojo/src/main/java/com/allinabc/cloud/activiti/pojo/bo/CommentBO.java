package com.allinabc.cloud.activiti.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/10 18:49
 **/
@Data
public class CommentBO implements Serializable {

    /**
     * 备注
     */
    private String decision;

    /**
     * 用户id
     */
    private String userId;

    @ApiModelProperty(value = "被转办人")
    private String transferredUser;

    @ApiModelProperty(value = "被委托人")
    private String principalUser;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户动作(创建、审核通过、取消、驳回。。。)
     */
    private String action;


    /**
     * 账户类型
     */
    private String accountType;

    private String taskId;

    private String procInstId;

    private String procDefKey;

    private String taskDefKey;

    /**
     * 表单主键
     */
    private String basicInfoId;
}
