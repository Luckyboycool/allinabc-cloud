package com.allinabc.cloud.common.web.pojo.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/28 11:32
 **/
@Data
public class BaseQueryParam implements Serializable {

    private static final long serialVersionUID = -5708898457504969362L;

    private long pageSize = 10L;
    private long pageNum = 0L;
    private long total = 0L;
    /**
     * 动态排序参数
     * @example
     * {
     *     "field": "REQUEST_NO",
     *     "method"："DESC"
     * }
     */
    private Map<String, String> sortParam;

}
