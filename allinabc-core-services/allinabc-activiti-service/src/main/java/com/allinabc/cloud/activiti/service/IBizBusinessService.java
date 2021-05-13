///*
// * @(#)IBizBusinessService.java 2020年1月6日 下午3:38:40
// * Copyright 2020 zmr, Inc. All rights reserved.
// * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// */
//package com.allinabc.cloud.activiti.service;
//
//import com.allinabc.cloud.activiti.pojo.po.ActReProcdef;
//import com.allinabc.cloud.activiti.pojo.po.BizBusiness;
//import com.allinabc.common.mybatis.service.MybatisCommonService;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * <p>File：IBizBusinessService.java</p>
// * <p>Title: </p>
// * <p>Description:</p>
// * <p>Copyright: Copyright (c) 2020 2020年1月6日 下午3:38:40</p>
// */
//public interface IBizBusinessService extends MybatisCommonService<BizBusiness> {
//
//    /**
//     * start 启动流程
//     *
//     * @param business 业务对象，必须包含id,title,userId,procDefId属性
//     * @param variables 启动流程需要的变量
//     */
//    void startProcess(BizBusiness business, Map<String, Object> variables);
//
//    /**
//     * check 检查负责人
//     * @param business 业务对象，必须包含id,procInstId属性
//     * @param result 审批结果
//     * @param currentUserId 当前操作用户 可能是发起人或者任务处理人
//     * @return
//     */
//    public int setAuditor(BizBusiness business, int result, String currentUserId);
//
//}
