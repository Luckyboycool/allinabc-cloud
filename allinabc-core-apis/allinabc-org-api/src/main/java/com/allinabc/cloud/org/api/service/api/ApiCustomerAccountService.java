package com.allinabc.cloud.org.api.service.api;

import com.allinabc.cloud.org.pojo.po.CustomerAccount;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountBasicVO;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/4 10:06 上午
 **/
public interface ApiCustomerAccountService {
    /**
     * 查询客户账户信息
     * @param username
     * @return
     */
    CustomerAccount getByUsername(String username);

    /**
     * 查询指定ID的客户子账号和主账号列表
     * @param id
     * @return
     */
    List<CustomerAccountBasicVO> findChildAndParentById(String id);
}
