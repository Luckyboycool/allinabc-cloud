package com.allinabc.cloud.common.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基本信息
 */
@Data
public class BasicInfo implements Serializable {

    /**
     * 主键
     */
    private String  id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTm;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTm;

}
