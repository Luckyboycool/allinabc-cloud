package com.allinabc.cloud.admin.pojo.dto;

import com.allinabc.cloud.common.web.pojo.param.BaseQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/8 10:41 上午
 **/
@Data
public class ResourceSearchDTO extends BaseQueryParam implements Serializable {
    @ApiModelProperty(value = "功能项代码")
    private String resKey;
    @ApiModelProperty(value = "功能项名称")
    private String resName;
    @ApiModelProperty(value = "功能项地址")
    private String path;
}
