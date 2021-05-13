package com.allinabc.cloud.starter.form.service;

import com.allinabc.cloud.starter.form.domain.BasicForm;
import com.allinabc.cloud.starter.form.resp.BasicFormVO;
import com.allinabc.common.mybatis.service.MybatisCommonService;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/24 10:16
 **/
public interface BasicFormService extends MybatisCommonService<BasicForm> {

    /**
     * 通过requestNo查询非草稿状态的表单（如果存在，不允许提交)
     * @param requestNo
     * @return
     */
    List<BasicForm> selectBasicFormByRequestNo(String requestNo);


    /**
     * 修改上个版本的头部信息 的状态  通过basicId  修改状态
     * @param basicId
     * @param status
     * @return
     */
    void updateBasicFormStatusByBasicId(String basicId, String status,String updatedBy);

    /**
     * 保存头部信息
     * @param basicForm
     * @return
     */
    void saveBasicForm(BasicForm basicForm);

    /**
     * 更新BasicForm信息
     * @param basicForm
     */
    void updateBasicFormById(BasicForm basicForm);

    /**
     * 更新表单为指定状态
     * @param basicInfoId 主键
     * @param status 状态
     * @param updatedBy 更新人
     */
    void updateStatus(String basicInfoId, String status, String updatedBy);

    /**
     * 设置表单基本信息
     * @param form
     * @param currentUser
     */
    void setBasicInfo(BasicForm form, String currentUser);

    /**
     * 通过requestNo查询最新的版本号（最大的版本号）
     */
    BasicForm selectNewVersionBasicFormByRequestNo(String requestNo);
    /**
     * 通过FormType查询最新版的表单（完成和被改版)
     * @param formType
     * @return
     */
    List<BasicForm> selectBasicFormByFormType(String formType);

    /**
     * 更改草稿和表单状态状态
     * @param basicInfoId
     * @param formStatus
     * @param draftStatus
     * @param updatedBy
     */
    void updateDraftStatus(String basicInfoId, String formStatus,String draftStatus, String updatedBy);

    /**
     * 通过id查询BasicForm的数据，自定义数据
     * @param id
     * @return
     */
    BasicFormVO selectBasicFormVOById(String id);

    /**
     * 通过userId和formType查询能看到的所有BasicForm
     * @param userId
     * @param formType
     * @return
     */
    List<String> selectBasicFormIdByFormTypeAndUserId(String accountType,List<String> formType,String userId);

    /**
     * 查询BasicForm
     * @param mainId mainId
     * @param formType 表单类型
     * @return
     */
    BasicForm selectByMainIdAndFormType(String mainId, String formType);
}
