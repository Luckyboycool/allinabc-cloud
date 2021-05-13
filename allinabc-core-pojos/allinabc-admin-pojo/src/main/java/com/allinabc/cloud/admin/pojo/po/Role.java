package com.allinabc.cloud.admin.pojo.po;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

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
@ApiModel(value="AdmRole对象", description="")
@TableName(value = "ADM_ROLE")
public class Role extends BasicInfo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用代码")
    @TableField("APP_CODE")
    private String appCode;

    @ApiModelProperty(value = "角色标识")
    @TableField("ROLE_KEY")
    private String roleKey;

    @ApiModelProperty(value = "角色名称")
    @TableField("ROLE_NAME")
    private String roleName;

    @ApiModelProperty(value = "序号")
    @TableField("SORT_NO")
    private BigDecimal sortNo;

    @ApiModelProperty(value = "角色状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "是否可用")
    @TableField("IS_AVAILABLE")
    private String isAvailable;


}
