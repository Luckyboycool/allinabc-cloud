package com.allinabc.cloud.attach.pojo.vo;

import com.allinabc.cloud.attach.pojo.po.Attachment;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/11/24 13:29
 **/
@Data
public class ESUseCaseInfoResponse {
    private String id;
    private String caseName;
    private String module;
    private String signatureId;
    private String signatureSourceId;
    private String createTm;
    private String updateTm;
    private String signature;
    private String tool;
    private List<Attachment> image;
}
