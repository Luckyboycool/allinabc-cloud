package com.allinabc.cloud.admin.pojo.po;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="AdmConfig对象", description="")
@TableName(value = "ADM_CONFIG")
public class Config extends BasicInfo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "程序代码")
    @TableField("APP_CODE")
    private String appCode;

    @ApiModelProperty(value = "配置类型：系统内置")
    @TableField("CONFIG_TYPE")
    private String configType;

    @ApiModelProperty(value = "配置代码")
    @TableField("CONFIG_KEY")
    private String configKey;

    @ApiModelProperty(value = "配置显示名称")
    @TableField("CONFIG_NAME")
    private String configName;

    @ApiModelProperty(value = "配置值")
    @TableField("CONFIG_VALUE")
    private String configValue;


}
