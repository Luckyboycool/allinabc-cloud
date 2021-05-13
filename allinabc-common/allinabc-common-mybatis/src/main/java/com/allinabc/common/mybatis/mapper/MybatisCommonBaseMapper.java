package com.allinabc.common.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/18 11:25
 **/
public interface MybatisCommonBaseMapper<T> extends BaseMapper<T> {

    @Select("select count(id) as num from ${tableName} ${ew.customSqlSegment}")
    int countNum(@Param(Constants.WRAPPER) Wrapper wrapper, @Param("tableName") String tableName);
}
