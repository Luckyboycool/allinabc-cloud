package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.OperLogMapper;
import com.allinabc.cloud.admin.pojo.po.OperLog;
import com.allinabc.cloud.admin.service.OperLogService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2020-12-16
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class OperLogServiceImpl extends MybatisCommonServiceImpl<OperLogMapper, OperLog> implements OperLogService {

    @Resource
    private OperLogMapper operLogMapper;

    @Override
    public void cleanOperLog() {

    }

    @Override
    protected MybatisCommonBaseMapper<OperLog> getRepository() {
        return operLogMapper;
    }

}
