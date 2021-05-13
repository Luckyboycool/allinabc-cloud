package com.allinabc.cloud.search.api.service;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.search.api.dto.CommonElasticsearchQueryParam;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 公共Elasticsearch服务(增、删、改)  注：与附件服务无交互，供其它业务系统直接调用
 * @Author wangtaifeng
 * @Date 2020/12/4 14:07
 **/
@RestController
@RequestMapping("/common/es")
public interface CommonElasticsearchServiceApi {

    /**
     * 从es中查询记录
     */
    @PostMapping("/search")
    Result<?> search(@RequestBody CommonElasticsearchQueryParam commonElasticsearchQueryParam);

    /**
     * 保存一条记录
     * json：业务系统数据json字符串 id：业务主键 index:索引
     */
    @PostMapping("/add")
    Result<?> add(@RequestParam("json") String json, @RequestParam("id") String id, @RequestParam("index") String index);

    /**
     * 修改一条记录
     * json：业务系统数据json字符串 id：业务主键 index:索引
     */
    @PostMapping("/modify")
    Result<?> modify(@RequestParam("json") String json, @RequestParam("id") String id, @RequestParam("index") String index);

    /**
     * 删除一条记录
     * id：业务主键 index:索引
     */
    @PostMapping("/remove")
    Result<?> remove(@RequestParam("id") String id, @RequestParam("index") String index);

}
