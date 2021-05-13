package com.allinabc.cloud.search.controller;

import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.search.api.dto.CommonElasticsearchQueryParam;
import com.allinabc.cloud.search.api.service.CommonElasticsearchServiceApi;
import com.allinabc.cloud.search.service.CommonElasticsearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 公共ES查询服务
 * @Author wangtaifeng
 * @Date 2020/12/4 10:58
 **/
@RestController
@RequestMapping("/common/es")
public class CommonElasticsearchServiceController implements CommonElasticsearchServiceApi {

    @Autowired
    private CommonElasticsearchService commonElasticsearchService;


    /**
     * 公共es查询接口
     */
    @ApiOperation(value = "通用es查询接口",notes = "通用es查询接口")
    @PostMapping("/search")
    public Result<?> search(@RequestBody CommonElasticsearchQueryParam commonElasticsearchQueryParam){
        if(StringUtils.isEmpty(commonElasticsearchQueryParam.getId())){
            if(StringUtils.isEmpty(commonElasticsearchQueryParam.getKeyWord())){
                return Result.failed("search value not null");
            }
        }
        if(StringUtils.isEmpty(commonElasticsearchQueryParam.getIndex())){
            return Result.failed("index not null");
        }
       return commonElasticsearchService.search(commonElasticsearchQueryParam);
    }

    /**
     * 保存一条记录
     * json：业务系统数据json字符串 id：业务主键 index:索引
     */
    @ApiOperation(value = "通用es保存一条记录",notes = "通用es保存一条记录")
    @PostMapping("/add")
    public Result<?> add(@RequestParam(value = "json")String json, @RequestParam("id")String id, @RequestParam("index")String index) {
        return commonElasticsearchService.add(json, id, index);
    }

    /**
     * 修改一条记录
     * json：业务系统数据json字符串 id：业务主键 index:索引
     */
    @ApiOperation(value = "通用es修改一条记录",notes = "通用es修改一条记录")
    @PostMapping("/modify")
    public Result<?> modify(@RequestParam("json")String json, @RequestParam("id")String id, @RequestParam("index")String index) {
        return commonElasticsearchService.modify(json, id, index);
    }

    /**
     * 删除一条记录
     * id：业务主键 index:索引
     */
    @ApiOperation(value = "通用es删除一条记录",notes = "通用es删除一条记录")
    @PostMapping("/remove")
    public Result<?> remove(@RequestParam("id") String id, @RequestParam("index") String index) {
        return commonElasticsearchService.remove(id, index);
    }
}
