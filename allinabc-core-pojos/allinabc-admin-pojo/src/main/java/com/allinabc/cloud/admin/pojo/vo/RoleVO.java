package com.allinabc.cloud.admin.pojo.vo;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author simon
 */
@Data
public class RoleVO extends BasicInfo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色标识")
    private String roleKey;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "序号")
    private BigDecimal sortNo;

    @ApiModelProperty(value = "角色状态")
    private String status;

    @ApiModelProperty(value = "是否可用")
    private String isAvailable;

    private List<ResourceVO> resources;

    @Data
    public static class ResourceVO {
        private String id;
        private String name;
    }


}
