package com.allinabc.cloud.admin.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2/25/21 11:19 AM
 **/
@Data
public class RoleBaseVO implements Serializable {
    private static final long serialVersionUID = -6934054963909115201L;

    private String id;

    private String roleName;

    private String userId;
}
