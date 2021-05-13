package com.allinabc.cloud.admin.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/4/14 9:46 上午
 **/
@Data
public class CidbUpdateDTO implements Serializable {
    @NotBlank(message = "ID不能为空")
    private String id;

    private CidbDetailUpdateDTO hc;
    private CidbDetailUpdateDTO lg;

    @Data
    public static class CidbDetailUpdateDTO {
        private List<String> sales;
        private List<String> backupSales;
        private List<String> ce;
        private List<String> backupCe;
        private List<String> cs;
        private List<String> backupCs;
    }

}
