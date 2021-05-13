package com.allinabc.cloud.activiti.mapper;

import com.allinabc.cloud.activiti.pojo.po.GtaRequestNo;
import com.allinabc.cloud.activiti.pojo.po.ProcessId;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

public interface GtaRequestNoMapper{

    int insert(GtaRequestNo record);

    String selectByKey(@Param("key") String key);

    int updateByKey(@Param("key") String key,@Param("value") String value);

}