package com.allinabc.cloud.activiti.pojo.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/15 14:33
 **/
@Data
@NoArgsConstructor
public class MailBO implements Serializable {

    private String processInstanceId;

    private String basicInfoId;

    private String requestNo;

    private String subject;

    private String requester;

    private String type;


    /**
     * 主送用户
     */
    private List<String> toUserList;

    /**
     * 抄送用户
     */
    private List<String> ccUserList;

}
