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
@ApiModel(value="AdmResource对象", description="")
@TableName(value = "ADM_RESOURCE")
public class Resource extends BasicInfo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "程序代码")
    @TableField("APP_CODE")
    private String appCode;

    @ApiModelProperty(value = "父资源主键")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "资源编码")
    @TableField("RES_KEY")
    private String resKey;

    @ApiModelProperty(value = "资源名称")
    @TableField("RES_NAME")
    private String resName;

    @ApiModelProperty(value = "资源类型（M目录 C菜单 F功能）")
    @TableField("RES_TYPE")
    private String resType;

    @ApiModelProperty(value = "菜单布局")
    @TableField("COMPONENT")
    private String component;

    @ApiModelProperty(value = "链接")
    @TableField("PATH")
    private String path;

    @ApiModelProperty(value = "权限标识")
    @TableField("PERMS")
    private String perms;

    @ApiModelProperty(value = "菜单图标")
    @TableField("ICON")
    private String icon;

    @ApiModelProperty(value = "显示顺序")
    @TableField("SORT_NO")
    private String sortNo;

    @ApiModelProperty(value = "资源状态（0显示 1隐藏）")
    @TableField("VISIBLE")
    private String visible;

    @ApiModelProperty(value = "打开方式")
    @TableField("TARGET")
    private String target;

    @ApiModelProperty(value = "重定向")
    @TableField("REDIRECT")
    private String redirect;

    @ApiModelProperty(value = "是否默认（0默认 1否默认）")
    @TableField("DEFAULTED")
    private String defaulted;


}
