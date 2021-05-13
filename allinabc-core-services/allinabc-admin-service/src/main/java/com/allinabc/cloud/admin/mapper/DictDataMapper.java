package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.po.DictData;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-15
 */
public interface DictDataMapper extends MybatisCommonBaseMapper<DictData> {

    /**
     * 根据dictType和appCode获取DictData列表
     *
     * @param dictType
     * @param appCode
     * @return
     */
    List<DictData> getDictValueByTypeAndCode(@Param("dictType") String dictType, @Param("appCode") String appCode);

    /**
     * 根据dictTypes和appCode获取DictData列表
     * @param appCode
     * @param dictTypes
     * @return
     */
    List<DictData> selectDictValueByFilter(@Param("type")String type,@Param("appCode") String appCode, @Param("ls") String[] dictTypes);
}
