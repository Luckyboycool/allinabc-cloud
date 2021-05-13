//package com.allinabc.cloud.common.auth.feign;
//
//import com.allinabc.cloud.admin.api.service.impl.feign.client.ResourcesServiceApi;
//import com.allinabc.cloud.common.api.interceptor.FeignInterceptor;
//import com.allinabc.cloud.common.auth.factory.RemoteResourcesFallbackFactory;
//import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
//import org.springframework.cloud.openfeign.FeignClient;
//
///**
// * @Description
// * @Author wangtaifeng
// * @Date 2021/1/4 13:35
// **/
//@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE,path = "/resources",fallbackFactory = RemoteResourcesFallbackFactory.class,configuration = FeignInterceptor.class)
//public interface RemoteResourcesService extends ResourcesServiceApi {
//
//}
