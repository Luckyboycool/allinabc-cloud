//package com.allinabc.cloud.attach.api.service.impl.feign.client;
//
//
//import com.allinabc.cloud.attach.pojo.dto.AttachAfterDTO;
//import com.allinabc.cloud.attach.pojo.dto.AttachBeforeDTO;
//import com.allinabc.cloud.common.web.pojo.resp.Result;
//
///**
// * @Description 公共上传附件前调用服务接口（所有需要实现此业务逻辑的服务需要单独实现，以供附件服务调用）
// * @Author wangtaifeng
// * @Date 2021/1/7 14:04
// **/
//public interface CommonInteractiveUploadApi {
//
//    /**
//     * 上传前调用
//     * @param attachBeforeDTO
//     * @return
//     */
//    Result<?> BeforeUploadAttach(AttachBeforeDTO attachBeforeDTO);
//
//    /**
//     * 上传后调用
//     * @param attachAfterDTO
//     * @return
//     */
//    Result<?> AfterUploadAttach(AttachAfterDTO attachAfterDTO);
//}
