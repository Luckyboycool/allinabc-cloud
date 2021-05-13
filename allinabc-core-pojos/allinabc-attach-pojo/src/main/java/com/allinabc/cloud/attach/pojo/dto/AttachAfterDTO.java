package com.allinabc.cloud.attach.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/1/7 14:47
 **/
@Data
@NoArgsConstructor
public class AttachAfterDTO implements Serializable {

    /**
     * 业务主键ID
     */
    private String bizId;

    /**
     * 上传类型
     */
    private String bizType;

    /**
     * 附件ID
     */
    private String attachmentId;

    /**
     * Excel数据
     */
    private List<Map<String,Object>> data;

    public AttachAfterDTO(String bizId, String bizType, String attachmentId, List<Map<String, Object>> data) {
        this.bizId = bizId;
        this.bizType = bizType;
        this.attachmentId = attachmentId;
        this.data = data;
    }
}
