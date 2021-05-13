package com.allinabc.cloud.search.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/4 11:03
 **/
@Data
public class CommonElasticsearchQueryParam implements Serializable {

    @ApiModelProperty(value = "查询值",required = true)
    private String keyWord;

    @ApiModelProperty(value = "索引值",required = true)
    private String index;

    @ApiModelProperty(value = "主键",required = false)
    private String id;

}
