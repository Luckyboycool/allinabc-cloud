package com.allinabc.cloud.attach.api.service.api;

import com.allinabc.cloud.attach.pojo.dto.AttachmentQueryParam;
import com.allinabc.cloud.attach.pojo.dto.BizAttachmentListParam;
import com.allinabc.cloud.attach.pojo.vo.AttachmentInfoResponse;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/25 20:57
 **/

public interface ApiAttachmentService {

    /**
     * 批量更新bizId
     * @param bizAttachmentListParam
     */
    void updateBizIds(BizAttachmentListParam bizAttachmentListParam);

    /**
     * 根据BizId,获取附件列表
     */
    List<AttachmentInfoResponse> findAttachByBizId(AttachmentQueryParam attachmentQueryParam);
}
