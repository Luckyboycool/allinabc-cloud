package com.allinabc.cloud.search.pojo.dto;

import lombok.Data;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/11/19 22:39
 **/
@Data
public class ESFileObj {

    /**
     * 文件主键
     */
    private String id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件路径
     */
    private String fileUrl;

    /**
     * 文件转化成base64编码后所有的内容
     */
    private String content;


}
