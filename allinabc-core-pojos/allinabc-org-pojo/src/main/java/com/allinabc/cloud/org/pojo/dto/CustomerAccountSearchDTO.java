package com.allinabc.cloud.org.pojo.dto;

import com.allinabc.cloud.common.web.pojo.param.BaseQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Simon.Xue
 * @date 2/24/21 3:19 PM
 **/
@Data
public class CustomerAccountSearchDTO extends BaseQueryParam {
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String username;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String name;
}
