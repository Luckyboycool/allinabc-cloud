package com.allinabc.cloud.common.web.pojo.param;

import lombok.Data;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/21 16:55
 **/
@Data
public class SqlOrder {

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方式
     */
    private String sortOrder;
}
