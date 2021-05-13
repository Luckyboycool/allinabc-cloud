package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.DictTypeMapper;
import com.allinabc.cloud.admin.pojo.po.DictData;
import com.allinabc.cloud.admin.pojo.po.DictType;
import com.allinabc.cloud.admin.service.DictDataService;
import com.allinabc.cloud.admin.service.DictTypeService;
import com.allinabc.cloud.common.core.domain.UserConstants;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-15
 */
@Service
public class DictTypeServiceImpl extends MybatisCommonServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    @Resource
    private DictTypeMapper dictTypeMapper;
    @Resource
    private DictDataService dictDataService;

    @Override
    public boolean beforeDeleteById(String id) {
        DictType dictType = this.selectById(id);
        Assert.notNull(dictType, "assets type is null with given id : " + id);
        List<DictData> dataList = dictDataService.selectByDictType(dictType.getDictType());
        if(null != dataList && dataList.size() > 0) {
            throw new BusinessException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
        }
        return true;
    }

    @Override
    public String checkUnique(DictType dictType) {
        String id = Strings.isNullOrEmpty(dictType.getId()) ? "" : dictType.getId();
        List<DictType> dictTypes = this.selectList(dictType);
        // 排除自身Id
        dictTypes = null != dictTypes ?
                dictTypes.stream().filter(i -> !StringUtils.equals(i.getId(), id)).collect(Collectors.toList()) : null;

        if(null != dictTypes && dictTypes.size() > 0) {
            return UserConstants.DICT_TYPE_NOT_UNIQUE;
        }
        return UserConstants.DICT_TYPE_UNIQUE;
    }



    @Override
    public boolean beforeInsert(DictType entity) {
        super.beforeInsert(entity);
        int count =super.checkExist("SYS_DICT_TYPE","DICT_NAME",entity.getDictName(),entity.getId());
        Assert.isTrue(!(count>0), "字典名称存在！");
        return true;
    }

    @Override
    public boolean beforeUpdate(String id, DictType entity) {
        super.beforeUpdate(id,entity);
        int count =super.checkExist("SYS_DICT_TYPE","DICT_NAME",entity.getDictName(),entity.getId());
        Assert.isTrue(!(count>0), "字典名称存在！");
        return true;
    }

    @Override
    protected MybatisCommonBaseMapper<DictType> getRepository() {
        return dictTypeMapper;
    }

}
