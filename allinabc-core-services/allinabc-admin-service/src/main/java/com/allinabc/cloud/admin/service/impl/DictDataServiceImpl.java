package com.allinabc.cloud.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.admin.mapper.DictDataMapper;
import com.allinabc.cloud.admin.pojo.dto.DictDateParam;
import com.allinabc.cloud.admin.pojo.po.DictData;
import com.allinabc.cloud.admin.service.DictDataService;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类(代码迁移和mybatis改造)
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-15
 */
@Service
@Slf4j
public class DictDataServiceImpl extends MybatisCommonServiceImpl<DictDataMapper, DictData> implements DictDataService {

    @Resource
    private DictDataMapper dictDataMapper;

    @Override
    public DictData selectByValue(String dictType, String dictCode) {
        DictData dictData = new DictData();
        dictData.setDictType(dictType);
        dictData.setDictValue(dictCode);
        List<DictData> list = this.selectList(dictData);
        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        DictData entity = this.selectByValue(dictType, dictValue);
        return null != entity ? entity.getDictLabel() : "";
    }

    @Override
    public List<DictData> selectByDictType(String dictType) {
        DictData dictData = new DictData();
        dictData.setDictType(dictType);
        return this.selectList(dictData);
    }

    @Override
    public List<DictData> selectList(DictData dictData) {
        QueryWrapper<DictData> sysDictDataQueryWrapper = new QueryWrapper<>();
        sysDictDataQueryWrapper.setEntity(dictData);
        return dictDataMapper.selectList(sysDictDataQueryWrapper);
    }

    /**
     * 根据dictType和appCode获取DictData列表
     *
     * @param dictType
     * @param appCode
     * @return
     */
    @Override
    public List<DictData> getDictValueByTypeAndCode(String dictType, String appCode) {
        return dictDataMapper.getDictValueByTypeAndCode(dictType, appCode);
    }

    @Override
    public Result getDictValueByFilter(DictDateParam dictDateParam) {
        String[] dictTypes = dictDateParam.getDictTypes().split(",");
        if(dictTypes[0].equals("")){
            dictTypes = null;
        }
        List<DictData> ls =dictDataMapper.selectDictValueByFilter(dictDateParam.getDictGroupType(),dictDateParam.getAppCode(),dictTypes);
        if(!StringUtils.isEmpty(dictDateParam.getLeaveType())&&dictDateParam.getLeaveType().equals("2")){
            return Result.success(ls);
        }
        Map<String, List<DictData>> result = ls.stream().distinct().collect(Collectors.groupingBy(DictData::getDictType));
        log.info(JSON.toJSONString(result));
        return Result.success(result);
    }

    @Override
    protected MybatisCommonBaseMapper<DictData> getRepository() {
        return dictDataMapper;
    }

}
