package com.allinabc.cloud.activiti.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TaskEnum {

    START("start","开始"),
    END("end","流程已结束");

    private String code;
    private String name;

}
