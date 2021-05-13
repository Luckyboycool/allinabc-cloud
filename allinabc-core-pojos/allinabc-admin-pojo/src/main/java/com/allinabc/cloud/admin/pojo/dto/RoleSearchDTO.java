package com.allinabc.cloud.admin.pojo.dto;

import com.allinabc.cloud.common.web.pojo.param.BaseQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/2 8:49 下午
 **/
@Data
public class RoleSearchDTO  extends BaseQueryParam implements Serializable {
    @ApiModelProperty(value = "角色代码")
    private String roleKey;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
