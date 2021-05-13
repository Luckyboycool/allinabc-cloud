///*
// * @(#)BizNodeController.java 2020年1月14日 上午11:36:58
// * Copyright 2020 zmr, Inc. All rights reserved.
// * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// */
//package com.allinabc.cloud.activiti.controller;
//
//import com.allinabc.cloud.activiti.pojo.po.BizNode;
//import com.allinabc.cloud.activiti.service.IBizNodeService;
//import com.allinabc.cloud.common.core.service.CommonService;
//import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
//import org.activiti.engine.RepositoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * <p>File：BizNodeController.java</p>
// * <p>Title: </p>
// * <p>Description:</p>
// * <p>Copyright: Copyright (c) 2020 2020年1月14日 上午11:36:58</p>
// * <p>Company: zmrit.com </p>
// * @author zmr
// * @version 1.0
// */
//@RestController
//@RequestMapping("node")
//public class BizNodeController extends MybatisBaseCrudController<BizNode> {
//
//    @Autowired
//    private RepositoryService repositoryService;
//    @Autowired
//    private IBizNodeService   bizNodeService;
//
//    //    /**
////     * 获取节点列表
////     *
////     * @param proDefId
////     * @return
////     * @author zmr
////     */
////    @GetMapping("list/{proDefId}")
////    public Result<List<ProcessNodeVo>> list(@PathVariable String proDefId) {
////        List<ProcessNodeVo> list = new ArrayList<>();
////        BpmnModel model = repositoryService.getBpmnModel(proDefId);
////        if (model != null) {
////            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
////            for (FlowElement element : flowElements) {
////                ProcessNodeVo node = new ProcessNodeVo();
////                node.setNodeId(element.getId());
////                node.setName(element.getName());
////                if (element instanceof StartEvent) {
////                    // 开始节点
////                    node.setType(ActivitiConstant.NODE_TYPE_START);
////                }
////                else if (element instanceof UserTask) {
////                    // 用户任务
////                    node.setType(ActivitiConstant.NODE_TYPE_TASK);
////                }
////                else if (element instanceof EndEvent) {
////                    // 结束
////                    node.setType(ActivitiConstant.NODE_TYPE_END);
////                }
////                else {
////                    // 排除其他连线或节点
////                    continue;
////                }
////                list.add(node);
////            }
////        }
////        return Result.success(list);
////    }
////
////    /**
////     * 获取节点属性
////     *
////     * @param nodeId
////     * @return
////     * @author zmr
////     */
////    @GetMapping("get/{nodeId}")
////    public Result get(@PathVariable String nodeId) {
////        ProcessNodeVo node = new ProcessNodeVo();
////        node.setNodeId(nodeId);
////        // 设置关联角色，用户，负责人
////        node = bizNodeService.setAuditors(node);
////        return Result.success(node);
////    }
//
//    @Override
//    protected CommonService<BizNode> getService() {
//        return bizNodeService;
//    }
//
//}
