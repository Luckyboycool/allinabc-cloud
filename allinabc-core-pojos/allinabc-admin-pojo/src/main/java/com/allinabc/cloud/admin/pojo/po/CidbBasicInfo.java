package com.allinabc.cloud.admin.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * CIDB_BASIC_INFO
 * @author 
 */
@Data
@ApiModel(value="AdmCidbInfo对象", description="")
@TableName(value = "ADM_CIDB_INFO")
public class CidbBasicInfo implements Serializable {
    private String id;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createTm;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updateTm;

    /**
     * 备注
     */
    private String remark;

    /**
     * 客户code
     */
    private String custCode;

    /**
     * 客户组
     */
    private String custGroup;

    /**
     * Customer Code all
     */
    private String custCodeAll;

    /**
     * 客户名称
     */
    private String custName;
    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 发运地址
     */
    private String shippingAddress;

    /**
     * 出具发票方
     */
    private String invoiceParty;

    /**
     * 客户付款条件
     */
    private String paymentTerm;

    /**
     * 国际贸易条款
     */
    private String deliveryTerm;

    /**
     * 币种
     */
    private String currency;

    /**
     * 税率
     */
    private String taxRate;

    private static final long serialVersionUID = 1L;
}