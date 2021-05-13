package com.allinabc.cloud.starter.web.util;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

public class BeanMergeUtils {

    public static Object merge(Object srcBean, Object tarBean) {
        Class srcBeanClass = srcBean.getClass();
        Class tarBeanClass = tarBean.getClass();

        List<Field> srcFields = FieldUtils.getAllFieldsList(srcBeanClass);
        List<Field> tarFields = FieldUtils.getAllFieldsList(tarBeanClass);

        for (int i = 0; i < srcFields.size(); i++) {
            Field srcField = srcFields.get(i);
            Field tarField = tarFields.get(i);

            srcField.setAccessible(true);
            tarField.setAccessible(true);
            try {
                if (srcField.get(srcBean) != null) {
                    tarField.set(tarBean, srcField.get(srcBean));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return tarBean;
    }


}
