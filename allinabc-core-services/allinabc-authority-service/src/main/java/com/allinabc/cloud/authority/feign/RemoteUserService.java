//package com.allinabc.cloud.authority.feign;
//
//import com.allinabc.cloud.admin.api.service.impl.feign.client.UserServiceApi;
//import com.allinabc.cloud.authority.factory.RemoteUserFallbackFactory;
//import com.allinabc.cloud.common.api.interceptor.FeignInterceptor;
//import com.allinabc.cloud.common.core.utils.constant.ServiceNameConstants;
//import org.springframework.cloud.openfeign.FeignClient;
//
///**
// * @Description
// * @Author wangtaifeng
// * @Date 2020/12/29 14:48
// **/
//@FeignClient(name = ServiceNameConstants.ADMIN_SERVICE,fallbackFactory = RemoteUserFallbackFactory.class,configuration = FeignInterceptor.class)
//public interface RemoteUserService extends UserServiceApi {
//}
