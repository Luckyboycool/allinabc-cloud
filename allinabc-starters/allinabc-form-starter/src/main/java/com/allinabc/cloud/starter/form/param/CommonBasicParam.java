package com.allinabc.cloud.starter.form.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/24 10:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonBasicParam implements Serializable {

    @ApiModelProperty(value = "BasicForm表单ID")
    private String id;

    @ApiModelProperty(value = "申请单号")
    private String requestNo;

    @ApiModelProperty(value = "申请人")
    private String requester;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date requestTime;

    @ApiModelProperty(value = "BasicForm状态")
    private String status;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "主题")
    private String subject;
}
