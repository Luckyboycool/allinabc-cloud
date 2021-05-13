package com.allinabc.cloud.org.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/25/21 10:24 AM
 **/
@Data
public class CustomerAccountDetailVO implements Serializable {
    private static final long serialVersionUID = 2768398237794597858L;
    private String id;

    public String username;

    public String password;

    private List<CustomerVO> customerList;
    private List<RoleVO> roleList;


    @Data
    public static class RoleVO {
        String id;
        String name;
    }

    /**
     * 是否可用
     */
    private String isAvailable;
}
