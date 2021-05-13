package com.allinabc.cloud.demo.pojo.vo;

import com.allinabc.cloud.starter.form.domain.BasicForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/18 21:32
 **/
@Data
public class LeaveVO implements Serializable {

    private List<BasicForm> basicFormList;

    @ApiModelProperty(value = "请假原因")
    private String reason;

    @ApiModelProperty(value = "请假时长")
    private Double days;
}
