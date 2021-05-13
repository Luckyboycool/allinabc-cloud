package com.allinabc.cloud.admin.pojo.dto;

import com.allinabc.cloud.common.web.pojo.param.BaseQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/10 7:41 下午
 **/
@Data
public class LoginInfoSearchDTO extends BaseQueryParam implements Serializable {

    @ApiModelProperty(value = "账号名")
    private String username;
}
