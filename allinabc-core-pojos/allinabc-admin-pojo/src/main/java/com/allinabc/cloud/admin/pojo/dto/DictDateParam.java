package com.allinabc.cloud.admin.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/24 15:36
 **/
@Data
public class DictDateParam implements Serializable {

    @ApiModelProperty(value = "appCode")
    private String appCode;

    @ApiModelProperty(value = "多个按照‘，’隔开")
    private String dictTypes;

    @ApiModelProperty(value = "组中再分类")
    private String dictGroupType;

    @ApiModelProperty(value = "一级或者二级")
    private String leaveType;
}
