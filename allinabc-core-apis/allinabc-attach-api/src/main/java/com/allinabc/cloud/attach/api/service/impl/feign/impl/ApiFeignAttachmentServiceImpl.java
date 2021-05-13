package com.allinabc.cloud.attach.api.service.impl.feign.impl;

import com.allinabc.cloud.attach.api.service.api.ApiAttachmentService;
import com.allinabc.cloud.attach.api.service.impl.feign.client.AttachmentServiceApi;
import com.allinabc.cloud.attach.pojo.dto.AttachmentQueryParam;
import com.allinabc.cloud.attach.pojo.dto.BizAttachmentListParam;
import com.allinabc.cloud.attach.pojo.vo.AttachmentInfoResponse;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/25 20:58
 **/
@Service
public class ApiFeignAttachmentServiceImpl implements ApiAttachmentService {

    @Autowired
    private AttachmentServiceApi attachmentServiceApi;

    @Override
    public void updateBizIds(BizAttachmentListParam bizAttachmentListParam) {
        attachmentServiceApi.updateBizIds(bizAttachmentListParam);
    }

    @Override
    public List<AttachmentInfoResponse> findAttachByBizId(AttachmentQueryParam attachmentQueryParam) {
        Result<List<AttachmentInfoResponse>> result = attachmentServiceApi.findAttachmentList(attachmentQueryParam);
        return null!=result && null!=result.getData() ? result.getData() : null;
    }
}
