package com.allinabc.cloud.common.core.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 */
@Data
public class User extends BasicInfo implements Serializable {

    /**
     * 主键
     */
    private String id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 名字
     */
    private String name;

}
