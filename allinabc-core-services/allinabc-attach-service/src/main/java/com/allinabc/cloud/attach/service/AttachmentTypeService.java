package com.allinabc.cloud.attach.service;

import com.allinabc.cloud.attach.pojo.dto.AttachmentTypeQueryParam;
import com.allinabc.cloud.attach.pojo.dto.AttachmentTypeRequest;
import com.allinabc.cloud.attach.pojo.po.AttachmentType;
import com.allinabc.cloud.attach.pojo.vo.QueryTableResult;
import com.allinabc.cloud.common.web.pojo.resp.Result;

/**
 * <p>
 * 附件类型表 服务类
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-11-16
 */
public interface AttachmentTypeService {

    /**
     * 新增单个附件类型配置
     */
    Result<String> addAttachmentType(AttachmentTypeRequest attachmentTypeRequest);

    /**
     * 删除单个附件类型配置
     */
    Result<Void> removeAttachmentType(AttachmentTypeRequest attachmentTypeRequest);

    /**
     * 查询附件类型配置(可根据传入的sysCode、resCode、bizType查询)
     */
    Result<QueryTableResult<AttachmentType>> findAttachmentType(AttachmentTypeQueryParam attachmentTypeQueryParam, Long pageNum, Long pageSize);
}
