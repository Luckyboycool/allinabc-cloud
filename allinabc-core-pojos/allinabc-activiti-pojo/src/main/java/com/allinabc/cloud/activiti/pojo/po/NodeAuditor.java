package com.allinabc.cloud.activiti.pojo.po;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/3 19:18
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ADM_NODE_AUDITOR",schema = "ADMINDA1")
@NoArgsConstructor
public class NodeAuditor extends BasicInfo {

    @ApiModelProperty(value = "节点ID")
    @TableField("NODE_ID")
    private String nodeId;

    @ApiModelProperty(value = "流程定义ID")
    @TableField("PROCESS_ID")
    private String processId;

    @ApiModelProperty(value = "审核人类型：1:用户、2:角色、3:群组、4:部门、5:指定节点审批人的直属主管6:申请人的直属主管7:当前申请人")
    @TableField("AUDITOR_TYPE")
    private String auditorType;

    @ApiModelProperty(value = "审批人")
    @TableField("AUDITOR")
    private String auditor;

    public NodeAuditor(String nodeId,String processId ,String auditorType,String auditor){
        this.processId = processId;
        this.nodeId = nodeId;
        this.auditorType =auditorType;
        this.auditor =auditor;
    }
}
