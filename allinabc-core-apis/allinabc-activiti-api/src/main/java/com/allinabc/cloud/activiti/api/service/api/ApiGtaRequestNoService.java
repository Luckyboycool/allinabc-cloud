package com.allinabc.cloud.activiti.api.service.api;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author gaoxiang
 * @Date 2021/3/5 16:54
 **/
public interface ApiGtaRequestNoService {
    String getRequestNo(String pre,Integer length );

}
