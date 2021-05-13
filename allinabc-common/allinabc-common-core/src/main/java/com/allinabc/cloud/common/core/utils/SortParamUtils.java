package com.allinabc.cloud.common.core.utils;

import cn.hutool.core.collection.CollUtil;
import com.allinabc.cloud.common.core.enums.SortEnum;
import com.allinabc.cloud.common.core.utils.constant.QueryConstant;

import java.util.Collections;
import java.util.Map;

/**
 * @author Simon.Xue
 * @date 2021/4/30 1:43 下午
 **/
public class SortParamUtils {
    /**
     * 去掉不符合条件的字段
     * @param sortParam
     * @return
     */
    public static Map<String, String> handleSortParam(Map<String, String> sortParam) {
        if (CollUtil.isEmpty(sortParam)) {
            return Collections.emptyMap();
        }
        if (!SortEnum.FieldEnum.checkIsExist(sortParam.get(QueryConstant.SORT_FIELD))) {
            return Collections.emptyMap();
        }


        if (!SortEnum.MethodEnum.checkIsExist(sortParam.get(QueryConstant.SORT_METHOD))) {
            return Collections.emptyMap();
        }

        sortParam.put(QueryConstant.SORT_FIELD, SortEnum.FieldEnum.getName(sortParam.get(QueryConstant.SORT_FIELD)));
        sortParam.put(QueryConstant.SORT_METHOD, SortEnum.MethodEnum.getName(sortParam.get(QueryConstant.SORT_METHOD)));
        return sortParam;
    }
}
