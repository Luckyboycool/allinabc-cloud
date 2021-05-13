//package com.allinabc.cloud.activiti.controller;
//
//import com.allinabc.cloud.activiti.pojo.po.ActReProcdef;
//import com.allinabc.cloud.activiti.service.IActReProcdefService;
//import com.allinabc.cloud.common.core.service.CommonService;
//import com.allinabc.cloud.common.web.pojo.resp.Result;
//import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.RuntimeService;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// * 流程控制接口
// */
//@RestController
//@RequestMapping("/prof")
//public class ActivitiController extends MybatisBaseCrudController<ActReProcdef> {
//
//    @Resource
//    private RepositoryService       repositoryService;
//    @Resource
//    private RuntimeService          runtimeService;
//    @Resource
//    private IActReProcdefService    procdefService;
//
//    /**
//     * 启动一个流程
//     * @param key
//     * @return
//     * @author zmr
//     */
//    @GetMapping("/start/{key}")
//    public Result<Void> start(@PathVariable("key") String key) {
//        runtimeService.startProcessInstanceByKey(key);
//        return Result.success();
//    }
//
////    @GetMapping("/allLatest")
////    public Result<List<ReProcdef>> list() {
////        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().latestVersion();
////        List<ProcessDefinition> processDefinitions = query.list();
////        List<ReProcdef> list = new ArrayList<>();
////        for (ProcessDefinition processDefinition : processDefinitions) {
////            ReProcdef reProcdef = new ReProcdef(processDefinition);
////            list.add(reProcdef);
////        }
////        return Result.success(list);
////    }
//
//
//    @Override
//    protected CommonService<ActReProcdef> getService() {
//        return procdefService;
//    }
//
//}
