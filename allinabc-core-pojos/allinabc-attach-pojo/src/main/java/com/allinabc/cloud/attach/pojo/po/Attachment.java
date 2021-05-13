package com.allinabc.cloud.attach.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Attachment对象", description="附件表")
@TableName("ATT_ATTACHMENT")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "关联业务主键ID")
    private String bizId;

    @ApiModelProperty(value = "关联att_attachment_type.biz_type")
    private String bizType;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "原始文件名称")
    private String originalFileName;

    @ApiModelProperty(value = "文件大小")
    private BigDecimal fileSize;

    @ApiModelProperty(value = "文件url，相对位置")
    private String fileUrl;

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

    /**
     * 文档内容
     */
   // private String content;


    public Attachment() {
    }

    public Attachment(String createdBy, Date createTm, String updatedBy, Date updateTm) {
        this.createdBy = createdBy;
        this.createTm = createTm;
        this.updatedBy = updatedBy;
        this.updateTm = updateTm;
    }

    public Attachment(String updatedBy, Date updateTm) {
        this.updatedBy = updatedBy;
        this.updateTm = updateTm;
    }
}
