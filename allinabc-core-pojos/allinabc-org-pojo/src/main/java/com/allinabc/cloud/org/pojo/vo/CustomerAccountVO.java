package com.allinabc.cloud.org.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/24/21 11:00 AM
 **/
@Data
public class CustomerAccountVO implements Serializable {

    private String id;
    private String username;
    private List<CusCustomerVO> customers;
    private String custNames;
    private List<CustomerAccountRoleVO> roles;

    private String createdBy;
    private String createTm;

    @Data
    public static class CustomerAccountRoleVO {
        private String id;
        private String name;
    }

    @Data
    public static class CusCustomerVO {
        private String name;
        private String companyAddress;
        private String companyName;
    }

}
