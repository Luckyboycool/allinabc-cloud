package com.allinabc.cloud.admin.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/23 9:17 上午
 **/
@Data
public class CidbBasicSimpleVO implements Serializable {
    /**
     * 客户三位代码
     */
    private String custCode;
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
     * 客户所在组
     */
    private String custGroup;
}
