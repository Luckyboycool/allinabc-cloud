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
 * @author wangtaifeng
 * @since 2020-12-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="AdmDictType对象", description="")
@TableName(value = "ADM_DICT_TYPE")
public class DictType extends BasicInfo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "程序代码")
    @TableField("APP_CODE")
    private String appCode;

    @ApiModelProperty(value = "字典类型")
    @TableField("DICT_TYPE")
    private String dictType;

    @ApiModelProperty(value = "字典名称")
    @TableField("DICT_NAME")
    private String dictName;

    @ApiModelProperty(value = "字典状态")
    @TableField("STATUS")
    private String status;


}
