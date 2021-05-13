package com.allinabc.cloud.common.web.pojo.param;

import lombok.Data;

@Data
public class PropertyFilter {

    private String expression;
    private String[] values;

    public static PropertyFilter create(String expression, String... values) {
        PropertyFilter bean = new PropertyFilter();
        bean.setExpression(expression);
        bean.setValues(values);
        return bean;
    }

}
