package com.allinabc.cloud.org.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2/22/21 3:39 PM
 **/
@Data
public class DepartmentTreeVO implements Serializable {

    private String departId;

    private String departName;

    private String departLevel;

    private String parentId;
}
