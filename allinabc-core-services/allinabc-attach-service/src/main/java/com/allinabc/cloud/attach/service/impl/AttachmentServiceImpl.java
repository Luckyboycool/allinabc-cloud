package com.allinabc.cloud.attach.service.impl;


import com.allinabc.cloud.attach.adapter.AttachmentAdapterService;
import com.allinabc.cloud.attach.mapper.AttachmentMapper;
import com.allinabc.cloud.attach.mapper.AttachmentTypeMapper;
import com.allinabc.cloud.attach.pojo.dto.AttachmentInfoDTO;
import com.allinabc.cloud.attach.pojo.dto.AttachmentQueryParam;
import com.allinabc.cloud.attach.pojo.dto.AttachmentRequest;
import com.allinabc.cloud.attach.pojo.dto.BizAttachmentListParam;
import com.allinabc.cloud.attach.pojo.po.AttachmentType;
import com.allinabc.cloud.attach.pojo.vo.AttachResponse;
import com.allinabc.cloud.attach.pojo.vo.AttachmentInfoResponse;
import com.allinabc.cloud.attach.service.AttachmentService;
import com.allinabc.cloud.common.core.utils.bean.BeanUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 附件表 服务实现类
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-11-16
 */
@Service
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    @Resource
    private AttachmentTypeMapper attachmentTypeMapper;

    @Resource
    private AttachmentMapper attachmentMapper;

    /**
     * 公共上传服务
     */
    @Autowired
    private CommonAttachmentServiceImpl commonAttachmentService;

    /**
     * 交互上传服务
     */
    @Autowired
    private InteractiveAttachmentServiceImpl interactiveAttachmentService;



    @Override
    public Result<AttachResponse> attachmentUpload(MultipartFile file, String bizId, String bizType, HttpServletRequest request) {
        //描述：先根据bizType查询具体的配置信息
        QueryWrapper<AttachmentType> queryWrapper = new QueryWrapper<>();
        AttachmentType attachmentTypeEntity = new AttachmentType();
        attachmentTypeEntity.setBizType(bizType);
        queryWrapper.setEntity(attachmentTypeEntity);
        AttachmentType attachmentType = attachmentTypeMapper.selectOne(queryWrapper);

        if(attachmentType==null){
            return Result.failed("未找到对应的文件配置信息，请查询是否已经配置！");
        }

        AttachmentAdapterService attachmentAdapterService;

        //描述：根据是否有业务交互来分类处理
        if(!StringUtils.isEmpty(attachmentType.getCallbackBefore())||!StringUtils.isEmpty(attachmentType.getCallbackAfter())){
            //有业务交互
            attachmentAdapterService = interactiveAttachmentService;
        }else{
            //通用业务上传
            attachmentAdapterService = commonAttachmentService;
        }
        return attachmentAdapterService.uploadAttachment(file,bizType,bizId,attachmentType,request);
    }



    @Override
    public void attachmentDownload(String attachmentId, HttpServletRequest request, HttpServletResponse response) {

        AttachmentInfoDTO attachmentInfoDTO = attachmentMapper.selectAttachmentInfoById(attachmentId);

        AttachmentAdapterService attachmentAdapterService;

        //描述：根据是否有业务交互来分类处理
        if(!StringUtils.isEmpty(attachmentInfoDTO.getCallbackBefore())||!StringUtils.isEmpty(attachmentInfoDTO.getCallbackAfter())){
            //有业务交互
            attachmentAdapterService = commonAttachmentService;
        }else{
            //通用业务
            attachmentAdapterService = commonAttachmentService;
        }
        attachmentAdapterService.downloadAttachment(attachmentInfoDTO,request,response);
    }

    @Override
    public Result<List<AttachmentInfoResponse>> findAttachmentList(AttachmentQueryParam attachmentQueryParam) {
        List<AttachmentInfoResponse> list = attachmentMapper.selectAttachmentInfoByBizIdAndBizType(attachmentQueryParam);
        list.forEach(res->{
            res.setFileName(res.getFileName()+"."+res.getFileType());
        });
        return Result.success(list);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> removeAttachment(AttachmentRequest attachmentRequest) {
        //描述：删除附件(逻辑删除)
        int result = attachmentMapper.deleteAttachmentById(attachmentRequest.getAttachmentId());
        if(result!=1){
            log.error("文件删除失败，失败信息id="+ attachmentRequest.getAttachmentId());
            throw new RuntimeException("文件删除失败");
        }
        log.info("删除附件成功");
        return Result.success();
    }

    @Override
    public Result<AttachmentInfoResponse> findSingleAttachment(AttachmentQueryParam attachmentQueryParam) {
        AttachmentInfoDTO attachmentInfoDTO = attachmentMapper.selectAttachmentInfoById(attachmentQueryParam.getAttachmentId());
        if(attachmentInfoDTO!=null) {
            AttachmentInfoResponse attachmentInfoResponse = new AttachmentInfoResponse();
            BeanUtils.copyBeanProp(attachmentInfoResponse, attachmentInfoDTO);
            attachmentInfoResponse.setFileName(attachmentInfoResponse.getFileName()+"."+attachmentInfoResponse.getFileType());
            return Result.success(attachmentInfoResponse);
        }
        return Result.success();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBizIds(BizAttachmentListParam bizAttachmentListParam) {
       attachmentMapper.updateBizIds(bizAttachmentListParam.getAttachmentParams());
    }
}
