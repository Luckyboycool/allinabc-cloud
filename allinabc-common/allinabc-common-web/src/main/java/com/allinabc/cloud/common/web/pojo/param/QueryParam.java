package com.allinabc.cloud.common.web.pojo.param;


import com.allinabc.cloud.common.web.constant.QueryConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QueryParam extends BaseQueryParam {

    /**
     * 多条件查询是 OR 还是 AND
     */
    private String  queryType;

    /**
     * JPA方式排序字段集合
     */
    //private Sort orders;

    /**
     * 过滤条件
     */
    private List<PropertyFilter>    filters;

    /**
     * mybatis方式排序字段集合
     */
    private List<SqlOrder> mbOrder;

    public QueryParam() {

    }
    public QueryParam(List<PropertyFilter> filters) {
        this.queryType = QueryConstant.QUERY_METHOD_AND;
        this.filters = filters;
    }

    public QueryParam(String queryType, List<PropertyFilter> filters) {
        this.queryType = queryType;
        this.filters = filters;
    }

}
