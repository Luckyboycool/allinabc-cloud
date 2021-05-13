package com.allinabc.cloud.admin.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/4/12 9:41 上午
 **/
@Data
public class DldbSendDTO implements Serializable {


    private List<DldbUserDTO> userList;

    @Data
    public static class DldbUserDTO {
        private String id;
        private String type;
    }
}
