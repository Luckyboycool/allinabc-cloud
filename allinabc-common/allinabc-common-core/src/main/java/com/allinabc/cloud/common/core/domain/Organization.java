package com.allinabc.cloud.common.core.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Organization implements Serializable {

    /**
     * 主键
     */
    private Long    id;
    /**
     * 名称
     */
    private String  name;

}
