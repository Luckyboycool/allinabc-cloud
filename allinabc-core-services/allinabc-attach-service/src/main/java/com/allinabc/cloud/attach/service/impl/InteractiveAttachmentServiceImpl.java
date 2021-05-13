package com.allinabc.cloud.attach.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.attach.adapter.AttachmentAdapterService;
import com.allinabc.cloud.attach.pojo.dto.AttachAfterDTO;
import com.allinabc.cloud.attach.pojo.dto.AttachBeforeDTO;
import com.allinabc.cloud.attach.pojo.dto.AttachmentInfoDTO;
import com.allinabc.cloud.attach.pojo.po.AttachmentType;
import com.allinabc.cloud.attach.pojo.vo.AttachResponse;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description 交互上传服务
 * @Author wangtaifeng
 * @Date 2020/11/17 17:01
 **/
@Service
@Slf4j
public class InteractiveAttachmentServiceImpl implements AttachmentAdapterService {

    @Autowired
    private CommonAttachmentServiceImpl commonAttachmentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Result<AttachResponse> uploadAttachment(MultipartFile file, String bizType, String bizId, AttachmentType attachmentType, HttpServletRequest request) {
        //描述：判断是否有上传之前的交互
        if (!StringUtils.isEmpty(attachmentType.getCallbackBefore())) {
            Result result = callBackBeforeService(bizType, bizId, file, attachmentType);
            if (!result.isSuccess()) {
                return result;
            }
        }
        //描述：调用公共上传服务，存在分布式事务问题，待补偿
        Result<AttachResponse> commonResult = commonAttachmentService.uploadAttachment(file, bizType, bizId, attachmentType, request);
        if (!commonResult.isSuccess()) {
            log.error("公共上传服务失败,失败数据" + JSON.toJSONString(commonResult));
            return Result.failed(commonResult.getMessage());
        }
        AttachResponse data = commonResult.getData();
        //描述：判断是否有上传之后的交互,存在分布式事务问题，待补偿
        if (!StringUtils.isEmpty(attachmentType.getCallbackAfter())) {
            Result result = callBackAfterService(bizType, data.getBizId(), data.getAttachmentId(), attachmentType,file);
            if (!result.isSuccess()) {
                return result;
            }
        }
        log.info("文件上传成功");
        return commonResult;
    }

    @Override
    public void downloadAttachment(AttachmentInfoDTO attachmentInfoDTO, HttpServletRequest request, HttpServletResponse response) {

    }


    /**
     * restTemplate上传前调用业务系统服务
     */
    public Result callBackBeforeService(String bizType, String bizId, MultipartFile file, AttachmentType attachmentType) {
        Result result;
        try {
            String url = attachmentType.getCallbackBefore();
            ImportParams params = new ImportParams();
            List<Map<String, Object>> list = ExcelImportUtil.importExcel(file.getInputStream(), Map.class, params);
            log.info("获取的Excel的中数据为="+JSON.toJSONString(list));
            AttachBeforeDTO attachBeforeDTO = new AttachBeforeDTO(bizId,bizType,list);
            result = restTemplate.postForObject(url, attachBeforeDTO, Result.class);
            log.info("调用接口返回值=" + JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return Result.failed(e.getMessage());
        }
    }


    /**
     * restTemplate上传后调用业务系统服务
     */
    public Result callBackAfterService(String bizType, String bizId, String attachmentId, AttachmentType attachmentType,MultipartFile file) {
        Result result;
        try {
            String url = attachmentType.getCallbackAfter();
            ImportParams params = new ImportParams();
            List<Map<String, Object>> list = ExcelImportUtil.importExcel(file.getInputStream(), Map.class, params);
            log.info("获取的Excel的中数据为="+JSON.toJSONString(list));
            AttachAfterDTO attachAfterDTO = new AttachAfterDTO(bizId,bizType,attachmentId,list);
            result = restTemplate.postForObject(url, attachAfterDTO, Result.class);
            log.info("调用上传文件后接口返回值=" + JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return Result.failed(e.getMessage());
        }
    }

}
