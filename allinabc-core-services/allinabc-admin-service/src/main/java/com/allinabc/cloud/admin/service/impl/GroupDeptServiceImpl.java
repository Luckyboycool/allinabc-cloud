package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.GroupDeptMapper;
import com.allinabc.cloud.admin.pojo.po.GroupDept;
import com.allinabc.cloud.admin.service.GroupDeptService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Simon.Xue
 * @date 2021/3/2 3:45 下午
 **/
@Service
public class GroupDeptServiceImpl extends MybatisCommonServiceImpl<GroupDeptMapper, GroupDept> implements GroupDeptService {

    @Resource
    private GroupDeptMapper groupDeptMapper;

    @Override
    protected MybatisCommonBaseMapper<GroupDept> getRepository() {
        return groupDeptMapper;
    }
}
