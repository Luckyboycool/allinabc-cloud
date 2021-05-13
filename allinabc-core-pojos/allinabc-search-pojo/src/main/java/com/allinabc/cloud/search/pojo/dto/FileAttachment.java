package com.allinabc.cloud.search.pojo.dto;

import lombok.Data;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/11/20 17:11
 **/
@Data
public class FileAttachment {
    /**
     * 文件内容
     */
    private String content;

    /**
     * 日期
     */
    private String date;

    /**
     * contentType
     */
    private String content_type;

    /**
     * author
     */
    private String author;

    /**
     * 语言
     */
    private String language;

    /**
     * 内容长度
     */
    private Integer content_length;
}
