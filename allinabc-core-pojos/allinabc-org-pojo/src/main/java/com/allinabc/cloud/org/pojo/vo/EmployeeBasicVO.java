package com.allinabc.cloud.org.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/15 7:39 下午
 **/
@Data
public class EmployeeBasicVO implements Serializable {
    private String userId;
    private String username;
}
