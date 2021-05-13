package com.allinabc.cloud.common.web.pojo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {

    private Long    pageNo;
    private Long    pageSize;
    private Long    totalPage;
    private Long    totalCount;
    private List<T> list;

    public Page(long pageNo, long pageSize, long totalPage, long totalCount, List records) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.list = records;
    }

}
