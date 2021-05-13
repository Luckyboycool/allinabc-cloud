package com.allinabc.cloud.activiti.adaptimpl.permission;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.adapt.FormPermissionAdapt;
import com.allinabc.cloud.activiti.mapper.FormGroupFuncMapper;
import com.allinabc.cloud.activiti.mapper.FormPermissionMapper;
import com.allinabc.cloud.activiti.mapper.NodeAuditorMapper;
import com.allinabc.cloud.activiti.pojo.consts.ProcessNodeConstant;
import com.allinabc.cloud.activiti.pojo.po.FormGroupFunc;
import com.allinabc.cloud.activiti.pojo.po.FormPermission;
import com.allinabc.cloud.activiti.pojo.po.NodeAuditor;
import com.allinabc.cloud.org.api.service.api.ApiCustomerAccountService;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountBasicVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/24 11:05
 **/
@Service
@Slf4j
public class ToptierFormPermissionService implements FormPermissionAdapt {

    @Autowired
    private ApiCustomerAccountService apiCustomerAccountService;

    @Autowired
    private FormPermissionMapper formPermissionMapper;

    @Autowired
    private NodeAuditorMapper nodeAuditorMapper;

    @Autowired
    private FormGroupFuncMapper formGroupFuncMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStartFormPerms(String formType, String businessId, String accountType, String userId, Map<String, Object> variables,String taskDefKey,String procDefKey) {
        log.info("toptier启动流程保存权限");
        //1.申请人及申请人所属组织内（部门）的人员
        //2.历史审批人
        //3.查看所有申请单功能项人员
        //4.Mask群组中的人员
        if(accountType.equals(ProcessNodeConstant.ACCOUNT_TYPE_EMPLOYEE)){
            //员工
            //先将申请人插入数据库
            FormPermission formPermision = getFormPermision(userId,formType,businessId,ProcessNodeConstant.ACCOUNT_TYPE_EMPLOYEE,userId,ProcessNodeConstant.READ);
            formPermissionMapper.insert(formPermision);
            String deptmentId = nodeAuditorMapper.selectEmployeeDeptmentIdById(userId);
           if(!StringUtils.isEmpty(deptmentId)){
               log.info("部门ID不为空，开始将部门信息插入权限表");
               FormPermission depetPermission = getFormPermision(userId, formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_DEPT, deptmentId, ProcessNodeConstant.READ);
               if(formPermissionMapper.insert(depetPermission)!=1){
                   throw new RuntimeException("插入部门权限失败");
               }
           }else {
               log.info("申请人部门ID为空");
           }

        }else if(accountType.equals(ProcessNodeConstant.ACCOUNT_TYPE_ACCOUNT)){
            //客户
            //1.申请人及申请人所属组织内（部门）的人员
            //2.历史审批人
            //3.查看所有申请单功能项人员
            //4.Mask群组中的人员
            //调用客户接口
            List<CustomerAccountBasicVO> childAndParentById = apiCustomerAccountService.findChildAndParentById(userId);
            childAndParentById.forEach(s->{
                FormPermission accountPermission = getFormPermision(userId, formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_ACCOUNT, s.getId(), ProcessNodeConstant.READ);
                log.info("新增客户账户权限");
                formPermissionMapper.insert(accountPermission);
            });
        }else{
            log.error("账户类型为未知类型");
        }

        //公共处理
        log.info("开始查询申请单功能项人员");

        List<FormGroupFunc> groupNameList = formGroupFuncMapper.selectGroupNameByFormType(formType);
        groupNameList.stream().distinct().forEach(groupName->{
            FormPermission var1 = getFormPermision(userId, formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_GROUP, groupName.getGroupName(), ProcessNodeConstant.READ);
            log.info("设置功能项人员入库参数="+ JSON.toJSONString(var1));
            formPermissionMapper.insert(var1);
        });
//        log.info("开始设置Mask群组中的人员");
//        List<NodeAuditor> nodeAuditors = nodeAuditorMapper.selectNodeAuditTypeByNodeIdAndProcessId(ProcessNodeConstant.TOPTIER_MASK, ProcessNodeConstant.TOPTIER_PROCESS_DEF_KEY);
//        if(nodeAuditors!=null&&nodeAuditors.size()>0){
//            List<FormPermission> formPermissions = formPermissionMapper.selectReadPermisonByBusinessId(businessId, nodeAuditors.get(0).getAuditor(), ProcessNodeConstant.ACCOUNT_TYPE_GROUP, formType);
//            if(formPermissions!=null&&formPermissions.size()>0){
//                log.info("已经有此群组的信息了，无需添加");
//            }else{
//                FormPermission formPermision = getFormPermision(userId, formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_GROUP, nodeAuditors.get(0).getAuditor(), ProcessNodeConstant.READ);
//                formPermissionMapper.insert(formPermision);
//            }
//        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExecuteFormPerms(String formType, String businessId, String accountType, String userId, Map<String, Object> variables,String taskDefKey,String procDefKey,String startUser) {
        log.info("执行中动态设置权限,首先查询是否此表单之前配置过此审核人");
        List<FormPermission> formPermissions = formPermissionMapper.selectReadPermisonByBusinessId(businessId, userId, accountType, formType);
        if(formPermissions==null||formPermissions.size()==0) {
            FormPermission var = getFormPermision(userId, formType, businessId, accountType, userId, ProcessNodeConstant.READ);
            if(formPermissionMapper.insert(var)!=1){
                throw new RuntimeException("动态设置表单权限失败");
            }
        }else{
            log.info("此审核人之前已配置此表单权限");
        }

    }




    public FormPermission getFormPermision(String createBy,String formType,String businessId,String userType,String userTypeId,String status){
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
}
