package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.GroupUserMapper;
import com.allinabc.cloud.admin.pojo.po.GroupUser;
import com.allinabc.cloud.admin.service.GroupUserService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2020-12-17
 */
@Service
public class GroupUserServiceImpl extends MybatisCommonServiceImpl<GroupUserMapper, GroupUser> implements GroupUserService {

    @Resource
    private GroupUserMapper groupUserMapper;

    @Override
    protected MybatisCommonBaseMapper<GroupUser> getRepository() {
        return groupUserMapper;
    }

}
