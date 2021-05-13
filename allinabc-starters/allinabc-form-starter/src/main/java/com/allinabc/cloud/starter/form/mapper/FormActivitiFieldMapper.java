package com.allinabc.cloud.starter.form.mapper;

import com.allinabc.cloud.starter.form.domain.FormActivitiField;
import com.allinabc.cloud.starter.form.resp.FormFieldResp;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/1 2:00 下午
 **/
public interface FormActivitiFieldMapper extends MybatisCommonBaseMapper<FormActivitiField> {
    /**
     * 查询列表
     * @param nodeKey
     * @return
     */
    /*
    @Select("SELECT ad.NODE_KEY, af.RW, af.FIELD_ID AS fieldId, fd.TYPE, fd.FIELD_NAME, fd.TABLE_ID\n" +
            "FROM ADMINDA1.ADM_FORM_ACTIVITI_DEF ad\n" +
            "         LEFT JOIN ADMINDA1.ADM_FORM_ACTIVITI_FIELD af ON ad.ID = af.ACTIVITI_ID\n" +
            "         LEFT JOIN ADMINDA1.ADM_FORM_FIELD_DEF fd ON af.FIELD_ID = fd.ID\n" +
            "WHERE ad.NODE_KEY = '${nodeKey}' and ad.FORM_TYPE = '${formType}'")
    List<FormFieldResp> listByNodeKey(@Param("nodeKey") String nodeKey,
                                      @Param("formType") String formType);
    */

    /**
     * 查询列表
     * @param nodeKey
     * @return
     */
    @Select("SELECT ad.NODE_KEY, af.RW, af.FIELD_ID AS fieldId, fd.TYPE, fd.FIELD_NAME, fd.TABLE_ID\n" +
            "FROM ADMINDA1.ADM_FORM_ACTIVITI_FIELD af\n" +
            "         LEFT JOIN ADMINDA1.ADM_FORM_ACTIVITI_DEF ad ON ad.ID = af.ACTIVITI_ID\n" +
            "         LEFT JOIN ADMINDA1.ADM_FORM_FIELD_DEF fd ON af.FIELD_ID = fd.ID\n" +
            "WHERE ad.NODE_KEY = '${nodeKey}' and ad.FORM_TYPE = '${formType}'")
    List<FormFieldResp> listByNodeKey(@Param("nodeKey") String nodeKey,
                                      @Param("formType") String formType);
}
