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
 * @Author gaoxiang
 * @Date 2021/3/5 18:24
 **/
@FeignClient(value = ServiceNameConstants.ACTIVITI_SERVICE)
public interface GtaRequestNoServiceApi {

    @GetMapping("/requestNo/get")
    Result<String> getRequestNo(@RequestParam("pre") String pre,@RequestParam("length") Integer length );

}
