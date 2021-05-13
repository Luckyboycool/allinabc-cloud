package com.allinabc.cloud.activiti.mapper;

import com.allinabc.cloud.activiti.pojo.bo.AuditorModel;
import com.allinabc.cloud.activiti.pojo.bo.NameModel;
import com.allinabc.cloud.activiti.pojo.po.NodeAuditor;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/3 19:18
 **/
public interface NodeAuditorMapper extends MybatisCommonBaseMapper<NodeAuditor> {

    /**
     * 批量保存审批人
     * @param list
     * @return
     */
    int saveNodeAuditor(@Param("ls") List<NodeAuditor> list);


    /**
     * 查询节点审批人的类型
     * @param nodeKey
     * @param processDefinitionKey
     * @return
     */
    List<NodeAuditor> selectNodeAuditTypeByNodeIdAndProcessId(@Param("nodeId")String nodeKey, @Param("processDefinitionKey")String processDefinitionKey);

    /**
     * 查询流程审批人
     * @param processDefinitionKey
     * @return
     */
    List<String> selectAuditByNodeIdAndProcessId(@Param("type") String type,@Param("auditor")String auditor, @Param("processDefinitionKey")String processDefinitionKey);


    List<AuditorModel> selectAuditAndTypeByNodeIdAndProcessId(@Param("type") String type,@Param("auditor") String auditor, @Param("processDefinitionKey")String processDefinitionKey);

    /**
     * 根据用户类型和userId查询用户姓名
     * @param userType
     * @param requester
     * @return
     */
    String selectUserNameByUserType(@Param("userType") String userType, @Param("requester")String requester);

    NameModel selectUserNameAndJobNumByUserType(@Param("userType") String userType, @Param("requester")String requester);

    /**
     * 根据userId查询部门ID
     * @param userId
     * @return
     */
    String selectEmployeeDeptmentIdById(@Param("id") String userId);

    /**
     * 查询雇员群组名称
     * @param userId
     * @return
     */
    List<String> selectEmployeeGroupNameById(@Param("id")String userId);

    /**
     * 根查询申请人部门直属领导
     * @param requester
     * @return
     */
    List<String> selectLeaderByRequester(@Param("requester") String requester);

    /**
     * 根据部门name查询审核人
     * @param deptId
     * @return
     */
    List<String> selectDeptAuditorByDeptId(@Param("deptId") String deptId);

    /**
     * 通过所选的fab和表单类型选择对应的群组名称
     * @param fab
     * @param formType
     * @param nodeKey
     * @return
     */
    String selectGroupNameByFabCode(@Param("fab") String fab, @Param("formType") String formType, @Param("nodeKey") String nodeKey);

    /**
     * 根据群组名称查询群组人员
     * @param groupName
     * @return
     */
    List<String> selectGroupAuditorByGroupName(@Param("groupName") String groupName);

    /**
     * 通过角色ID查询审核人
     * @param roleId
     * @return
     */
    List<String> selectRoleAuditorByRoleId(@Param("roleId") String roleId);
}
