package com.allinabc.cloud.starter.form.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.pojo.bo.NameModel;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.starter.form.domain.BasicForm;
import com.allinabc.cloud.starter.form.enums.BasicFormStatus;
import com.allinabc.cloud.starter.form.mapper.BasicFormMapper;
import com.allinabc.cloud.starter.form.resp.BasicFormVO;
import com.allinabc.cloud.starter.form.service.BasicFormService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/24 10:19
 **/
@Service
@Slf4j
public class BasicFormServiceImpl extends MybatisCommonServiceImpl<BasicFormMapper, BasicForm> implements BasicFormService {

    @Resource
    private BasicFormMapper basicFormMapper;

    @Override
    public List<BasicForm> selectBasicFormByRequestNo(String requestNo) {
        log.info("通过requestNo查询BasicForm信息；requestNo="+requestNo);
        QueryWrapper<BasicForm> basicFormQueryWrapper = new QueryWrapper<>();
        BasicForm basicForm = new BasicForm();
        basicForm.setRequestNo(requestNo);
        basicFormQueryWrapper.ne("status", BasicFormStatus.DRAFT.getCode());
        basicFormQueryWrapper.setEntity(basicForm);
        List<BasicForm> basicForms = basicFormMapper.selectList(basicFormQueryWrapper);
        return basicForms;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBasicFormStatusByBasicId(String basicId, String status,String updatedBy) {
        log.info("修改BasicForm的状态，通过basicId="+basicId+";status="+status);
        BasicForm basicForm = new BasicForm();
        basicForm.setId(basicId);
        basicForm.setStatus(status);
        basicForm.setUpdatedBy(updatedBy);
        basicForm.setUpdateTm(new Date());
        int var1 = basicFormMapper.updateById(basicForm);
        if(var1!=1){
            throw new BusinessException("修改BasicForm状态失败");
        }
        log.info("修改BasicForm成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBasicForm(BasicForm basicForm) {
        log.info("保存头部信息参数="+ JSON.toJSONString(basicForm));
        if(basicFormMapper.insert(basicForm)!=1){
            throw new BusinessException("保存头部信息失败");
        }
        log.info("保存头部信息成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBasicFormById(BasicForm basicForm) {
        log.info("更新BasicForm信息的参数="+JSON.toJSONString(basicForm));
        if(basicFormMapper.updateById(basicForm)!=1){
            throw new BusinessException("更新BasicForm信息失败");
        }
        log.info("更新BasicForm信息失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String basicInfoId, String status, String updatedBy) {
        BasicForm form = new BasicForm();
        form.setId(basicInfoId);
        form.setStatus(status);
        form.setUpdateTm(new Date());
        form.setUpdatedBy(updatedBy);
        basicFormMapper.updateById(form);
    }

    @Override
    public void setBasicInfo(BasicForm form, String currentUser) {
        form.setRequester(currentUser);
        form.setDrafter(currentUser);
        form.setRequestTime(new Date());
    }

    @Override
    public BasicForm selectNewVersionBasicFormByRequestNo(String requestNo) {
        QueryWrapper<BasicForm> basicFormQueryWrapper = new QueryWrapper<>();
        BasicForm basicForm = new BasicForm();
        basicForm.setRequestNo(requestNo);
        List<BasicForm> ls = basicFormMapper.selectList(basicFormQueryWrapper);
        BasicForm maxVersion = ls.stream().max(Comparator.comparing(BasicForm::getVersion)).get();
        return maxVersion;
    }

    @Override
    protected MybatisCommonBaseMapper<BasicForm> getRepository() {
        return basicFormMapper;
    }

    @Override
    public List<BasicForm> selectBasicFormByFormType(String formType) {
        log.info("通过formType查询BasicForm信息；formType="+formType);
        QueryWrapper<BasicForm> basicFormQueryWrapper = new QueryWrapper<>();
        BasicForm basicForm = new BasicForm();
        basicForm.setFormType(formType);
        basicFormQueryWrapper.eq("status",BasicFormStatus.COMPLETED.getCode())
                .or()
                .eq("status",BasicFormStatus.REDESIGN.getCode());
        basicFormQueryWrapper.setEntity(basicForm);
        List<BasicForm> basicForms = basicFormMapper.selectList(basicFormQueryWrapper);
        return basicForms;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDraftStatus(String basicInfoId, String formStatus, String draftStatus, String updatedBy) {
        BasicForm form = new BasicForm();
        form.setId(basicInfoId);
        form.setStatus(formStatus);
        form.setUpdateTm(new Date());
        form.setUpdatedBy(updatedBy);
        form.setIsDraft(draftStatus);
        basicFormMapper.updateById(form);
    }

    @Override
    public BasicFormVO selectBasicFormVOById(String id) {
        BasicFormVO basicFormVO = basicFormMapper.selectBasicFormById(id);
        if(basicFormVO!=null){
            if(!StringUtils.isEmpty(basicFormVO.getUserType())&&basicFormVO.getUserType().equals("1")){
                //员工
                NameModel nameModel = basicFormMapper.selectEmployeeUserNameById(basicFormVO.getRequester());
                if(nameModel!=null){
                    basicFormVO.setRequesterName(nameModel.getName());
                    basicFormVO.setJobNumber(nameModel.getJobNumber());
                    basicFormVO.setNameAndJobNumber(nameModel.getNameAndJobNumber());
                }
            }else if(!StringUtils.isEmpty(basicFormVO.getUserType())&&basicFormVO.getUserType().equals("2")){
                //客户
                NameModel nameModel = basicFormMapper.selectAccountNameById(basicFormVO.getRequester());
                if(nameModel!=null){
                    basicFormVO.setRequesterName(nameModel.getName());
                    basicFormVO.setJobNumber(nameModel.getJobNumber());
                    basicFormVO.setNameAndJobNumber(nameModel.getNameAndJobNumber());
                }
            }else if(basicFormVO.getUserType()==null){
                log.error("员工类型为空");
            }
        }
       return basicFormVO;
    }

    @Override
    public List<String> selectBasicFormIdByFormTypeAndUserId(String accountType,List<String> formType, String userId) {
        if(CollUtil.isEmpty(formType)){
            throw new RuntimeException("formType为空");
        }
        if(StringUtils.isEmpty(userId)){
            throw new RuntimeException("userId为空");
        }
        //描述：首先查询出所属组织，所属部门
        String deptmentId = basicFormMapper.selectDeptmentIdByUserId(userId);
        List<String> groupNames = basicFormMapper.selectGroupNameByUserId(userId);

        List<String> groupNamePermissions = null;

        List<String> depetPermissions = null;

        List<String> userIdPermissions = basicFormMapper.selctPermissionByUserIdAndFormTypes(accountType,userId, formType);
        if(groupNames!=null && groupNames.size()>0) {
            groupNamePermissions = basicFormMapper.selectPermissionByGroupNameAndFormTypes(groupNames, formType);
        }
        if(!StringUtils.isEmpty(deptmentId)){
            depetPermissions = basicFormMapper.selectPermissionByDeptIdAndFormTypes(deptmentId, formType);
        }

        List<String> basicInfoIds = Lists.newArrayList();
        if(userIdPermissions!=null && userIdPermissions.size()>0){
            basicInfoIds.addAll(userIdPermissions);
        }
        if(groupNamePermissions!=null && groupNamePermissions.size()>0){
            basicInfoIds.addAll(groupNamePermissions);
        }
        if(depetPermissions!=null && depetPermissions.size()>0){
            basicInfoIds.addAll(depetPermissions);
        }
        basicInfoIds = basicInfoIds.stream().distinct().collect(Collectors.toList());
        log.info("查询出的basicInfoId的集合="+JSON.toJSONString(basicInfoIds));
        return basicInfoIds;
    }

    @Override
    public BasicForm selectByMainIdAndFormType(String mainId, String formType) {
        QueryWrapper<BasicForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MAIN_ID", mainId);
        queryWrapper.eq("FORM_TYPE", formType);
        return basicFormMapper.selectOne(queryWrapper);
    }
}
