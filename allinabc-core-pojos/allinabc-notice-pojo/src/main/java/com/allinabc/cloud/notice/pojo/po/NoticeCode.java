package com.allinabc.cloud.notice.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 通知编码表
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="NteNoticeCode对象", description="通知编码表")
@TableName(value = "NTE_NOTICE_CODE")
public class NoticeCode implements Serializable {


    private static final long serialVersionUID = -1077397344423322798L;

    @ApiModelProperty(value = "通知编码")
    private String noticeTypeCode;

    @ApiModelProperty(value = "通知类型名称")
    private String noticeTypeName;

    @ApiModelProperty(value = "系统code")
    private String sysCode;


}
