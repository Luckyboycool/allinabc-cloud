package com.allinabc.cloud.activiti.pojo.dto;

import com.allinabc.cloud.common.web.pojo.param.BaseQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/3 14:00
 **/
@Data
public class DraftParam extends BaseQueryParam implements Serializable {

    @ApiModelProperty(value = "表单类型")
    private String formType;

    @ApiModelProperty(value = "用户类型")
    private String userType;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "主题")
    private String subject;
    /**
     * 模糊搜索条件 年月份
     */
    @ApiModelProperty(value = "申请时间")
    private String requestTime;

    @ApiModelProperty(value = "单个表单类型")
    private String singleFormType;

}
