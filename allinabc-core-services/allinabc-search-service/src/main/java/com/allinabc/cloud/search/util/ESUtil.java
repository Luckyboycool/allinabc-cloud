package com.allinabc.cloud.search.util;


import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.search.pojo.dto.ESFileObj;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @Description es查询工具类
 * @Author wangtaifeng
 * @Date 2020/10/14 16:11
 * @Version V1.0
 **/
@Component
@Slf4j
public class ESUtil<T> {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 将附件放入ES中
     *
     * @param inputStream
     * @param file
     * @param index
     * @return
     * @throws IOException
     */
    public IndexResponse saveAttachment(InputStream inputStream, ESFileObj file, String index) throws IOException {
        byte[] bytes = this.readInputStream(inputStream);
        String base64 = Base64.getEncoder().encodeToString(bytes);
        file.setContent(base64);
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(file.getId());
        //使用attachment pipline进行提取文件
        indexRequest.source(JSON.toJSONString(file), XContentType.JSON);
        indexRequest.setPipeline("attachment");
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse;

    }

    /**
     * 存入json数据往es中
     */
    public IndexResponse save(Object t, String index, String id) {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(id);
        indexRequest.source(JSON.toJSONString(t), XContentType.JSON);
        IndexResponse indexResponse = null;
        try {
            indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return indexResponse;
    }


    /**
     * 根据ID查询es中的信息
     */
    public GetResponse findById(String id, String index) {
        GetRequest request = new GetRequest();
        request.id(id);
        request.index(index);
        GetResponse response = null;
        try {
            response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("根据ID获取ES中的数据异常" + e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        log.info("ES根据id获取的数据=" + JSON.toJSONString(response));
        return response;
    }

    /**
     * 根据ID删除es中的信息
     */
    public DeleteResponse deleteById(String index, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("根据ID=" + id + "  删除ES中的数据异常" + e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        if (!deleteResponse.getResult().equals("DELETED")) {
            log.error("根据id删除es中数据失败id=" + id);
            throw new RuntimeException("ES删除失败id=" + id);
        }
        return deleteResponse;
    }


    public UpdateResponse updateById(Object obj, String index, String id) {
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.doc(JSON.toJSONString(obj), XContentType.JSON);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("根据ID=" + id + "  修改ES中的数据异常" + e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return updateResponse;
    }

    /**
     * 全文检索模糊查询（针对文件）
     */
    public List<Map<String, Object>> searchByName(String keyword, String index) {
        try {
            List<Map<String, Object>> result =new ArrayList();
            SearchRequest searchRequest = new SearchRequest(index);
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("attachment.content", keyword).analyzer("ik_smart"));
            HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("attachment.content");
            highlightBuilder.field(highlightContent);
            highlightBuilder.preTags("<span style=\"color:red\">");
            highlightBuilder.postTags("</span>");
            searchSourceBuilder.highlighter(highlightBuilder);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit searchHit : searchHits) {
                Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                HighlightField titleField = highlightFields.get("attachment.content");

                Map<String, Object> source = (Map<String, Object>) searchHit.getSourceAsMap().get("attachment");

                if (titleField != null) {
                    Text[] fragments = titleField.fragments();
                    String name = "";
                    for (Text text : fragments) {
                        name += text;
                    }
                    source.put("content", name);   //高亮字段替换掉原本的内容
                }
                result.add(searchHit.getSourceAsMap());
            }
            log.info("返回结果集："+JSON.toJSONString(result));
            return result;
        } catch (
                IOException e) {
            log.error("根据名称查询异常" + e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    /**
     * 存入json字符串到ES中
     *
     * @param json  业务字段json字符串
     * @param id    主键
     * @param index 索引
     */
    public IndexResponse saveJsonString(String json, String id, String index) {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(id);
        indexRequest.source(json, XContentType.JSON);
        IndexResponse indexResponse = null;
        try {
            indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es存储数据异常，异常数据:json=" + json + ",id=" + id + ",index=" + index, e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return indexResponse;
    }

    /**
     * 修改json字符串到ES中
     *
     * @param json  业务字段json字符串
     * @param id    主键
     * @param index 索引
     */
    public UpdateResponse updateByJsonString(String json, String id, String index) {
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.doc(json, XContentType.JSON);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("id=" + id + ",index=" + index + " 修改ES中的数据异常" + e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return updateResponse;
    }
}
