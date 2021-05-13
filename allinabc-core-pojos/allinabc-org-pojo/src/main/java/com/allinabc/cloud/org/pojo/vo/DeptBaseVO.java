package com.allinabc.cloud.org.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/2 2:56 下午
 **/
@Data
public class DeptBaseVO implements Serializable {
    private String departId;
    private String departName;
}
