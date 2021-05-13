package com.allinabc.cloud.attach.pojo.dto;

import com.allinabc.cloud.attach.pojo.po.Attachment;
import lombok.Data;

import java.util.List;


/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/11/23 15:03
 **/
@Data
//@Document(indexName = "aiyei-yms-alibrary-usecase")
public class ESUseCaseInfo {
    /**
     * 主键ID
     */
    private String id;
    /**
     * caseName
     */
    private String caseName;
    private String module;
    private String signatureId;
    private String signatureSourceId;
    private String createTm;
    private String updateTm;
    private String signature;
    private String tool;
    private List<Attachment> attachments;
    /**
     * 文件内容
     */
   // private String content;



}
