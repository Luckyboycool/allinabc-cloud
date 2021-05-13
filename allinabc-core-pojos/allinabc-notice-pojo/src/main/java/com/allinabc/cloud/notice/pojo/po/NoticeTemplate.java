package com.allinabc.cloud.notice.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangtaifeng
 * @since 2021-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="NoticeTemplate对象", description="")
@TableName(value = "NTE_NOTICE_TEMPLATE")
public class NoticeTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "模板主题")
    private String subject;

    @ApiModelProperty(value = "模板内容")
    private String content;

    @ApiModelProperty(value = "模板内容是否是html")
    private Boolean isHtml;

    @ApiModelProperty(value = "模板类型(0:邮件1：短信，2：站内信)")
    private String type;

    @ApiModelProperty(value = "模板用途")
    private String noticeTypeCode;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "逻辑状态 (常量值：Y-逻辑保留，N-逻辑删除，默认值：Y)")
    private String isAvailable;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTm;

    @ApiModelProperty(value = "修改人")
    private String updatedBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTm;

    public NoticeTemplate() {
    }

    public NoticeTemplate(String createdBy, Date createTm, String updatedBy, Date updateTm) {
        this.createdBy = createdBy;
        this.createTm = createTm;
        this.updatedBy = updatedBy;
        this.updateTm = updateTm;
    }
}
