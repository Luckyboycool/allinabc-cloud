package com.allinabc.cloud.search.service;


import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.search.api.dto.CommonElasticsearchQueryParam;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/4 11:08
 **/
public interface CommonElasticsearchService {

    /**
     * 根据传入的索引值和搜索值从ES中查询对应的数据
     */
    Result<?> search(CommonElasticsearchQueryParam commonElasticsearchQueryParam);

    /**
     * ES保存一条记录 json: 业务系统数据  id：业务主键 index:索引
     */
    Result<?> add(String json, String id, String index);

    /**
     * ES修改一条记录 json: 业务系统数据  id：业务主键 index:索引
     */
    Result<?> modify(String json, String id, String index);

    /**
     * 根据ID和index从ES删除一条数据
     */
    Result<?> remove(String id, String index);
}
