package com.allinabc.cloud.activiti.pojo.params;

import com.allinabc.cloud.activiti.pojo.bo.AssignBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/8 18:25
 **/
@Data
public class AssignParam implements Serializable {

    /**
     * 节点审核人
     */
    @ApiModelProperty(value = "指定节点审核人对象集合")
    private List<AssignBO> assignList;
}
