package com.allinabc.cloud.activiti.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/1 16:27
 **/
@Data
@TableName(value = "WEB_LEAVE")
public class Leave implements Serializable {

    private String id;

    private String reason;

    private String processInsId;
}
