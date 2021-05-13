package com.allinabc.cloud.attach.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.attach.mapper.AttachmentTypeMapper;
import com.allinabc.cloud.attach.pojo.dto.AttachmentTypeQueryParam;
import com.allinabc.cloud.attach.pojo.dto.AttachmentTypeRequest;
import com.allinabc.cloud.attach.pojo.po.AttachmentType;
import com.allinabc.cloud.attach.pojo.vo.QueryTableResult;
import com.allinabc.cloud.attach.service.AttachmentTypeService;
import com.allinabc.cloud.common.core.utils.bean.BeanUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 附件类型表 服务实现类
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-11-16
 */
@Service
@Slf4j
public class AttachmentTypeServiceImpl  implements AttachmentTypeService {

    @Resource
    private AttachmentTypeMapper attachmentTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> addAttachmentType(AttachmentTypeRequest attachmentTypeRequest) {
        log.info("新增单个附件类型接口请求参数="+ JSON.toJSONString(attachmentTypeRequest));
        //描述：首先判断bizType是否已经配置
        ConcurrentHashMap<String, Object> hs = new ConcurrentHashMap<>();
        hs.put("biz_type",attachmentTypeRequest.getBizType());
        List<AttachmentType> attachmentTypeList = attachmentTypeMapper.selectByMap(hs);
        if(attachmentTypeList!=null&&attachmentTypeList.size()>0){
            return Result.failed("配置信息已存在，请重新配置");
        }
        AttachmentType attachmentType = new AttachmentType();
        BeanUtils.copyBeanProp(attachmentType,attachmentTypeRequest);
        attachmentType.setId(new Snowflake(0,0).nextIdStr());
        int result = attachmentTypeMapper.insert(attachmentType);
        if(result!=1){
            log.error("新增单个附件类型失败，失败数据="+ JSON.toJSONString(attachmentType));
            throw new RuntimeException("新增单个附件类型失败");
        }
        log.info("新增单个附件类型成功");
        return Result.success(attachmentType.getId());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> removeAttachmentType(AttachmentTypeRequest attachmentTypeRequest) {
        //描述：物理删除单个附件配置
        int result = attachmentTypeMapper.deleteById(attachmentTypeRequest.getId());
        if(result!=1){
            log.error("删除单个附件类型失败，失败数据：id="+attachmentTypeRequest.getId());
            throw new RuntimeException("删除单个附件类型失败");
        }
        return Result.success();
    }


    @Override
    public Result<QueryTableResult<AttachmentType>> findAttachmentType(AttachmentTypeQueryParam attachmentTypeQueryParam, Long pageNum, Long pageSize) {
        //描述：分页
        Page<AttachmentType> page = new Page(pageNum, pageSize);
        QueryWrapper<AttachmentType> queryWrapper = new QueryWrapper<>();
        AttachmentType attachmentType = new AttachmentType();
        BeanUtils.copyBeanProp(attachmentType,attachmentTypeQueryParam);
        queryWrapper.setEntity(attachmentType);
        Page<AttachmentType> attachmentTypePage = attachmentTypeMapper.selectPage(page, queryWrapper);
        QueryTableResult<AttachmentType> result = QueryTableResult.success(attachmentTypePage.getTotal(), attachmentTypePage.getRecords());
        return Result.success(result);
    }
}
