package com.allinabc.cloud.attach.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/1/7 14:08
 **/
@Data
@NoArgsConstructor
public class AttachBeforeDTO implements Serializable {

    /**
     * 业务主键ID
     */
    private String bizId;

    /**
     * 上传类型
     */
    private String bizType;

    /**
     * Excel数据
     */
    private List<Map<String,Object>> data;

    public AttachBeforeDTO(String bizId, String bizType, List<Map<String, Object>> data) {
        this.bizId = bizId;
        this.bizType = bizType;
        this.data = data;
    }
}
