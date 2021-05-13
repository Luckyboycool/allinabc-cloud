package com.allinabc.cloud.org.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2/22/21 4:18 PM
 **/
@Data
public class EmployeeDeptVO implements Serializable {
    private String id;

    private String name;
    /**
     * 工号
     */
    private String jobNumber;


    /**
     * 座机
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 所属组织
     */
    private String deptName;
}
