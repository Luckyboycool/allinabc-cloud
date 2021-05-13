package com.allinabc.cloud.attach.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 附件类型表
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AttAttachmentType对象", description="附件类型表")
@TableName("ATT_ATTACHMENT_TYPE")
public class AttachmentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "web_system.sys_application.app_code")
    private String sysCode;

    @ApiModelProperty(value = "web_system.sys_resource.RES_KEY")
    private String resCode;

    @ApiModelProperty(value = "业务类型字段")
    private String bizType;

    @ApiModelProperty(value = "存储类型(关联web_system.sys_dict_data.dict_value")
    private String storageCode;

    @ApiModelProperty(value = "文件存储根路径名称(S3-对应桶名称)")
    private String fileRootName;

    @ApiModelProperty(value = "附件类型(0-通用模板, 1-通用附件，2-上传待处理)")
    private String uploadType;

    @ApiModelProperty(value = "上传前调用")
    private String callbackBefore;

    @ApiModelProperty(value = "上传后调用")
    private String callbackAfter;

    @ApiModelProperty(value = "备注")
    private String remark;


}
