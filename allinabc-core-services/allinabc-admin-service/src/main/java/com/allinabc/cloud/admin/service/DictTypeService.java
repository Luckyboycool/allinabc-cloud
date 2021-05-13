package com.allinabc.cloud.admin.service;

import com.allinabc.cloud.admin.pojo.po.DictType;
import com.allinabc.common.mybatis.service.MybatisCommonService;

import java.util.List;

/**
 * <p>
 *  代码迁移
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-15
 */
public interface DictTypeService extends MybatisCommonService<DictType> {

    List<DictType> selectList(DictType entity);

    String checkUnique(DictType entity);
}
