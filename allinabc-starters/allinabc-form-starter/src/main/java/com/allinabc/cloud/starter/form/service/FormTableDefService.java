package com.allinabc.cloud.starter.form.service;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.form.domain.FormTableDef;
import com.allinabc.cloud.starter.form.resp.FormFieldResp;
import com.allinabc.common.mybatis.service.MybatisCommonService;

import java.util.Map;

/**
 * @author Simon.Xue
 * @date 2021/3/1 1:44 下午
 **/
public interface FormTableDefService extends MybatisCommonService<FormTableDef> {
    /**
     * 查询表单下所有字段的定义
     * @param formType
     * @deprecated 此功能暂时不需要
     * @return
     */
    @Deprecated
    Result<Map<String, Map<String, FormFieldResp>>> listByFormTypeV1(String formType);

    /**
     * 查询表单下所有字段 是否可写
     * @param formType
     * @deprecated 已废弃
     * @return
     */
    @Deprecated
    Result<Map<String, Map<String, String>>> listByFormTypeV2(String formType);

    /**
     * 查询表单是否可写列表
     * @param formType
     * @param nodeKey
     * @return
     */
    Result<Map<String, Map<String, Map<String, String >>>> listByFormType(String formType, String nodeKey);

}
