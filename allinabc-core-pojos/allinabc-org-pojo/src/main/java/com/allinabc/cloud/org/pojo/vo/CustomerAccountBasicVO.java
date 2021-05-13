package com.allinabc.cloud.org.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/23 11:17 上午
 **/
@Data
public class CustomerAccountBasicVO implements Serializable {
    /**
     * 客户账号ID
     */
    private String id;
    /**
     * 客户账号名
     */
    private String userName;
}
