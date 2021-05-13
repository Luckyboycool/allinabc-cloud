package com.allinabc.cloud.admin.pojo.dto;

import com.allinabc.cloud.common.web.pojo.param.BaseQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/4 8:07 下午
 **/
@Data
public class GroupSearchDTO extends BaseQueryParam implements Serializable {
    @ApiModelProperty(value = "群组名称")
    private String groupName;


    @ApiModelProperty(value = "是否可用")
    private String isAvailable;


    @ApiModelProperty(value = "群组描述")
    private String description;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "部门名称")
    private String deptName;
}
