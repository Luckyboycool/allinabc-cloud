package com.allinabc.cloud.activiti.pojo.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/16 21:01
 **/
@Data
public class NameModel implements Serializable {
    private String id;
    private String name;

    private String jobNumber;

    private String nameAndJobNumber;
}
