package com.allinabc.cloud.org.pojo.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 暂时使用  客户账号模拟
 * @author Simon.Xue
 * @date 2021/3/10 2:42 下午
 **/
@Data
public class CustomerData implements Serializable {
    
    private String id;
    private String name;
    private String companyName;
    private String companyAddress;
}
