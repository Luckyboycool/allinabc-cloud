package com.allinabc.cloud.admin.pojo.po;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="AdmOperLog对象", description="")
@TableName(value = "ADM_OPER_LOG")
public class OperLog extends BasicInfo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "操作模块")
    @TableField("TITLE")
    private String title;

    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    @TableField("BUSINESS_TYPE")
    private BigDecimal businessType;

    @ApiModelProperty(value = "请求方法")
    @TableField("METHOD")
    private String method;

    @ApiModelProperty(value = "请求方式")
    @TableField("REQUEST_METHOD")
    private String requestMethod;

    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    @TableField("OPERATOR_TYPE")
    private BigDecimal operatorType;

    @ApiModelProperty(value = "操作人员")
    @TableField("OPER_NAME")
    private String operName;

    @ApiModelProperty(value = "请求url")
    @TableField("OPER_URL")
    private String operUrl;

    @ApiModelProperty(value = "操作地址")
    @TableField("OPER_IP")
    private String operIp;

    @ApiModelProperty(value = "操作地点")
    @TableField("OPER_LOCATION")
    private String operLocation;

    @ApiModelProperty(value = "请求参数")
    @TableField("OPER_PARAM")
    private String operParam;

    @ApiModelProperty(value = "返回参数")
    @TableField("JSON_RESULT")
    private String jsonResult;

    @ApiModelProperty(value = "操作状态（0=异常,1=正常）")
    @TableField("STATUS")
    private BigDecimal status;

    @ApiModelProperty(value = "错误消息")
    @TableField("ERROR_MSG")
    private String errorMsg;

    @ApiModelProperty(value = "操作时间")
    @TableField("OPER_TIME")
    private LocalDateTime operTime;


}
