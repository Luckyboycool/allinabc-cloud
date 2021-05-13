package com.allinabc.cloud.demo.pojo.dto;

import com.allinabc.cloud.starter.form.domain.BasicForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/18 21:05
 **/
@Data
public class LeaveDTO extends BasicForm {

    @ApiModelProperty(value = "请假原因",required = true)
    private String reason;

    @ApiModelProperty(value = "请假时长",required = true)
    private Double days;

}
