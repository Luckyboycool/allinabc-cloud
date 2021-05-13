package com.allinabc.cloud.org.service.impl;

import com.allinabc.cloud.org.mapper.CustomerMapper;
import com.allinabc.cloud.org.pojo.po.Customer;
import com.allinabc.cloud.org.service.CustomerService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Simon.Xue
 * @date 2/24/21 11:19 PM
 **/
@Service
public class CustomerServiceImpl extends MybatisCommonServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;

    @Override
    protected MybatisCommonBaseMapper<Customer> getRepository() {
        return customerMapper;
    }
}
