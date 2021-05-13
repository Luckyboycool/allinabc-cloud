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
 * @author wangtaifeng
 * @since 2020-12-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="AdmDictData对象", description="")
@TableName(value = "ADM_DICT_DATA")
public class DictData extends BasicInfo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典类型")
    @TableField("DICT_TYPE")
    private String dictType;

    @ApiModelProperty(value = "字典数据标签")
    @TableField("DICT_LABEL")
    private String dictLabel;

    @ApiModelProperty(value = "字典数据值")
    @TableField("DICT_VALUE")
    private String dictValue;

    @ApiModelProperty(value = "字典数据顺序")
    @TableField("DICT_SORT")
    private BigDecimal dictSort;

    @ApiModelProperty(value = "是否默认")
    @TableField("IS_DEFAULT")
    private String isDefault;

    @ApiModelProperty(value = "表格样式")
    @TableField("LIST_CLASS")
    private String listClass;

    @ApiModelProperty(value = "列表CSS样式")
    @TableField("CSS_CLASS")
    private String cssClass;

    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "组类型")
    @TableField("DICT_GROUP_TYPE")
    private String dictGroupType;


}
