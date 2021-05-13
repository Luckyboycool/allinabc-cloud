package com.allinabc.cloud.attach.pojo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/10/18 17:57
 **/
public class QueryTableResult<T> implements Serializable {

    private static final long serialVersionUID = -2197218559645340131L;

    private List<T> rows = new ArrayList(0);
    private long total = 0L;

    public QueryTableResult() {
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public static final <T> QueryTableResult<T> failure() {
        return new QueryTableResult();
    }

    public static final <T> QueryTableResult<T> success(long total) {
        return success(total, (List)null);
    }

    public static final <T> QueryTableResult<T> success(List<T> rows) {
        return success(0L, rows);
    }

    public static final <T> QueryTableResult<T> success(long total, List<T> rows) {
        QueryTableResult<T> table = new QueryTableResult();
        table.setTotal(total);
        table.setRows(rows);
        return table;
    }
}
