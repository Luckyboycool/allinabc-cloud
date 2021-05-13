package com.allinabc.cloud.activiti.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/26 14:04
 **/
@Data
public class ProcessIdModel implements Serializable {

    private static final long serialVersionUID = -1082473261880549163L;

    /**
     * 表单类型
     */
    private String formType;

    /**
     * 流程定义ID
     */
    private String processId;

}
