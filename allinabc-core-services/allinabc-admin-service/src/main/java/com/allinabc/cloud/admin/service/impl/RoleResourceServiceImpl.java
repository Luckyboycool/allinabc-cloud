package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.RoleResourceMapper;
import com.allinabc.cloud.admin.pojo.po.RoleResource;
import com.allinabc.cloud.admin.service.RoleResourceService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Simon.Xue
 * @date 2021/3/2 6:15 下午
 **/
@Service
public class RoleResourceServiceImpl extends MybatisCommonServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {
    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Override
    protected MybatisCommonBaseMapper<RoleResource> getRepository() {
        return roleResourceMapper;
    }
}
