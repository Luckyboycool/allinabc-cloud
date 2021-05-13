package com.allinabc.cloud.starter.form.param;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizFormParam implements Serializable {

    private BasicFormParam  form;
    private BasicAuditParam audit;
    private List<String>    attachmentIds;

    /**
     * 新增、修改、审批
     */
    private String          createType;
    /**
     * 保存、提交、改版
     */
    private String          actionType;


    /**
     * 流程变量
     */
    private Map<String,Object> variables;

    public boolean isDraft() {
        return StringUtils.equalsIgnoreCase("Y", form.getIsDraft());
    }

    public boolean isCreate() {
        return StringUtils.equals("CREATE", createType);
    }

    public boolean isSubmit() {
        return Lists.newArrayList("SUBMIT", "REVISION").contains(actionType.toUpperCase());
    }

}
