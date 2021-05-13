package com.allinabc.cloud.admin.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/11 1:16 下午
 **/
@Data
public class SysUserDTO implements Serializable {
    private String id;
    private String name;
}
