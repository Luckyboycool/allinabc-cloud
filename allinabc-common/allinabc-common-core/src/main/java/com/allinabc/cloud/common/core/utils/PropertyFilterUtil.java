package com.allinabc.cloud.common.core.utils;

import com.allinabc.cloud.common.web.pojo.param.PropertyFilter;
import com.allinabc.cloud.common.web.pojo.param.QueryParam;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Sort;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.allinabc.cloud.common.web.constant.QueryConstant.FILTER_PREFIX;
import static com.allinabc.cloud.common.web.constant.QueryConstant.QUERY_METHOD_AND;


public class PropertyFilterUtil {

    public final static List<String> QUERY_LIST = Arrays.asList("EQ","NE","GT","GE","LT","LE","LK");

    public static QueryParam buildFromRequest(HttpServletRequest request){
        QueryParam param = new QueryParam();

        List<PropertyFilter> filters = Lists.newArrayList();
        if(null == request || null == request.getParameterMap()) {
            return param;
        }

        // 获取查询参数
        Map<String, String[]> parameters = request.getParameterMap();
        // 过滤查询参数，仅保留"filter_"前缀的参数
        parameters = parameters.entrySet().stream()
                .filter(i -> i.getKey().startsWith(FILTER_PREFIX))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // 转换为PropertyFilter
        for(String key : parameters.keySet()) {
            PropertyFilter filter = new PropertyFilter();
            filter.setExpression(key);
            filter.setValues(parameters.get(key));
            filters.add(filter);
        }
        param.setFilters(filters);

        // 获取查询并行条件(多条件是AND 还是 OR)
        String queryType = request.getParameter("q");
        queryType = Strings.isNullOrEmpty(queryType) ? QUERY_METHOD_AND : queryType;
        param.setQueryType(queryType);

        // 获取排序条件
        String orderExpr = request.getParameter("o");
        Sort sort = buildSortFromRequest(orderExpr);
        //如果使用JPA，请取消QueryParam的注释
        //param.setOrders(sort);

        return param;
    }

    public static Sort buildSortFromRequest(String orderExpr) {
        if(Strings.isNullOrEmpty(orderExpr)) {
            return null;
        }

        String[] orders = orderExpr.split(",");
        List<Sort.Order> orderList = Lists.newArrayList();
        if(orders.length > 0) {
            for(String order : orders) {
                order = order.trim();
                String[] params = order.split(" ");
                List<String> values = Arrays.stream(params).filter(i -> !Strings.isNullOrEmpty(i)).collect(Collectors.toList());
                if(values.size() != 2) {
                    throw new IllegalArgumentException(String.format("expression error in sort field %s", order));
                }
                Sort.Direction tDirection = Sort.Direction.fromString(values.get(1));
                Sort.Order tOrder = new Sort.Order(tDirection, values.get(0));
                orderList.add(tOrder);
            }
        }
        return Sort.by(orderList);
    }

}
