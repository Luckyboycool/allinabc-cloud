package com.allinabc.cloud.admin.pojo.vo;

import lombok.Data;

/**
 * @Author: QQF
 * @create: 2020-09-08 11:19
 **/

@Data
public class AppPathVO {

    /** 系统名称 */
    private String appCode;

    /** 系统code */
    private String appName;

    /** 系统 path**/
    private String path;
}