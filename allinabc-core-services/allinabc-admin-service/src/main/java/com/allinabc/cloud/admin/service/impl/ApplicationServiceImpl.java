package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.ApplicationMapper;
import com.allinabc.cloud.admin.pojo.po.Application;
import com.allinabc.cloud.admin.pojo.vo.AppPathVO;
import com.allinabc.cloud.admin.pojo.vo.ApplicationVO;
import com.allinabc.cloud.admin.service.ApplicationService;
import com.allinabc.cloud.admin.util.BeanDtoVoUtils;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-14
 */
@Service
@Slf4j
public class ApplicationServiceImpl extends MybatisCommonServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Resource
    private ApplicationMapper applicationMapper;

    @Override
    public List<ApplicationVO> findAppCode() {
        List<Application> applicationList = applicationMapper.selectList(null);
        return BeanDtoVoUtils.listVoStream(applicationList, ApplicationVO.class);
    }

    @Override
    public List<AppPathVO> findAppPaht() {
        List<Application> applicationList = applicationMapper.selectList(null);
        return BeanDtoVoUtils.listVoStream(applicationList, AppPathVO.class);
    }

    @Override
    protected MybatisCommonBaseMapper<Application> getRepository() {
        return applicationMapper;
    }

}
