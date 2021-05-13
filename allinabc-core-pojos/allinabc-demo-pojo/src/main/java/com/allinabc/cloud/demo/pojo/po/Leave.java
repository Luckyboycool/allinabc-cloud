package com.allinabc.cloud.demo.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/18 21:57
 **/
@Data
@ApiModel(value="AdmLeave对象", description="")
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ADM_LEAVE")
public class Leave implements Serializable {

    private static final long serialVersionUID = 8474073438931651196L;

    @ApiModelProperty(value = "主键")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "请假原因")
    @TableField("REASON")
    private String reason;

    @ApiModelProperty(value = "请假时长")
    @TableField("DAYS")
    private Double days;
}
