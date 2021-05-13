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
 * 通知参与表
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="NteNoticeParticipant对象", description="通知参与表")
@TableName(value = "NTE_NOTICE_PARTICIPANT")
public class NoticeParticipant implements Serializable {


    private static final long serialVersionUID = 3334972742947601395L;

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "通知ID")
    private String noticeId;

    @ApiModelProperty(value = "NOTICE_CC/NOTICE_TO")
    private String participantType;

    @ApiModelProperty(value = "参与对象类型 USER/GROUP/DEPARTMENT/COMPANY/")
    private String objectType;

    @ApiModelProperty(value = "参与对象主键")
    private String objectId;

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

    public NoticeParticipant() {
    }

    public NoticeParticipant(String createdBy, Date createTm, String updatedBy, Date updateTm) {
        this.createdBy = createdBy;
        this.createTm = createTm;
        this.updatedBy = updatedBy;
        this.updateTm = updateTm;
    }
}
