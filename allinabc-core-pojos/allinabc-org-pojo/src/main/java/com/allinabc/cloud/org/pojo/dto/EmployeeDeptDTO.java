package com.allinabc.cloud.org.pojo.dto;

import com.allinabc.cloud.common.web.pojo.param.BaseQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Simon.Xue
 * @date 2/22/21 4:24 PM
 **/
@Data
public class EmployeeDeptDTO extends BaseQueryParam {

    @ApiModelProperty(value = "搜索条件 可按员工号、邮箱、姓名、手机号、分机号、拼音查询")
    private String keyword;
    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门id,不传代表所有")
    private String departId;
}
