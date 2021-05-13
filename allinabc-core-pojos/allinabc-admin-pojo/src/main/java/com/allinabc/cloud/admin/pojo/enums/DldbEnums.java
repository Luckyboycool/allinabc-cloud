package com.allinabc.cloud.admin.pojo.enums;

/**
 * @author Simon.Xue
 * @date 2021/4/6 5:50 下午
 **/
public interface DldbEnums {
    /**
     * 产品状态
     */
    enum PRODUCT_ID_STATUS {
        RELEASE,CLOSE,ENG;

        /**
         * 查询是否存在
         * @param productIdStatus
         * @return
         */
        public static boolean isExist(PRODUCT_ID_STATUS productIdStatus) {
            for (PRODUCT_ID_STATUS value : PRODUCT_ID_STATUS.values()) {
                if (value.equals(productIdStatus)) {
                    return true;
                }
            }
            return false;
        }
    }
}
