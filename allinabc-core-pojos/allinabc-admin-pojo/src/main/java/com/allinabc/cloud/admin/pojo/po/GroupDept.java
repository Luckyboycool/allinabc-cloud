package com.allinabc.cloud.admin.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2021/3/2 2:47 下午
 **/@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdmGroupDept对象", description="")
@TableName(value = "ADM_GROUP_DEPT")
public class GroupDept implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "群组ID")
    @TableField("GROUP_ID")
    private String groupId;

    @ApiModelProperty(value = "组织")
    @TableField("DEPT_ID")
    private String deptId;

    /*
    @ApiModelProperty(value = "组织名称")
    @TableField("DEPT_NAME")
    private String deptName;
    */

    @ApiModelProperty(value = "群组名字")
    @TableField("GROUP_NAME")
    private String groupName;

}
