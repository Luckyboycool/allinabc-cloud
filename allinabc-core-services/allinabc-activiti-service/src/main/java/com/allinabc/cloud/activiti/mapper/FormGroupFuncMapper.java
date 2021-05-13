package com.allinabc.cloud.activiti.mapper;

import com.allinabc.cloud.activiti.pojo.po.FormGroupFunc;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/24 14:18
 **/
public interface FormGroupFuncMapper extends MybatisCommonBaseMapper<FormGroupFunc> {

    /**
     * 分局formTYpe查询功能项的群组配置
     * @param formType
     * @return
     */
    List<FormGroupFunc> selectGroupNameByFormType(@Param("formType") String formType);

    /**
     * 根据fabcode和表单类型查询group
     * @param formType
     * @param isFab
     * @return
     */
    List<FormGroupFunc> selectGroupFuncByFormTypeAndFabCode(@Param("formType")String formType, @Param("isFab")String isFab);
}
