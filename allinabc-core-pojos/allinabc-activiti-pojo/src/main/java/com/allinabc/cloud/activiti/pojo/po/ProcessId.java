package com.allinabc.cloud.activiti.pojo.po;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/26 13:34
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ProcessId对象", description="")
@TableName(value = "ADM_PROCESS_ID",schema = "ADMINDA1")
public class ProcessId  extends BasicInfo {

    private static final long serialVersionUID = 2221161539750875211L;

    @ApiModelProperty(value = "表单类型")
    @TableField("form_type")
    private String formType;

    @ApiModelProperty(value = "流程定义ID")
    @TableField("PROCESS_ID")
    private String processId;
}
