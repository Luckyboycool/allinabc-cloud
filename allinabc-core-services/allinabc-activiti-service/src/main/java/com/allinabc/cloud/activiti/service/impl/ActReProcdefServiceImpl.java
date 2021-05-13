//package com.allinabc.cloud.activiti.service.impl;
//
//import com.allinabc.cloud.activiti.mapper.ActReProcdefMapper;
//import com.allinabc.cloud.activiti.pojo.po.ActReProcdef;
//import com.allinabc.cloud.activiti.service.IActReProcdefService;
//import com.allinabc.cloud.common.core.exception.BusinessException;
//import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
//import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.RuntimeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
//@Service
//public class ActReProcdefServiceImpl extends MybatisCommonServiceImpl<ActReProcdef> implements IActReProcdefService {
//
//    @Autowired
//    private ActReProcdefMapper procdefMapper;
//    @Resource
//    private RepositoryService repositoryService;
//    @Resource
//    private RuntimeService runtimeService;
//
//    @Override
//    public boolean beforeDeleteById(String id) {
//        super.beforeDeleteById(id);
//        long count = runtimeService.createProcessInstanceQuery().deploymentId(id).count();
//        if (count > 0) {
//            throw new BusinessException("流程正在运行中，无法删除");
//        }
//        else {
//            // 根据deploymentID删除定义的流程，普通删除
//            repositoryService.deleteDeployment(id);
//        }
//        return true;
//    }
//
//    @Override
//    protected MybatisCommonBaseMapper<ActReProcdef> getRepository() {
//        return procdefMapper;
//    }
//}
