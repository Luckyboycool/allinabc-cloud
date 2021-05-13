package com.allinabc.cloud.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Simon.Xue
 * @date 2021/4/30 2:20 下午
 **/
public interface SortEnum {
    /**
     * 字段枚举
     */
    @Getter
    @AllArgsConstructor
    enum FieldEnum {
        REQUEST_NO("requestNo"),SUBJECT("subject"),REQUEST_TIME("requestTime");
        String code;

        /**
         * 判断字段是否存在
         * @param field
         * @return
         */
        public static boolean checkIsExist(String field) {
            for (FieldEnum value : FieldEnum.values()) {
                if (value.getCode().equals(field)) {
                    return true;
                }
            }
            return false;
        }

        public static String getName(String code) {
            for (FieldEnum value : FieldEnum.values()) {
                if (value.getCode().equals(code)) {
                    return value.name();
                }
            }
            return "";
        }
    }

    /**
     * 类型枚举
     */
    @Getter
    @AllArgsConstructor
    enum MethodEnum {
        ASC("ascend"),DESC("descend");
        String code;

        public static boolean checkIsExist(String type) {
            for (MethodEnum value : MethodEnum.values()) {
                if (value.getCode().equals(type)) {
                    return true;
                }
            }
            return false;
        }

        public static String getName(String code) {
            for (MethodEnum value : MethodEnum.values()) {
                if (value.getCode().equals(code)) {
                    return value.name();
                }
            }
            return "";
        }
    }

}
