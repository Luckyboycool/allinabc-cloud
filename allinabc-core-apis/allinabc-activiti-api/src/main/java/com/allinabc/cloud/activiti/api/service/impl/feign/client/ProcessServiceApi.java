package com.allinabc.cloud.activiti.api.service.impl.feign.client;

import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessStartParam;
import com.allinabc.cloud.activiti.pojo.vo.ProcessIdModel;
import com.allinabc.cloud.activiti.pojo.vo.TaskModel;
import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/25 18:24
 **/
@FeignClient(value = ServiceNameConstants.ACTIVITI_SERVICE)
public interface ProcessServiceApi {

    @PostMapping("/process/ins/start")
    Result<TaskModel> startProcess(@RequestBody ProcessStartParam processStartParam);

    @PostMapping("/process/ins/execute")
    Result<List<String>> executeProcess(@RequestBody ProcessExecuteParam processExecuteParam);

    @GetMapping("/process/id/{formtype}")
    Result<ProcessIdModel> findProcessIdByFormType(@PathVariable("formtype") String formType);

    @GetMapping("/process/ins/task/node")
    Result<TaskModel> findBizNodeInfoByBizId(@RequestParam("basicInfoId") String basicInfoId);

}
