package com.allinabc.cloud.activiti.service.impl;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.mapper.FormGroupFuncMapper;
import com.allinabc.cloud.activiti.mapper.FormPermissionMapper;
import com.allinabc.cloud.activiti.mapper.NodeAuditorMapper;
import com.allinabc.cloud.activiti.pojo.consts.ProcessNodeConstant;
import com.allinabc.cloud.activiti.pojo.dto.TaskParam;
import com.allinabc.cloud.activiti.pojo.po.FormGroupFunc;
import com.allinabc.cloud.activiti.pojo.po.FormPermission;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.org.api.service.api.ApiCustomerAccountService;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountBasicVO;
import com.allinabc.cloud.starter.form.domain.BasicForm;
import com.allinabc.cloud.starter.form.enums.BasicFormStatus;
import com.allinabc.cloud.starter.form.mapper.BasicFormMapper;
import com.allinabc.cloud.starter.web.util.CurrentUserUtil;
import com.google.common.collect.Maps;
import com.gta.cloud.tapeout.api.service.api.ApiTapeoutService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/12 20:07
 **/
@Component
@Slf4j
public class GtaAsyncTask {

    @Resource
    private BasicFormMapper basicFormMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private NodeAuditorMapper nodeAuditorMapper;

    @Autowired
    private ApiTapeoutService apiTapeoutService;

    @Resource
    private FormPermissionMapper formPermissionMapper;

    @Resource
    private FormGroupFuncMapper formGroupFuncMapper;

    @Autowired
    private ApiCustomerAccountService apiCustomerAccountService;


    /**
     * 取消流程
     *
     * @param taskParam
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void cancleTask(TaskParam taskParam) {
        log.info("取消流程参数=" + JSON.toJSONString(taskParam));
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processDefinitionKey(taskParam.getProcessDefKey()).processInstanceId(taskParam.getProcessInstId()).singleResult();
        if (pi == null) {
            throw new RuntimeException("流程已删除");
        }
        //删除流程，并且修改表单状态
        if (StringUtils.isEmpty(taskParam.getReason())) {
            taskParam.setReason("取消");
        }
        runtimeService.deleteProcessInstance(pi.getId(), taskParam.getReason());
        log.info("删除一个流程实例 processInsId=" + pi.getId());
        //描述：修改表单状态
        BasicForm basicForm = new BasicForm();
        basicForm.setId(taskParam.getBasicInfoId());
        basicForm.setStatus(BasicFormStatus.CANCEL.getCode());
        basicForm.setUpdateTm(new Date());
        String currentUserId = CurrentUserUtil.getCurrentUserId();
        basicForm.setUpdatedBy(currentUserId);
        if (basicFormMapper.updateById(basicForm) != 1) {
            log.error("修改表单失败");
            throw new RuntimeException("修改表单状态失败");
        }

        BasicForm basicForm1 = basicFormMapper.selectById(taskParam.getBasicInfoId());

        if (basicForm1 != null) {
            //判断是否是mtr,如果是将字表单的状态改成取消(含改版后生成新的MTF状态)
            if (basicForm1.getFormType().equals("mtr")) {
                HashMap<String, Object> hs = Maps.newHashMap();
                hs.put("MAIN_ID", basicForm1.getId());
                List<BasicForm> basicForms = basicFormMapper.selectByMap(hs);
                basicForms.forEach(i -> {
                    log.info("修改mtr子表单状态");
                    BasicForm basicForm3 = new BasicForm();
                    basicForm3.setId(i.getId());
                    basicForm3.setStatus(BasicFormStatus.CANCEL.getCode());
                    basicForm3.setUpdateTm(new Date());
                    basicForm3.setUpdatedBy(currentUserId);
                    basicFormMapper.updateById(basicForm3);
                });
            }
            String pid = basicForm1.getPid();
            //取消 查询pid不为空  改成完成
            if (!StringUtils.isEmpty(pid)) {
                BasicForm cancleForm = basicFormMapper.selectById(pid);
                if (cancleForm != null) {
                    log.info("取消，将废版改成已完成");
                    BasicForm basicForm2 = new BasicForm();
                    basicForm2.setId(pid);
                    basicForm2.setStatus(BasicFormStatus.COMPLETED.getCode());
                    basicForm2.setUpdateTm(new Date());
                    basicForm2.setUpdatedBy(currentUserId);
                    basicFormMapper.updateById(basicForm2);
                    if (cancleForm.getFormType().equals("mtr")) {
                        HashMap<String, Object> sonMap = Maps.newHashMap();
                        sonMap.put("MAIN_ID", cancleForm.getId());
                        List<BasicForm> mtrSonBasicForm = basicFormMapper.selectByMap(sonMap);
                        mtrSonBasicForm.forEach(i -> {
                            log.info("修改mtr子表单的状态");
                            BasicForm basicForm3 = new BasicForm();
                            basicForm3.setId(i.getId());
                            basicForm3.setStatus(BasicFormStatus.COMPLETED.getCode());
                            basicForm3.setUpdateTm(new Date());
                            basicForm3.setUpdatedBy(currentUserId);
                            basicFormMapper.updateById(basicForm3);

                        });
                    }
                }
            }
        }
    }



    /**
     * 结束流程
     *
     * @param taskParam
     */
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void endTask(TaskParam taskParam) {
        log.info("结束流程参数=" + JSON.toJSONString(taskParam));
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processDefinitionKey(taskParam.getProcessDefKey()).processInstanceId(taskParam.getProcessInstId()).singleResult();
        if (pi == null) {
            throw new RuntimeException("流程已删除");
        }
        //删除流程，并且修改表单状态
        if (StringUtils.isEmpty(taskParam.getReason())) {
            taskParam.setReason("结束流程");
        }
        runtimeService.deleteProcessInstance(pi.getId(), taskParam.getReason());
        log.info("删除一个流程实例 processInsId=" + pi.getId());
        //描述：修改表单状态
//        BasicForm basicForm = new BasicForm();
//        basicForm.setId(taskParam.getBasicInfoId());
//        basicForm.setStatus(BasicFormStatus.CANCEL.getCode());
//        basicForm.setUpdateTm(new Date());
        String currentUserId = CurrentUserUtil.getCurrentUserId();
//        basicForm.setUpdatedBy(currentUserId);
        BasicForm basicForm = getBasicForm(taskParam.getBasicInfoId(),BasicFormStatus.CANCEL.getCode(),null,null,currentUserId,new Date());
        if (basicFormMapper.updateById(basicForm) != 1) {
            log.error("修改表单失败");
            throw new RuntimeException("修改表单状态失败");
        }

        BasicForm basicForm1 = basicFormMapper.selectById(taskParam.getBasicInfoId());

        if (basicForm1 != null) {
            //判断是否是mtr,如果是将字表单的状态改成取消(含改版后生成新的MTF状态)
            if (basicForm1.getFormType().equals("mtr")) {
                HashMap<String, Object> hs = Maps.newHashMap();
                hs.put("MAIN_ID", basicForm1.getId());
                List<BasicForm> basicForms = basicFormMapper.selectByMap(hs);
                basicForms.forEach(i -> {
                    log.info("修改mtr子表单状态");
//                    BasicForm basicForm3 = new BasicForm();
//                    basicForm3.setId(i.getId());
//                    basicForm3.setStatus(BasicFormStatus.CANCEL.getCode());
//                    basicForm3.setUpdateTm(new Date());
//                    basicForm3.setUpdatedBy(currentUserId);
                    BasicForm basicForm3 = getBasicForm(taskParam.getBasicInfoId(),BasicFormStatus.CANCEL.getCode(),null,null,currentUserId,new Date());
                    basicFormMapper.updateById(basicForm3);
                });
            }
            String pid = basicForm1.getPid();
            //取消 查询pid不为空  改成完成
            if (!StringUtils.isEmpty(pid)) {
                BasicForm cancleForm = basicFormMapper.selectById(pid);
                if (cancleForm != null) {
                    log.info("取消，将废版改成已完成");
//                    BasicForm basicForm2 = new BasicForm();
//                    basicForm2.setId(pid);
//                    basicForm2.setStatus(BasicFormStatus.COMPLETED.getCode());
//                    basicForm2.setUpdateTm(new Date());
//                    basicForm2.setUpdatedBy(currentUserId);
                    BasicForm basicForm2 = getBasicForm(taskParam.getBasicInfoId(),BasicFormStatus.COMPLETED.getCode(),null,null,currentUserId,new Date());
                    basicFormMapper.updateById(basicForm2);
                    if (cancleForm.getFormType().equals("mtr")) {
                        HashMap<String, Object> sonMap = Maps.newHashMap();
                        sonMap.put("MAIN_ID", cancleForm.getId());
                        List<BasicForm> mtrSonBasicForm = basicFormMapper.selectByMap(sonMap);
                        mtrSonBasicForm.forEach(i -> {
                            log.info("修改mtr子表单的状态");
//                            BasicForm basicForm3 = new BasicForm();
//                            basicForm3.setId(i.getId());
//                            basicForm3.setStatus(BasicFormStatus.COMPLETED.getCode());
//                            basicForm3.setUpdateTm(new Date());
//                            basicForm3.setUpdatedBy(currentUserId);
                            BasicForm basicForm3 = getBasicForm(taskParam.getBasicInfoId(),BasicFormStatus.COMPLETED.getCode(),null,null,currentUserId,new Date());
                            basicFormMapper.updateById(basicForm3);

                        });
                    }
                }
            }
        }
    }


//    /**
//     * 结束流程
//     *
//     * @param taskParam
//     */
//    @Async
//    @Transactional(rollbackFor = Exception.class)
//    public void endProcess(TaskParam taskParam) {
//        log.info("结束流程参数=" + JSON.toJSONString(taskParam));
//        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processDefinitionKey(taskParam.getProcessDefKey()).processInstanceId(taskParam.getProcessInstId()).singleResult();
//        if (pi == null) {
//            throw new RuntimeException("流程已删除");
//        }
//        //删除流程，并且修改表单状态
//        if (StringUtils.isEmpty(taskParam.getReason())) {
//            taskParam.setReason("结束流程");
//        }
//        runtimeService.deleteProcessInstance(pi.getId(), taskParam.getReason());
//        log.info("删除一个流程实例 processInsId=" + pi.getId());
//        //描述：修改表单状态
//        BasicForm basicForm = new BasicForm();
//        basicForm.setId(taskParam.getBasicInfoId());
//        basicForm.setStatus(BasicFormStatus.CANCEL.getCode());
//        basicForm.setUpdateTm(new Date());
//        String currentUserId = CurrentUserUtil.getCurrentUserId();
//        basicForm.setUpdatedBy(currentUserId);
//        if (basicFormMapper.updateById(basicForm) != 1) {
//            log.error("修改表单失败");
//            throw new RuntimeException("修改表单状态失败");
//        }
//        //取消 查询pid不为空  改成完成
//        BasicForm basicForm1 = basicFormMapper.selectById(taskParam.getBasicInfoId());
//        if(basicForm1!=null){
//            String pid = basicForm1.getPid();
//            if(!StringUtils.isEmpty(pid)){
//                BasicForm cancleForm = basicFormMapper.selectById(pid);
//                if(cancleForm!=null){
//                    log.info("取消，将废版改成已完成");
//                    BasicForm basicForm2 = new BasicForm();
//                    basicForm2.setId(pid);
//                    basicForm2.setStatus(BasicFormStatus.COMPLETED.getCode());
//                    basicForm2.setUpdateTm(new Date());
//                    basicFormMapper.updateById(basicForm2);
//                    if(cancleForm.getFormType().equals("mtr")){
//                        HashMap<String, Object> sonMap = Maps.newHashMap();
//                        sonMap.put("MAIN_ID",cancleForm.getId());
//                        List<BasicForm> mtrSonBasicForm = basicFormMapper.selectByMap(sonMap);
//                        mtrSonBasicForm.forEach(i->{
//                            log.info("修改mtr子表单的状态");
//                            BasicForm basicForm3 = new BasicForm();
//                            basicForm3.setId(i.getId());
//                            basicForm3.setStatus(BasicFormStatus.COMPLETED.getCode());
//                            basicForm3.setUpdateTm(new Date());
//                            basicFormMapper.updateById(basicForm3);
//
//                        });
//                    }
//                }
//            }
//        }
//
//    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    public Future<String> updateBasicFormStatus(String processInstanceId) {
        log.info("异步执行修改表单方法");
        HashMap<String, Object> hs = Maps.newHashMap();
        hs.put("INSTANCE_ID", processInstanceId);
        List<BasicForm> basicForms = basicFormMapper.selectByMap(hs);
        Date now = new Date();
        BasicForm basicForm = new BasicForm();
        basicForm.setUpdateTm(now);
        basicForm.setId(basicForms.get(0).getId());
        basicForm.setStatus(BasicFormStatus.COMPLETED.getCode());
        log.info("更新表单状态参数=" + JSON.toJSONString(basicForm));
        if (basicFormMapper.updateById(basicForm) != 1) {
            throw new RuntimeException("更新表单完成状态失败");
        }

        //修改子表单 formType=device(ipdf fsr)
        //改版  找pid不为空 改为废板
        //取消 查询pid不为空  改成完成

        log.info("修改主表单状态为完成，继续查询是否有子表单");
        HashMap<String, Object> sonMap = Maps.newHashMap();
        sonMap.put("MAIN_ID", basicForms.get(0).getId());
        List<BasicForm> sonBasicForm = basicFormMapper.selectByMap(sonMap);
        if (sonBasicForm != null && sonBasicForm.size() > 0) {
            log.info("有子表单，继续判断是否是ipdf或者是fsr");
            sonBasicForm.forEach(i -> {
                if ("jdv".equals(i.getFormType()) || "mtf".equals(i.getFormType()) || "ipdf".equals(i.getFormType()) || "fsr".equals(i.getFormType())) {
                    BasicForm form = new BasicForm();
                    form.setUpdateTm(now);
                    form.setId(i.getId());
                    form.setStatus(BasicFormStatus.COMPLETED.getCode());
                    basicFormMapper.updateById(form);
                }
            });
        }
        //判断是否是改版
        String pid = basicForms.get(0).getPid();
        if (!StringUtils.isEmpty(pid)) {
            BasicForm pidForm = basicFormMapper.selectById(pid);
            if (pidForm != null) {
                log.info("pid不为空，把之前的版本改成废版本");
                BasicForm form = new BasicForm();
                form.setUpdateTm(now);
                form.setId(pidForm.getId());
                form.setStatus(BasicFormStatus.WASTE_VERSION.getCode());
                basicFormMapper.updateById(form);
                if (pidForm.getFormType().equals("mtr")) {
                    HashMap<String, Object> sonMap2 = Maps.newHashMap();
                    sonMap2.put("MAIN_ID", pidForm.getId());
                    List<BasicForm> mtrSonBasicForm = basicFormMapper.selectByMap(sonMap2);
                    mtrSonBasicForm.forEach(i -> {
                        log.info("修改mtr子表单的状态");
                        BasicForm basicForm4 = new BasicForm();
                        basicForm4.setId(i.getId());
                        basicForm4.setStatus(BasicFormStatus.WASTE_VERSION.getCode());
                        basicForm4.setUpdateTm(new Date());
                        basicFormMapper.updateById(basicForm4);

                    });
                }
            }
        }


        setRevesion(basicForms.get(0));

        log.info("修改表单状态成功");
        return new AsyncResult("更改表单状态完成");
    }

    /**
     * 设置改版
     *
     * @param basicForm
     */
    public void setRevesion(BasicForm basicForm) {
        String requester = basicForm.getRequester();
        String accountType = basicForm.getUserType();
        if (ProcessNodeConstant.FORM_TYPE_TOPTIER.equals(basicForm.getFormType())) {
            log.info("toptier流程结束,需要设置改版人");
//            1、流程结束后可发起改版
//            2、申请人及申请人所属组织内(部门)的人员有权限发起改版
//            3、拥有改版功能项的人员可发起改版


            if (accountType.equals(ProcessNodeConstant.ACCOUNT_TYPE_EMPLOYEE)) {
                //员工
                //先将申请人插入数据库
                FormPermission formPermision = getFormPermision(requester, basicForm.getFormType(), basicForm.getId(), ProcessNodeConstant.ACCOUNT_TYPE_EMPLOYEE, requester, ProcessNodeConstant.REVISION);
                formPermissionMapper.insert(formPermision);
                String deptmentId = nodeAuditorMapper.selectEmployeeDeptmentIdById(requester);
                if (!StringUtils.isEmpty(deptmentId)) {
                    log.info("部门ID不为空，开始将部门信息插入权限表");
                    FormPermission depetPermission = getFormPermision(requester, basicForm.getFormType(), basicForm.getId(), ProcessNodeConstant.ACCOUNT_TYPE_DEPT, deptmentId, ProcessNodeConstant.REVISION);
                    if (formPermissionMapper.insert(depetPermission) != 1) {
                        throw new RuntimeException("插入部门权限失败");
                    }
                } else {
                    log.info("申请人部门ID为空");
                }

            } else if (accountType.equals(ProcessNodeConstant.ACCOUNT_TYPE_ACCOUNT)) {
                //客户
                //1.申请人及申请人所属组织内（部门）的人员
                //2.历史审批人
                //3.查看所有申请单功能项人员
                //4.Mask群组中的人员
                //调用客户接口
                List<CustomerAccountBasicVO> childAndParentById = apiCustomerAccountService.findChildAndParentById(requester);
                childAndParentById.forEach(s -> {
                    FormPermission accountPermission = getFormPermision(requester, basicForm.getFormType(), basicForm.getId(), ProcessNodeConstant.ACCOUNT_TYPE_ACCOUNT, s.getId(), ProcessNodeConstant.REVISION);
                    log.info("新增客户账户权限");
                    formPermissionMapper.insert(accountPermission);
                });
            } else {
                log.error("账户类型为未知类型");
            }

            //公共处理
            log.info("开始查询申请单功能项人员");
            //todo  写死  之前按照表设计可达到直接查表，但是业务不允许这样做。。。。。。
            List<FormGroupFunc> groupNameList = formGroupFuncMapper.selectGroupNameByFormType(basicForm.getFormType());
            groupNameList = groupNameList.stream().filter(g->!StringUtils.isEmpty(g.getGroupName())).collect(Collectors.toList());
             String groupJobReview = null;
            for (int i = 0; i < groupNameList.size(); i++) {
                if(groupNameList.get(i).getGroupName().toLowerCase().contains("revision")){
                    groupJobReview = groupNameList.get(i).getGroupName();
                    break;
                }
            }
            FormPermission var1 = getFormPermision(requester, basicForm.getFormType(), basicForm.getId(), ProcessNodeConstant.ACCOUNT_TYPE_GROUP, groupJobReview, ProcessNodeConstant.REVISION);
            log.info("设置功能项人员入库参数=" + JSON.toJSONString(var1));
            formPermissionMapper.insert(var1);


        } else if (ProcessNodeConstant.FORM_TYPE_MTR.equals(basicForm.getFormType())) {
            log.info("mtr流程结束,需要设置改版人");
//            1、表单状态为完成，只读页面显示该按钮
//            2、申请人及申请人所属组内所有人员可发起改版
            if (accountType.equals(ProcessNodeConstant.ACCOUNT_TYPE_EMPLOYEE)) {
                //员工
                //先将申请人插入数据库
                FormPermission formPermision = getFormPermision(requester, basicForm.getFormType(), basicForm.getId(), ProcessNodeConstant.ACCOUNT_TYPE_EMPLOYEE, requester, ProcessNodeConstant.REVISION);
                formPermissionMapper.insert(formPermision);
                List<String> groupIds = nodeAuditorMapper.selectEmployeeGroupNameById(requester);
                log.info("开始进行申请人所在账号组内的所有人员");
                if (groupIds != null && groupIds.size() > 0) {
                    groupIds = groupIds.stream().distinct().collect(Collectors.toList());
                    groupIds.forEach(group -> {
                        FormPermission var1 = getFormPermision(requester, basicForm.getFormType(), basicForm.getId(), ProcessNodeConstant.ACCOUNT_TYPE_GROUP, group, ProcessNodeConstant.REVISION);
                        formPermissionMapper.insert(var1);
                    });

                } else {
                    log.error("group群组名称为空");
                }
            } else if (accountType.equals(ProcessNodeConstant.ACCOUNT_TYPE_ACCOUNT)) {

                List<CustomerAccountBasicVO> childAndParentById = apiCustomerAccountService.findChildAndParentById(requester);
                childAndParentById.forEach(s -> {
                    FormPermission accountPermission = getFormPermision(requester, basicForm.getFormType(), basicForm.getId(), ProcessNodeConstant.ACCOUNT_TYPE_ACCOUNT, s.getId(), ProcessNodeConstant.REVISION);
                    log.info("新增客户账户权限");
                    formPermissionMapper.insert(accountPermission);
                });
            } else {
                log.error("账户类型为未知类型");
            }

        } else {
            log.info("无需设置改版人");
        }
    }





    public FormPermission getFormPermision(String createBy, String formType, String businessId, String userType, String userTypeId, String status) {
        FormPermission formPermission = new FormPermission();
        //创建人
        formPermission.setCreatedBy(createBy);
        //创建时间
        formPermission.setCreateTm(new Date());
        //表单类型
        formPermission.setFormType(formType);
        //basicFormID
        formPermission.setBasicFormId(businessId);
        //账户类型
        formPermission.setBizUserType(userType);
        //账户类型ID
        formPermission.setBizUserTypeId(userTypeId);
        //账户权限
        formPermission.setBizPermission(status);
        return formPermission;
    }

    public BasicForm getBasicForm(String id,String status,String createdBy,Date createTm,String updatedBy,Date updateTm){
        BasicForm basicForm = new BasicForm();

        if(!StringUtils.isEmpty(id)){
            basicForm.setId(id);
        }
        if(!StringUtils.isEmpty(status)){
            basicForm.setStatus(status);
        }

        if(!StringUtils.isEmpty(createdBy)){
            basicForm.setCreatedBy(createdBy);
        }

        if(createTm!=null){
            basicForm.setCreateTm(createTm);
        }

        if(!StringUtils.isEmpty(updatedBy)){
            basicForm.setUpdatedBy(updatedBy);
        }

        if(updateTm!=null){
            basicForm.setUpdateTm(updateTm);
        }

        return basicForm;
    }


}
