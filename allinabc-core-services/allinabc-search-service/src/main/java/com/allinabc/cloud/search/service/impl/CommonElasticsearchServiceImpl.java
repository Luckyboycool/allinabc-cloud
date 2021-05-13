package com.allinabc.cloud.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.search.api.dto.CommonElasticsearchQueryParam;
import com.allinabc.cloud.search.service.CommonElasticsearchService;
import com.allinabc.cloud.search.util.ESUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/4 11:09
 **/
@Service
@Slf4j
public class CommonElasticsearchServiceImpl implements CommonElasticsearchService {

    @Autowired
    private ESUtil esUtil;

    @Override
    public Result<?> search(CommonElasticsearchQueryParam commonElasticsearchQueryParam) {
        log.info("公共es查询接口请求参数="+ JSON.toJSONString(commonElasticsearchQueryParam));
        if(StringUtils.isEmpty(commonElasticsearchQueryParam.getId())){
            //模糊查询
            List<Map<String, Object>> result = esUtil.searchByName(commonElasticsearchQueryParam.getKeyWord(), commonElasticsearchQueryParam.getIndex());
            return Result.success(result);
        }else{
            //精准查询
            GetResponse response = esUtil.findById(commonElasticsearchQueryParam.getId(), commonElasticsearchQueryParam.getIndex());
            Map<String, Object> sourceAsMap = response.getSourceAsMap();
            log.info("根据id查询数据="+JSON.toJSONString(sourceAsMap));
            return Result.success(sourceAsMap);
        }
    }

    @Override
    public Result<?> add(String json, String id, String index) {
        log.info("ES新增一条记录请求参数：json="+json+",id="+id+",index="+index);
        IndexResponse indexResponse = esUtil.saveJsonString(json, id, index);
        log.info("ES存储一条数据返回值="+ JSON.toJSONString(indexResponse));
        return Result.success(indexResponse);
    }

    @Override
    public Result<?> modify(String json, String id, String index) {
        log.info("ES修改一条记录请求参数：json="+json+",id="+id+",index="+index);
        UpdateResponse updateResponse = esUtil.updateByJsonString(json, id, index);
        log.info("ES修改一条数据返回值="+ JSON.toJSONString(updateResponse));
        return Result.success(updateResponse);
    }

    @Override
    public Result<?> remove(String id, String index) {
        log.info("调用删除ES一条记录接口请求参数:id="+id+",index="+index);
        DeleteResponse deleteResponse = esUtil.deleteById(index, id);
        log.info("调用删除ES中一条记录返回值="+ JSON.toJSONString(deleteResponse));
        return Result.success(deleteResponse);
    }
}
