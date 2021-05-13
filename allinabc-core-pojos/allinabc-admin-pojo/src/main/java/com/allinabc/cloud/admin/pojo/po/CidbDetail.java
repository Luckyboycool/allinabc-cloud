package com.allinabc.cloud.admin.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * CIDB_DETAIL
 * @author 
 */
@Data
@ApiModel(value="AdmCidbDetail对象", description="")
@TableName(value = "ADM_CIDB_DETAIL")
public class CidbDetail implements Serializable {
    private String id;

    /**
     * 区域(HC,LG)
     */
    private String area;

    /**
     * 部门（Sales，Backup Sales，CE，Backup CE，CS，Backup CS）
     */
    private String dept;

    /**
     * 组员
     */
    private String members;

    /**
     * 客户三位代码
     */
    private String custCode;

    private static final long serialVersionUID = 1L;
}