package com.allinabc.cloud.admin.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author QQF
 * @date 2020/12/16 11:30
 */

@Data
public class UserChangePwd  implements Serializable {


    private String  oldPwd;


    private String  newPwd;

}