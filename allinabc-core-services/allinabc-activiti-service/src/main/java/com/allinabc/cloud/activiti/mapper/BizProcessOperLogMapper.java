package com.allinabc.cloud.activiti.mapper;

import com.allinabc.cloud.activiti.pojo.params.OperLogParam;
import com.allinabc.cloud.activiti.pojo.po.BizProcessOperLog;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/22 17:55
 **/
public interface BizProcessOperLogMapper extends MybatisCommonBaseMapper<BizProcessOperLog> {

    /**
     * 分页条件查询操作日志
     * @param page
     * @param operLogParam
     * @return
     */
    Page<BizProcessOperLog> findOperList(IPage page, @Param("operLogParam") OperLogParam operLogParam);
}
