package com.allinabc.cloud.starter.form.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/1 4:45 下午
 **/
@Data
public class FormFieldResp implements Serializable {
    @JsonIgnore
    private String id;

    @JsonIgnore
    private String entityKey;

    @JsonIgnore
    private String fieldId;

    /**
     * 字段名称
     */
    private String fieldName;
    private String fieldType;
    private String componentType;
    private String rw;
    /**
     * 表单ID
     */
    private String tableId;
    /**
     * 类型  section function field
     */
    private String type;

    private String nodeKey;
}
