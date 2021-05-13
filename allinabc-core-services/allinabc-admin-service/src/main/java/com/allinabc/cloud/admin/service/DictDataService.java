package com.allinabc.cloud.admin.service;

import com.allinabc.cloud.admin.pojo.dto.DictDateParam;
import com.allinabc.cloud.admin.pojo.po.DictData;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.service.MybatisCommonService;

import java.util.List;

/**
 * <p>
 *  字典数据业务接口 （迁移）
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-15
 */
public interface DictDataService extends MybatisCommonService<DictData> {

    /**
     * 根据类型和字典编码获取字典数据
     * @param dictType
     * @param dictCode
     * @return
     */
    DictData selectByValue(String dictType, String dictCode);

    /**
     * 根据字典类型和字典编码获取字典数据的值
     * @param dictType
     * @param dictCode
     * @return
     */
    String selectDictLabel(String dictType, String dictCode);

    /**
     * 根据字典类型获取字典数据列表
     * @param dictType
     * @return
     */
    List<DictData> selectByDictType(String dictType);

    /**
     * 带条件查询字典列表
     * @param dictData
     * @return
     */
    List<DictData> selectList(DictData dictData);

    /**
     * 根据dictType和appCode获取DictData列表
     *
     * @param dictType
     * @param appCode
     * @return
     */
    List<DictData> getDictValueByTypeAndCode(String dictType, String appCode);

    /**
     * 根据dictTypes和appCode获取DictData列表
     * @param dictDateParam
     * @return
     */
    Result getDictValueByFilter(DictDateParam dictDateParam);
}
