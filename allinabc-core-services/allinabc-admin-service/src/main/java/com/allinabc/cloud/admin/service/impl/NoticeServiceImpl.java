package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.NoticeMapper;
import com.allinabc.cloud.admin.pojo.po.Notice;
import com.allinabc.cloud.admin.service.NoticeService;
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
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-16
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class NoticeServiceImpl extends MybatisCommonServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    protected MybatisCommonBaseMapper<Notice> getRepository() {
        return noticeMapper;
    }

}
