package com.allinabc.cloud.org.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "ORG_CUSTOMER")
public class Customer implements Serializable {

    @ApiModelProperty(value = "客户信息编号")
    @TableField("ID")
    private String id;


    @ApiModelProperty(value = "客户三位代码")
    @TableField("CUST_CODE")
    private String custCode;

    @ApiModelProperty(value = "客户账号ID")
    @TableField(value = "CUSTOMER_ACCOUNT_ID")
    private String customerAccountId;

}
