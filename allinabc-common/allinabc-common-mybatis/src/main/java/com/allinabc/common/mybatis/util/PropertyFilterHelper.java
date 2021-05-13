package com.allinabc.common.mybatis.util;

import com.allinabc.cloud.common.core.utils.CamelTool;
import com.allinabc.cloud.common.web.pojo.param.PropertyFilter;
import com.allinabc.cloud.common.web.pojo.param.QueryParam;
import com.allinabc.cloud.common.web.pojo.param.SqlOrder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.allinabc.cloud.common.core.utils.constant.QueryConstant.*;

public class PropertyFilterHelper<T> {

    /**
     * 构造mybatis queryWapper
     */
    public static QueryWrapper createQueryWrapper(QueryParam mybatisQueryParam){
        QueryWrapper wapper = new QueryWrapper<>();
        Map<String, String[]> parameters = Maps.newHashMap();
        for(PropertyFilter bean : mybatisQueryParam.getFilters()) {
            parameters.put(bean.getExpression(), bean.getValues());
        }
        //默认为and
        String queryType = mybatisQueryParam.getQueryType();
        mybatisQueryParam.setQueryType(Strings.isNullOrEmpty(queryType)? QUERY_METHOD_AND:queryType);

        parameters.forEach((key, values)->{
            String[] title = key.split("_");
            if (title.length != 3) {
                return;
            }
            String matchType = title[1];
            String matchField = title[2];

            createOrPredicate(queryType, CamelTool.humpToLine2(matchField),values,wapper,matchType);
        });
        //最后进行排序(支持多字段)
        List<SqlOrder> mOrders = mybatisQueryParam.getMbOrder();
        Map<String, List<SqlOrder>> sqlOrderCollect = mOrders.stream().collect(Collectors.groupingBy(SqlOrder::getSortOrder));

        for(Map.Entry<String,List<SqlOrder>> entry :sqlOrderCollect.entrySet()){
            if(entry.getKey().equals(ASC)){
                List<SqlOrder> sqlOrderValue = entry.getValue();
                String[] tmp = new String[sqlOrderValue.size()];
                for (int i = 0; i < sqlOrderValue.size(); i++) {
                    tmp[i] =(CamelTool.humpToLine2(sqlOrderValue.get(i).getSortField()));
                }
                wapper.orderByAsc(tmp);
            }
            if(entry.getKey().equals(DESC)){
                List<SqlOrder> sqlOrderValue = entry.getValue();
                String[] tmp = new String[sqlOrderValue.size()];
                for (int i = 0; i < sqlOrderValue.size(); i++) {
                    tmp[i] =(CamelTool.humpToLine2(sqlOrderValue.get(i).getSortField()));
                }
                wapper.orderByDesc(tmp);
            }
        }
        return wapper;
    }

    /**
     * 构建queryWapper 可参考mybatis-plus官方文档
     * @param queryType
     * @param column
     * @param values
     * @param queryWrapper
     * @param matchType
     */
    public static void createOrPredicate(String queryType,String column,String[] values, QueryWrapper queryWrapper, String matchType) {
        if(null == values || values.length == 0) {
            return;
        }

        for (String value : values) {
            if(Strings.isNullOrEmpty(value)) {
                continue;
            }

            switch (matchType){
                case FILTER_EQUALS:
                    queryWrapper.eq(column, value);
                    if(queryType.equals(QUERY_METHOD_OR)){
                        queryWrapper.or();
                    }
                    break;
                case FILTER_NOT_EQUALS:
                    queryWrapper.ne(column, value);
                    if(queryType.equals(QUERY_METHOD_OR)){
                        queryWrapper.or();
                    }
                    break;
                case FILTER_GREAT_THAN:
                    queryWrapper.gt(column, value);
                    if(queryType.equals(QUERY_METHOD_OR)){
                        queryWrapper.or();
                    }

                    break;
                case FILTER_GREAT_EQUALS:
                    queryWrapper.ge(column ,value);
                    if(queryType.equals(QUERY_METHOD_OR)){
                        queryWrapper.or();
                    }

                    break;
                case FILTER_LESS_THAN:
                    queryWrapper.lt(column, value);
                    if(queryType.equals(QUERY_METHOD_OR)){
                        queryWrapper.or();
                    }

                    break;
                case FILTER_LESS_EQUALS:
                    queryWrapper.le(column, value);
                    if(queryType.equals(QUERY_METHOD_OR)){
                        queryWrapper.or();
                    }
                    break;
                case FILTER_LIKE:
                    queryWrapper.like(column, value);
                    if(queryType.equals(QUERY_METHOD_OR)){
                        queryWrapper.or();
                    }
                    break;
                case FILTER_NULL:
                    queryWrapper.isNull(column);
                    if(queryType.equals(QUERY_METHOD_OR)){
                        queryWrapper.or();
                    }
                    break;
                case FILTER_NOT_NULL:
                    queryWrapper.isNotNull(column);
                    if(queryType.equals(QUERY_METHOD_OR)){
                        queryWrapper.or();
                    }
                    break;
                default:
                    break;
            }
        }

    }

}
