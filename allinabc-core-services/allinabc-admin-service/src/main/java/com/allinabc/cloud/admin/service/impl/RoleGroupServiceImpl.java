package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.RoleGroupMapper;
import com.allinabc.cloud.admin.pojo.po.RoleGroup;
import com.allinabc.cloud.admin.service.RoleGroupService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Simon.Xue
 * @date 2021/3/2 5:38 下午
 **/
@Service
public class RoleGroupServiceImpl extends MybatisCommonServiceImpl<RoleGroupMapper, RoleGroup> implements RoleGroupService {
    @Resource
    private RoleGroupMapper roleGroupMapper;
    @Override
    protected MybatisCommonBaseMapper<RoleGroup> getRepository() {
        return roleGroupMapper;
    }
}
