package com.allinabc.cloud.admin.pojo.po;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="AdmNotice对象", description="")
@TableName(value = "ADM_NOTICE")
public class Notice extends BasicInfo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通知类型")
    @TableField("NOTICE_TYPE")
    private String noticeType;

    @ApiModelProperty(value = "通知标题")
    @TableField("NOTICE_TITLE")
    private String noticeTitle;

    @ApiModelProperty(value = "正文")
    @TableField("NOTICE_CONTENT")
    private String noticeContent;

    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private String status;


}
