package com.allinabc.cloud.org.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2/24/21 10:11 AM
 **/
@Data
@ApiModel(value = "客户信息实体", description = "")
public class CustomerVO implements Serializable {

    @ApiModelProperty(value = "客户信息名称")
    private String custName;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @ApiModelProperty(value = "客户三位代码")
    private String custCode;



}
