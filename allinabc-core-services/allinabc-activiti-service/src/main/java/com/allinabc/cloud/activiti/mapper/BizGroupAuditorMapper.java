package com.allinabc.cloud.activiti.mapper;

import com.allinabc.cloud.activiti.pojo.po.BizGroupAuditor;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/18 15:21
 **/
public interface BizGroupAuditorMapper extends MybatisCommonBaseMapper<BizGroupAuditor> {
    /**
     * 根据三个值查询group_name
     * @param dictValue
     * @param formType
     * @param nodeKey
     * @return
     */
    List<String> selectGroupNameByVariable(@Param("dictValue") String dictValue, @Param("formType")String formType, @Param("nodeKey")String nodeKey);
}
