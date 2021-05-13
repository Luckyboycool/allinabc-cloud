package com.allinabc.cloud.activiti.service.impl;

import com.allinabc.cloud.activiti.mapper.BizProcessOperLogMapper;
import com.allinabc.cloud.activiti.pojo.consts.OperLogConstant;
import com.allinabc.cloud.activiti.pojo.params.OperLogParam;
import com.allinabc.cloud.activiti.pojo.po.BizProcessOperLog;
import com.allinabc.cloud.activiti.service.AdminService;
import com.allinabc.cloud.activiti.service.ProcessOperService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/22 17:49
 **/
@Service
@Slf4j
public class ProcessOperServiceImpl implements ProcessOperService {

    @Resource
    private BizProcessOperLogMapper bizProcessOperLogMapper;

    @Autowired
    private AdminService adminService;

    @Override
    public Page<BizProcessOperLog> findOperList(long pageNum, long pageSize, OperLogParam operLogParam) {
        IPage page = new Page(pageNum, pageSize);
        return bizProcessOperLogMapper.findOperList(page,operLogParam);
    }

    @Override
    public void addOperUpdateLog(OperLogParam operLogParam) {
        adminService.saveBizProcessOperLog(OperLogConstant.UPDATE,OperLogConstant.UPDATE_REMARK,operLogParam.getBasicInfoId(),operLogParam.getProcDefKey(),operLogParam.getProcInstId(),operLogParam.getTaskDefKey(),operLogParam.getTaskId(),null,null,null);
    }
}
