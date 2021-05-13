package com.allinabc.cloud.activiti.mapper;

import com.allinabc.cloud.activiti.pojo.po.FormPermission;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/23 19:43
 **/
public interface FormPermissionMapper extends MybatisCommonBaseMapper<FormPermission> {

    List<FormPermission> selectReadPermisonByBusinessId(@Param("businessId") String businessId, @Param("userId") String userId, @Param("userType") String userType,@Param("formType")String formType);

    int deleteFabGroupPermission(@Param("formType") String formType,@Param("basicFormId") String basicFormId, @Param("bizUserType")String bizUserType, @Param("ls")List<String> groupName);
}
