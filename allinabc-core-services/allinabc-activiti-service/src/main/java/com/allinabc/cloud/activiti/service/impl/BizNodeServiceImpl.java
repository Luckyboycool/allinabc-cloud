//package com.allinabc.cloud.activiti.service.impl;
//
//import cn.hutool.core.util.StrUtil;
//import com.allinabc.cloud.activiti.mapper.BizNodeMapper;
//import com.allinabc.cloud.activiti.mapper.BizPurchaseMapper;
//import com.allinabc.cloud.activiti.pojo.consts.ActivitiConstant;
//import com.allinabc.cloud.activiti.pojo.po.BizNode;
//import com.allinabc.cloud.activiti.pojo.po.BizPurchase;
//import com.allinabc.cloud.activiti.pojo.vo.ProcessNodeVo;
//import com.allinabc.cloud.activiti.service.IBizNodeService;
////import com.allinabc.cloud.admin.api.service.api.ApiUserService;
////import com.allinabc.cloud.admin.pojo.po.User;
//import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
//import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Sets;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * 节点Service业务层处理
// *
// * @author ruoyi
// * @date 2020-01-07
// */
//@Service
//public class BizNodeServiceImpl extends MybatisCommonServiceImpl<BizNode> implements IBizNodeService {
//
//    @Autowired
//    private BizNodeMapper bizNodeMapper;
////    @Autowired
////    private ApiUserService apiUserService;
////    @Autowired
////    private RemoteDeptService remoteDeptService;
//
//    @Override
//    public ProcessNodeVo setAuditors(ProcessNodeVo node) {
//        List<BizNode> bizNodes = super.selectList(new BizNode().setNodeId(node.getNodeId()));
//        List<String> roleIds = Lists.newArrayList();
//        List<String> userIds = Lists.newArrayList();
//        List<String> deptIds = Lists.newArrayList();
//        for (BizNode bizNode : bizNodes) {
//            // 设置关联角色
//            if (bizNode.getType().equals(ActivitiConstant.NODE_ROLE)) {
//                roleIds.add(bizNode.getAuditor());
//            }
//            // 设置关联部门
//            else if (bizNode.getType().equals(ActivitiConstant.NODE_DEPARTMENT)) {
//                deptIds.add(bizNode.getAuditor());
//            }
//            // 设置关联用户
//            else if (bizNode.getType().equals(ActivitiConstant.NODE_USER)) {
//                userIds.add(bizNode.getAuditor());
//            }
//            else if (bizNode.getType().equals(ActivitiConstant.NODE_DEP_HEADER)) {
//                // 是否设置操作人负责人
//                node.setDeptHeader(true);
//            }
//        }
//        // 设置关联角色
//        node.setRoleIds(roleIds);
//        // 设置关联部门
//        node.setDeptIds(deptIds);
//        // 设置关联用户
//        node.setUserIds(userIds);
//
//        return node;
//    }
//
//    @Override
//    public int updateBizNode(ProcessNodeVo node) {
//        // 删除所有节点信息
//        super.delete(new BizNode().setNodeId(node.getNodeId()));
//        List<BizNode> bizNodes = Lists.newArrayList();
//        List<String> roleIds = node.getRoleIds();
//        if (null != roleIds && roleIds.size() > 0) {
//            for (String roleId : roleIds) {
//                bizNodes.add(new BizNode().setNodeId(node.getNodeId()).setType(ActivitiConstant.NODE_ROLE)
//                        .setAuditor(roleId));
//            }
//        }
//        List<String> deptIds = node.getDeptIds();
//        if (null != deptIds && deptIds.size() > 0) {
//            for (String deptId : node.getDeptIds()) {
//                bizNodes.add(new BizNode().setNodeId(node.getNodeId()).setType(ActivitiConstant.NODE_DEPARTMENT)
//                        .setAuditor(deptId));
//            }
//        }
//        List<String> userIds = node.getUserIds();
//        if (null != userIds && userIds.size() > 0) {
//            for (String userId : userIds) {
//                bizNodes.add(new BizNode().setNodeId(node.getNodeId()).setType(ActivitiConstant.NODE_USER)
//                        .setAuditor(userId));
//            }
//        }
//        if (null != node.getDeptHeader() && node.getDeptHeader()) {
//            bizNodes.add(new BizNode().setNodeId(node.getNodeId()).setType(ActivitiConstant.NODE_DEP_HEADER));
//        }
//        super.batchInsert(bizNodes);
//        return 1;
//    }
//
//    @Override
//    public Set<String> getAuditors(String nodeId, String userId) {
//        // TODO 优化查询次数可以将同类型审核人一次性查询得到
//        List<BizNode> bizNodes = super.selectList(new BizNode().setNodeId(nodeId));
//        List<String> auditors = Lists.newArrayList();
//        List<String> roleIds = Lists.newArrayList();
//        List<String> deptIds = Lists.newArrayList();
//        if (null != bizNodes && bizNodes.size() > 0) {
//            for (BizNode node : bizNodes) {
//                if (node.getType().equals(ActivitiConstant.NODE_USER)) {
//                    // 如果是用户类型直接塞到审核人结合
//                    auditors.add(node.getAuditor());
//                }
//                else if (node.getType().equals(ActivitiConstant.NODE_ROLE)) {
//                    // 查询所有拥有有当前角色编号的用户
//                    roleIds.add(node.getAuditor());
//                }
//                else if (node.getType().equals(ActivitiConstant.NODE_DEPARTMENT)) {
//                    deptIds.add(node.getAuditor());
//                }
//                else if (node.getType().equals(ActivitiConstant.NODE_DEP_HEADER)) {
////                    User user = apiUserService.get(userId);
////                    SysDept dept = remoteDeptService.selectSysDeptByDeptId(user.);
//                    // 查询所有用有当前用户部门的负责人
////                    auditors.add(dept.getLeaderId());
//                }
//            }
//        }
//        if (roleIds.size() > 0) {
////            auditors.addAll(apiUserService.selectUserIdsHasRoles(roleIds));
//        }
//        if (deptIds.size() > 0) {
////            auditors.addAll(apiUserService.selectUserIdsInDeparts(deptIds));
//        }
//        return auditors.stream().map(Object::toString).collect(Collectors.toSet());
//    }
//
//    @Override
//    protected MybatisCommonBaseMapper<BizNode> getRepository() {
//        return bizNodeMapper;
//    }
//}
