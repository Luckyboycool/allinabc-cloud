package com.allinabc.cloud.admin.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author gaoxiang
 * @Date 2021/3/22
 **/
@Data
@ApiModel("查找CIDB入参")
public class QueryCidbParam implements Serializable {

    @ApiModelProperty(value = "Customer Code")
    private String custCode;
    @ApiModelProperty(value = "Customer Name")
    private String custName;
    @ApiModelProperty(value = "local")
    private String area;
    private static final long serialVersionUID = 1L;

}
