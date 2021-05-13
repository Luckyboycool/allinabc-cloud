package com.allinabc.cloud.activiti.api.service.impl.feign.impl;

import com.allinabc.cloud.activiti.api.service.api.ApiProcessService;
import com.allinabc.cloud.activiti.api.service.impl.feign.client.ProcessServiceApi;
import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessStartParam;
import com.allinabc.cloud.activiti.pojo.vo.ProcessIdModel;
import com.allinabc.cloud.activiti.pojo.vo.TaskModel;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/25 18:24
 **/
@Service
public class ApiFeignProcessServiceImpl implements ApiProcessService {

    @Autowired
    private ProcessServiceApi processServiceApi;

    @Override
    public TaskModel startProcess(ProcessStartParam processStartParam) {
        Result<TaskModel> processInstanceResult = processServiceApi.startProcess(processStartParam);
        if(processInstanceResult.getCode()!=20000){
            throw new BusinessException("调用服务失败");
        }
        return null!=processInstanceResult&& null!=processInstanceResult.getData() ? processInstanceResult.getData():null;
    }

    @Override
    public List<String> executeProcess(ProcessExecuteParam processExecuteParam) {
        Result<List<String>> result = processServiceApi.executeProcess(processExecuteParam);
        if(result.getCode()!=20000){
            throw new BusinessException("调用服务失败");
        }
        return null!=result && null!=result.getData() ? result.getData() : null;
    }

    @Override
    public ProcessIdModel findProcessIdByFormType(String formType) {
        Result<ProcessIdModel> result = processServiceApi.findProcessIdByFormType(formType);
        if(result.getCode()!=20000){
            throw new BusinessException("调用服务失败");
        }
        return null!=result && null!=result.getData() ? result.getData() : null;
    }

    @Override
    public TaskModel getBizNodeInfoByBizId(String basicInfoId) {
        Result<TaskModel> result = processServiceApi.findBizNodeInfoByBizId(basicInfoId);
        if(result.getCode()!=20000){
            throw new BusinessException("调用服务失败");
        }
        return null!=result&& null!=result.getData() ? result.getData():null;
    }
}
