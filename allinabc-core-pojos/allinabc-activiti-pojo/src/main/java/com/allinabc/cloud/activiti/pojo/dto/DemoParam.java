package com.allinabc.cloud.activiti.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/1 17:39
 **/
@Data
public class DemoParam implements Serializable {

    private List<Map<String,String>> users;

    private String taskId;
    //包含：用户、角色、群组、部门、指定节点审批人的部门主管
    private String auditorType;

}
