package com.allinabc.cloud.admin.pojo.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * @author wangtaifeng
 * @since 2020-12-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="AdmApplication对象", description="")
@TableName(value = "ADM_APPLICATION")
public class Application extends BasicInfo {

    @ApiModelProperty(value = "系统code")
    @TableField("APP_CODE")
    @Excel(name = "appCode")
    private String appCode;

    @ApiModelProperty(value = "系统名称")
    @TableField("APP_NAME")
    @Excel(name = "appName")
    private String appName;

    @ApiModelProperty(value = "系统描述")
    @TableField("APP_DESC")
    @Excel(name = "appDesc")
    private String appDesc;

    @ApiModelProperty(value = "路径CODE")
    @TableField("PATH_CODE")
    @Excel(name = "pathCode")
    private String pathCode;

    @ApiModelProperty(value = "路径")
    @TableField("PATH")
    @Excel(name = "path")
    private String path;


}
