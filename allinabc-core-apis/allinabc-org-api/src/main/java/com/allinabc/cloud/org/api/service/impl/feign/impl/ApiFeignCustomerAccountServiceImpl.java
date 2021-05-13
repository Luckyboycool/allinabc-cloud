package com.allinabc.cloud.org.api.service.impl.feign.impl;

import cn.hutool.core.lang.Assert;
import com.allinabc.cloud.common.web.enums.ApiResultCode;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.api.service.api.ApiCustomerAccountService;
import com.allinabc.cloud.org.api.service.impl.feign.client.CustomerAccountServiceApi;
import com.allinabc.cloud.org.pojo.po.CustomerAccount;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountBasicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/4 11:21 上午
 **/
@Service
public class ApiFeignCustomerAccountServiceImpl implements ApiCustomerAccountService {
    @Autowired
    private CustomerAccountServiceApi customerAccountServiceApi;

    @Override
    public CustomerAccount getByUsername(String username) {
        Result<CustomerAccount> result = customerAccountServiceApi.getByUsername(username);
        Assert.isTrue(ApiResultCode.SUCCESS.getCode() == result.getCode(), "请求客户账号服务异常");
        return null!=result && null!=result.getData() ? result.getData() : null;
    }

    @Override
    public List<CustomerAccountBasicVO> findChildAndParentById(String id) {
        Result<List<CustomerAccountBasicVO>> result = customerAccountServiceApi.findChildAndParentById(id);
        Assert.isTrue(ApiResultCode.SUCCESS.getCode() == result.getCode(), "请求客户账号服务异常");
        return null!=result && null!=result.getData() ? result.getData() : null;
    }
}
