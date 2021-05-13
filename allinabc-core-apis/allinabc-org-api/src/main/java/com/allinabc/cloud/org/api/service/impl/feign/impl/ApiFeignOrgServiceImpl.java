package com.allinabc.cloud.org.api.service.impl.feign.impl;

import com.allinabc.cloud.admin.pojo.vo.EmployeeDetailVO;
import com.allinabc.cloud.common.web.enums.ApiResultCode;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.api.service.api.ApiOrgService;
import com.allinabc.cloud.org.api.service.impl.feign.client.OrgServiceApi;
import com.allinabc.cloud.org.pojo.vo.DeptBaseVO;
import com.allinabc.cloud.org.pojo.vo.EmployeeDeptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/2 3:16 下午
 **/
@Service
public class ApiFeignOrgServiceImpl implements ApiOrgService {

    @Autowired
    private OrgServiceApi orgServiceApi;

    @Override
    public List<DeptBaseVO> findBaseList(List<String> ids) {
        Result<List<DeptBaseVO>> result = orgServiceApi.findBaseList(ids);
        return  null!=result && null!=result.getData() ? result.getData() : null;
    }

    @Override
    public List<EmployeeDeptVO> getEmployeeByDeptIds(List<String> ids) {
        Result<List<EmployeeDeptVO>> result = orgServiceApi.getEmployeeByDeptIds(ids);
        Assert.isTrue(result.getCode() == ApiResultCode.SUCCESS.getCode(), "调用部门服务异常");
        return null!=result && null!=result.getData() ? result.getData() : null;
    }

    @Override
    public List<EmployeeDetailVO> getDeptEmployees(String code) {
        Result<List<EmployeeDetailVO>> result = orgServiceApi.getDeptEmployees(code);
        Assert.isTrue(result.getCode() == ApiResultCode.SUCCESS.getCode(), "调用部门服务异常");
        return null!=result && null!=result.getData() ? result.getData() : null;
    }
}
