package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.LoginInfoMapper;
import com.allinabc.cloud.admin.pojo.dto.LoginInfoSearchDTO;
import com.allinabc.cloud.admin.pojo.po.LoginInfo;
import com.allinabc.cloud.admin.service.LoginInfoService;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.web.pojo.param.QueryParam;
import com.allinabc.cloud.common.web.pojo.param.SqlOrder;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-17
 */
@Service
public class LoginInfoServiceImpl extends MybatisCommonServiceImpl<LoginInfoMapper, LoginInfo> implements LoginInfoService {

    @Resource
    private LoginInfoMapper loginInfoMapper;

    /**
     * 清空系统登录日志
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanLoginInfo() {
        loginInfoMapper.cleanLoginInfo();
    }

    @Override
    public boolean beforeFindPage(QueryParam param) {
        List<SqlOrder> mOrders = param.getMbOrder();
        if(mOrders==null) {
            List<SqlOrder> sqlOrders = new ArrayList<>();
            SqlOrder sqlOrder = new SqlOrder();
            sqlOrder.setSortField("loginTime");
            sqlOrder.setSortOrder("DESC");
            sqlOrders.add(sqlOrder);
            param.setMbOrder(sqlOrders);
        }
        return true;
    }

    @Override
    public IPage<LoginInfo> pageList(LoginInfoSearchDTO searchDTO) {
        Page<LoginInfo> page = new Page<>();
        page.setCurrent(searchDTO.getPageNum());
        page.setSize(searchDTO.getPageSize());

        IPage<LoginInfo> iPage = loginInfoMapper.pageList(page, searchDTO.getUsername());
        return iPage;
    }


    @Override
    public Result<LoginInfo> getLastByUsername() {
        User currentUser = getCurrentUser();
        String accountType = currentUser.getAccountType();
        String id = currentUser.getId();
        LoginInfo loginInfo = loginInfoMapper.getLastLoginInfo(id, accountType);
        return Result.success(loginInfo);
    }

    @Override
    protected MybatisCommonBaseMapper<LoginInfo> getRepository() {
        return loginInfoMapper;
    }

}
