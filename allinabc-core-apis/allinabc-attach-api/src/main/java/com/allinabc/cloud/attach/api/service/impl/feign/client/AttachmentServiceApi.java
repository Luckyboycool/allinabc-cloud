package com.allinabc.cloud.attach.api.service.impl.feign.client;

import com.allinabc.cloud.attach.pojo.dto.AttachmentQueryParam;
import com.allinabc.cloud.attach.pojo.dto.BizAttachmentListParam;
import com.allinabc.cloud.attach.pojo.vo.AttachmentInfoResponse;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/25 20:58
 **/
@FeignClient(name = ServiceNameConstants.ATTACHMENT_SERVICE)
public interface AttachmentServiceApi {

    @PostMapping("/attachment/bizids")
    Result<Void> updateBizIds(@RequestBody BizAttachmentListParam bizAttachmentListParam);

    @PostMapping("/attachment/list")
    Result<List<AttachmentInfoResponse>> findAttachmentList(@RequestBody AttachmentQueryParam attachmentQueryParam);

}

