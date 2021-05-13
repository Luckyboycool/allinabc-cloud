package com.allinabc.cloud.admin.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/11 5:11 下午
 **/
@Data
public class ResourceRoleVO implements Serializable {
    private String id;
    private String name;
    private String roleId;
}
