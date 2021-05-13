package com.allinabc.cloud.admin.api.service.api;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/21 21:53
 **/
public interface ApiResourcesService {

    List<String> listPermissions(String userId, String appCode);

}
