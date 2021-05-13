package com.allinabc.cloud.activiti.adaptimpl.permission;

import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.adapt.FormPermissionAdapt;
import com.allinabc.cloud.activiti.mapper.BizGroupAuditorMapper;
import com.allinabc.cloud.activiti.mapper.FormGroupFuncMapper;
import com.allinabc.cloud.activiti.mapper.FormPermissionMapper;
import com.allinabc.cloud.activiti.mapper.NodeAuditorMapper;
import com.allinabc.cloud.activiti.pojo.consts.ProcessNodeConstant;
import com.allinabc.cloud.activiti.pojo.po.FormGroupFunc;
import com.allinabc.cloud.activiti.pojo.po.FormPermission;
import com.allinabc.cloud.common.core.exception.BusinessException;
import com.allinabc.cloud.org.api.service.api.ApiCustomerAccountService;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountBasicVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description device
 * @Author wangtaifeng
 * @Date 2021/3/24 11:05
 **/
@Service
@Slf4j
public class IpMergeFormPermissionService implements FormPermissionAdapt {

    @Autowired
    private ApiCustomerAccountService apiCustomerAccountService;

    @Autowired
    private FormPermissionMapper formPermissionMapper;

    @Autowired
    private NodeAuditorMapper nodeAuditorMapper;

    @Autowired
    private FormGroupFuncMapper formGroupFuncMapper;

    @Resource
    private BizGroupAuditorMapper bizGroupAuditorMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStartFormPerms(String formType, String businessId, String accountType, String userId, Map<String, Object> variables,String taskDefKey,String procDefKey) {
        log.info("IpMerge启动流程保存权限");
//        1.申请人及申请人所在账号组内的所有人员
//        2.历史审批人
//        3.查看所有申请单功能项人员
//        Device表单的“Foundry 区域”取对应的CE、PIE、DS、Mask、Mask（DS）群组
        if (accountType.equals(ProcessNodeConstant.ACCOUNT_TYPE_EMPLOYEE)) {
            //员工
            //先将申请人插入数据库
            FormPermission formPermision = getFormPermision(userId, formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_EMPLOYEE, userId, ProcessNodeConstant.READ);
            formPermissionMapper.insert(formPermision);
            List<String> groupIds = nodeAuditorMapper.selectEmployeeGroupNameById(userId);
            log.info("开始进行申请人所在账号组内的所有人员");
            if(groupIds!=null && groupIds.size()>0){
                groupIds =groupIds.stream().distinct().collect(Collectors.toList());
                groupIds.forEach(group->{
                    FormPermission var1 = getFormPermision(userId, formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_GROUP, group, ProcessNodeConstant.READ);
                    formPermissionMapper.insert(var1);
                });

            }else{
                log.error("group群组名称为空");
            }


        } else if (accountType.equals(ProcessNodeConstant.ACCOUNT_TYPE_ACCOUNT)) {
            //        1.申请人及申请人所在账号组内的所有人员
            //        2.历史审批人
            //        3.查看所有申请单功能项人员
           //        Device表单的“Foundry 区域”取对应的CE、PIE、DS、Mask、Mask（DS）群组
            List<CustomerAccountBasicVO> childAndParentById = apiCustomerAccountService.findChildAndParentById(userId);
            childAndParentById.forEach(s -> {
                FormPermission accountPermission = getFormPermision(userId, formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_ACCOUNT, s.getId(), ProcessNodeConstant.READ);
                log.info("新增客户账户权限");
                formPermissionMapper.insert(accountPermission);
            });
        } else {
            log.error("账户类型为未知类型");
        }

        //公共处理
        List<FormGroupFunc> groupNameList = formGroupFuncMapper.selectGroupNameByFormType(formType);
        groupNameList.stream().forEach(j->{
            if(j.getIsFab().equals("N")){
                FormPermission var1 = getFormPermision(userId, formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_GROUP, j.getGroupName(), ProcessNodeConstant.READ);
                log.info("设置功能项人员入库参数=" + JSON.toJSONString(var1));
                formPermissionMapper.insert(var1);
            }else if(j.getIsFab().equals("Y")){
                Object fab = variables.get("fab");
                if(fab==null){
                    throw new BusinessException("fab为空");
                }
                String fabCode = (String)fab;
                FormPermission var1 = getFormPermision(userId, formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_GROUP, j.getGroupName()+"_"+fabCode, ProcessNodeConstant.READ);
                log.info("ipmerge表单的“Foundry 区域”取对应的CE、PIE、DS、Mask、Mask（DS）群组=" + JSON.toJSONString(var1));
                formPermissionMapper.insert(var1);
            }else {
                log.error("表单权限出错，未找到对应类型的group!!!!!!!!!!!!!!!");
            }

        });



    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExecuteFormPerms(String formType, String businessId, String accountType, String userId, Map<String, Object> variables,String taskDefKey,String procDefKey,String startUser) {
        log.info("执行中动态设置权限,首先查询是否此表单之前配置过此审核人");
        List<FormPermission> formPermissions = formPermissionMapper.selectReadPermisonByBusinessId(businessId, userId, accountType, formType);
        if (formPermissions == null || formPermissions.size() == 0) {
            FormPermission var = getFormPermision(userId, formType, businessId, accountType, userId, ProcessNodeConstant.READ);
            if (formPermissionMapper.insert(var) != 1) {
                throw new RuntimeException("动态设置表单权限失败");
            }
        } else {
            log.info("此审核人之前已配置此表单权限");
        }


        if(taskDefKey.equals(ProcessNodeConstant.IPMERGE_CREATE)){
            log.info("当前节点为："+ProcessNodeConstant.IPMERGE_CREATE+" 需要删除之前的group群组的权限，然后再插入现在群组的权限");
            //描述：首先根据申请人查出之前的group，如果查出来了说明此用户类型不是客户(客户是查不到的group)
            List<String> groupIds =null;
            if(accountType.equals(ProcessNodeConstant.ACCOUNT_TYPE_EMPLOYEE)) {
                log.info("此用户是Employee");
                groupIds = nodeAuditorMapper.selectEmployeeGroupNameById(startUser);
                groupIds = groupIds.stream().filter(m->m!=null).collect(Collectors.toList());
            }
            formPermissionMapper.deleteFabGroupPermission(formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_GROUP,groupIds);
            log.info("删除之前的fab_group成功,开始插入新fab");
            List<FormGroupFunc> groupFuncList = formGroupFuncMapper.selectGroupNameByFormType(formType);
            Object fab = variables.get("fab");
            if(fab==null){
                throw new BusinessException("fab为空！！！");
            }
            String fabCode = (String)fab;
            log.info("fab="+fabCode);
            groupFuncList.stream().forEach(gruop->{
                String tempGroup = gruop.getGroupName();
                if(gruop.getIsFab().equals("Y")){
                    tempGroup = tempGroup+"_"+fabCode;
                }
                FormPermission var2 = getFormPermision(userId, formType, businessId, ProcessNodeConstant.ACCOUNT_TYPE_GROUP, tempGroup, ProcessNodeConstant.READ);
                formPermissionMapper.insert(var2);
                log.info("插入新group成功");
            });
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
}
