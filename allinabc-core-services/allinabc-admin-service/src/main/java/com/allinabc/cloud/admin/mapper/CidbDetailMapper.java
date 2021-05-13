package com.allinabc.cloud.admin.mapper;


import com.allinabc.cloud.admin.pojo.po.CidbDetail;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CidbDetailMapper extends MybatisCommonBaseMapper<CidbDetail> {
    int deleteByPrimaryKey(String id);

    int insertSelective(CidbDetail record);

    CidbDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CidbDetail record);

    /**
     * 批量插入
     * @param cidbDetailList
     * @return
     */
    int batchInsert(@Param("cidbDetails") List<CidbDetail> cidbDetailList);

}