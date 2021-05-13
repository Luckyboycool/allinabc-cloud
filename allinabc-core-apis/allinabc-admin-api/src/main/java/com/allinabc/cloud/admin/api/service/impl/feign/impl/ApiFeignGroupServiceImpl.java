package com.allinabc.cloud.admin.api.service.impl.feign.impl;

import cn.hutool.core.lang.Assert;
import com.allinabc.cloud.admin.api.service.api.ApiGroupService;
import com.allinabc.cloud.admin.api.service.impl.feign.client.GroupServiceApi;
import com.allinabc.cloud.common.web.enums.ApiResultCode;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.vo.EmployeeBasicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/29 11:00 上午
 **/
@Service
public class ApiFeignGroupServiceImpl implements ApiGroupService {
    @Autowired
    private GroupServiceApi groupServiceApi;

    @Override
    public List<EmployeeBasicVO> findEmployeeByGroupName(String groupName) {
        Result<List<EmployeeBasicVO>> result = groupServiceApi.findEmployeeByGroupName(groupName);
        Assert.isTrue(result.getCode() == ApiResultCode.SUCCESS.getCode(), "群组服务请求超时");
        return  null!=result && null!=result.getData() ? result.getData() : null;

    }
}
