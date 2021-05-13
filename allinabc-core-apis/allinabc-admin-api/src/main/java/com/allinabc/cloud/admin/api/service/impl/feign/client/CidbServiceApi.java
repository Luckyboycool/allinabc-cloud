package com.allinabc.cloud.admin.api.service.impl.feign.client;

import com.allinabc.cloud.admin.pojo.vo.CidbBasicSimpleVO;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/23 10:44 上午
 **/
@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE)
public interface CidbServiceApi {

    /**
     * 查询部分客户列表
     * @param custCodes
     * @return
     */
    @RequestMapping(value = "/cidb/find_by_cust_codes", method = RequestMethod.GET)
    Result<List<CidbBasicSimpleVO>> findListByCustCodes(@RequestParam("custCodes") List<String> custCodes);
}
