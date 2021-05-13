package com.allinabc.cloud.admin.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/4/6 5:05 下午
 **/
@Data
public class DldbUpdateDTO implements Serializable {
    @NotEmpty(message = "ID不能为空")
    @ApiModelProperty(value = "ID")
    private String id;


    private List<DldbDetailParam> dldbDetailList;

    @Data
    public static class DldbDetailParam implements Serializable{

        private String id;
        /**
         * 产品状态
         */
        @ApiModelProperty(value = "产品状态")
        private String productIdStatus;
        /**
         * 状态说明
         */
        @ApiModelProperty(value = "状态说明")
        private String statusRemark;
        /**
         * 产品对应PIE/TD Group
         */
        @ApiModelProperty(value = "产品对应PIE/TD Group")
        private String pieTdGroup;
        /**
         * Risk初始额度
         */
        @ApiModelProperty(value = "Risk初始额度")
        private String riskInitialQuota;
    }
}
