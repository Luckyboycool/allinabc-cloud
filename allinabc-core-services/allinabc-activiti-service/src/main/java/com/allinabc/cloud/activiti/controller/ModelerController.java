//package com.allinabc.cloud.activiti.controller;
//
//import com.allinabc.cloud.activiti.pojo.po.ActReModel;
//import com.allinabc.cloud.activiti.service.IActReModelService;
//import com.allinabc.cloud.common.core.service.CommonService;
//import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.activiti.engine.RepositoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 模型管理
// */
//@Controller
//@RequestMapping("models")
//public class ModelerController extends MybatisBaseCrudController<ActReModel> {
//
//    @Autowired
//    private RepositoryService   repositoryService;
//    @Autowired
//    private ObjectMapper        objectMapper;
//    @Autowired
//    private IActReModelService  modelService;
//
////    /**
////     * 新建一个空模型
////     * @return
////     * @throws UnsupportedEncodingException
////     */
////    @GetMapping("newModel")
////    public Object newModel() throws UnsupportedEncodingException {
////        // 初始化一个空模型
////        Model model = repositoryService.newModel();
////        // 设置一些默认信息
////        String name = "new-process";
////        String description = "";
////        int revision = 1;
////        String key = "process";
////        ObjectNode modelNode = objectMapper.createObjectNode();
////        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
////        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
////        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
////        model.setName(name);
////        model.setKey(key);
////        model.setMetaInfo(modelNode.toString());
////        repositoryService.saveModel(model);
////        String id = model.getId();
////        // 完善ModelEditorSource
////        ObjectNode editorNode = objectMapper.createObjectNode();
////        editorNode.put("id", "canvas");
////        editorNode.put("resourceId", "canvas");
////        ObjectNode stencilSetNode = objectMapper.createObjectNode();
////        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
////        editorNode.replace("stencilset", stencilSetNode);
////        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
////        return "redirect:/modeler.html?modelId=" + id;
////    }
////
////    /**
////     * 发布模型为流程定义
////     * @param id
////     * @return
////     * @throws Exception
////     */
////    @PostMapping("deploy/{id}")
////    @ResponseBody
////    public Result<Void> deploy(@PathVariable("id") String id) throws Exception
////    {
////        // 获取模型
////        Model modelData = repositoryService.getModel(id);
////        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
////        if (bytes == null)
////        {
////            return Result.failed("模型数据为空，请先设计流程并成功保存，再进行发布。");
////        }
////        JsonNode modelNode = new ObjectMapper().readTree(bytes);
////        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
////        if (model.getProcesses().size() == 0)
////        {
////            return Result.failed(("数据模型不符要求，请至少设计一条主线流程。");
////        }
////        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
////        // 发布流程
////        String processName = modelData.getName() + ".bpmn20.xml";
////        Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
////                .addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
////        modelData.setDeploymentId(deployment.getId());
////        repositoryService.saveModel(modelData);
////        return Result.success();
////    }
////
////    @GetMapping("get/{id}")
////    public Result get(@PathVariable("id") String id) {
////        Model model = repositoryService.createModelQuery().modelId(id).singleResult();
////        return Result.success(model);
////    }
////
////    @GetMapping("list")
////    @ResponseBody
////    public R getList(ActReModel actReModel)
////    {
////        startPage();
////        PageHelper.orderBy("create_time_ desc");
////        return result(modelService.selectActReModelList(actReModel));
////    }
////
////    @PostMapping("remove")
////    @ResponseBody
////    public R deleteOne(String ids)
////    {
////        String[] idsArr = ids.split(",");
////        for (String id : idsArr)
////        {
////            repositoryService.deleteModel(id);
////        }
////        return R.ok();
////    }
//
//    @Override
//    protected CommonService<ActReModel> getService() {
//        return modelService;
//    }
//
//}
