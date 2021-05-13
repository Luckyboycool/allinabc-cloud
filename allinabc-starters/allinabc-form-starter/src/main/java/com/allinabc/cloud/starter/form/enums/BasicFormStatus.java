package com.allinabc.cloud.starter.form.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum BasicFormStatus {

    CREATE("CREATE", "Create", "创建"),
    DRAFT("DRAFT", "Draft", "草稿"),
    PROCESSING("PROCESSING", "Processing", "流程中"),
    REJECTED("REJECTED", "Rejected", "被退回"),
    COMPLETED("COMPLETED", "Completed", "已完成"),
    FROZEN("FROZEN", "Frozen", "被冻结"),
    CANCEL("CANCEL", "Canceled", "取消"),
    REDESIGN("REDESIGN", "Redesign","改版"),
    WASTE_VERSION("WASTEVERSION", "Wasteversion","废版");

    private String  code;
    private String  nameEn;
    private String  nameCn;

}
