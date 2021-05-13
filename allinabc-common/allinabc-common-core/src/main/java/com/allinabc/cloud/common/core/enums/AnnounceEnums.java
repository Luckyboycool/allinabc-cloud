package com.allinabc.cloud.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公告相关枚举
 * @author Simon.Xue
 * @date 2021/3/11 9:09 下午
 **/
public interface AnnounceEnums {

    /**
     * 公告类型
     */
    @AllArgsConstructor
    @Getter
    enum TYPE {
        IMPORTANT("1","重要通知"),
        NORMAL("0", "普通通知");
        String code;
        String msg;

        public static boolean isExist(String code) {
            for (TYPE value : TYPE.values()) {
                if (value.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 发布类型
     */
    @AllArgsConstructor
    @Getter
    enum TIME_TYPE {
        IMMEDIATE("1", "即时"),
        TIMING("0", "定时");
        String code;
        String msg;

        public static boolean isExist(String code) {
            for (TIME_TYPE value : TIME_TYPE.values()) {
                if (value.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 发布对象
     */
    @AllArgsConstructor
    @Getter
    enum RECEIVE_PERSON {
        ALL("0", "ALL"),
        INSIDE("1", "内部人员"),
        outside("2", "外部人员");
        String code;
        String msg;

        public static boolean isExist(String code) {
            for (RECEIVE_PERSON value : RECEIVE_PERSON.values()) {
                if (value.getCode().equals(code)) {
                    return true;
                }
            }
            return false;
        }
    }
}
