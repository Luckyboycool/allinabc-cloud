package com.allinabc.cloud.activiti.service;

import com.allinabc.cloud.activiti.pojo.params.OperLogParam;
import com.allinabc.cloud.activiti.pojo.po.BizProcessOperLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/22 17:49
 **/
public interface ProcessOperService {

    /**
     * 分页查询操作日志
     * @param pageNum
     * @param pageSize
     * @param operLogParam
     * @return
     */
    Page<BizProcessOperLog> findOperList(long pageNum, long pageSize, OperLogParam operLogParam);


    /**
     * 新增表单修改日志
     * @param operLogParam
     */
    void addOperUpdateLog(OperLogParam operLogParam);
}
