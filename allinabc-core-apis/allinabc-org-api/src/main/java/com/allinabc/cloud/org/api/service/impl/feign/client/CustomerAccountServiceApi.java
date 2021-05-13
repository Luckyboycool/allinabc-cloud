package com.allinabc.cloud.org.api.service.impl.feign.client;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.po.CustomerAccount;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountBasicVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/4 10:08 上午
 **/
@FeignClient(value = "allinabc-org")
public interface CustomerAccountServiceApi {
    /**
     * 查询客户账号信息
     * @param username
     * @return
     */
    @RequestMapping(value = "/account/get_by_username", method = RequestMethod.GET)
    Result<CustomerAccount> getByUsername(@RequestParam("username") String username);


    /**
     * 查询指定ID的客户子账号和主账号列表
     * @param id
     * @return
     */
    @RequestMapping(value = "/account/find_child_parent", method = RequestMethod.GET)
    Result<List<CustomerAccountBasicVO>> findChildAndParentById(@RequestParam("id") String id);
}
