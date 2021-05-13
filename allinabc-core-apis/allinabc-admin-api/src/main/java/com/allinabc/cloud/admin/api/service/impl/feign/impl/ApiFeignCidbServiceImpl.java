package com.allinabc.cloud.admin.api.service.impl.feign.impl;

import com.allinabc.cloud.admin.api.service.api.ApiCidbService;
import com.allinabc.cloud.admin.api.service.impl.feign.client.CidbServiceApi;
import com.allinabc.cloud.admin.pojo.vo.CidbBasicSimpleVO;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.common.web.enums.ApiResultCode;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/23 10:46 上午
 **/
@Service
public class ApiFeignCidbServiceImpl implements ApiCidbService {
    @Autowired
    private CidbServiceApi cidbServiceApi;
    @Override
    public List<CidbBasicSimpleVO> findListByCustCodes(List<String> custCodes) {
        Result<List<CidbBasicSimpleVO>> result = cidbServiceApi.findListByCustCodes(custCodes);
        if (ApiResultCode.SUCCESS.getCode() != result.getCode()) {
            throw new BusinessException("获取客户列表服务异常!");
        }
        return null!=result && null!=result.getData() ? result.getData() : null;
    }
}
