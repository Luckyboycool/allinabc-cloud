package com.allinabc.cloud.activiti.controller;

import com.allinabc.cloud.activiti.pojo.params.AssignParam;
import com.allinabc.cloud.activiti.pojo.params.AuditorParam;
import com.allinabc.cloud.activiti.pojo.params.DecisionParam;
import com.allinabc.cloud.activiti.pojo.vo.DecisionVO;
import com.allinabc.cloud.activiti.pojo.vo.NodeModel;
import com.allinabc.cloud.activiti.pojo.vo.ProcessIdModel;
import com.allinabc.cloud.activiti.service.IProcessService;
import com.allinabc.cloud.activiti.util.ActUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 流程管理
 *
 */
@Slf4j
@Api(tags = "流程定义接口")
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private IProcessService processService;

    @Autowired
    private ActUtils actUtils;

    @GetMapping("/id/{formtype}")
    @ApiOperation(value = "根据formType类型查询processId")
    public Result<ProcessIdModel> findProcessIdByFormType(@PathVariable("formtype") String formType){
        ProcessIdModel result = processService.findProcessIdByFormType(formType);
        return Result.success(result);
    }

    /**
     * 获取所有节点信息
     * @return
     */
    @GetMapping("/node/list")
    @ApiOperation(value = "获取所有用户节点信息")
    public Result<List<NodeModel>> findUserTaskNodes(@RequestParam("processDefinitionkey") String processDefinitionkey ){
        List<NodeModel> nodeModels = processService.findUserTaskNodes(processDefinitionkey);
        return Result.success(nodeModels);

    }

    /**
     * 配置节点审批人(只有Employee可以甚至节点审批人，custome可以发起流程，不可以设置流程审批人)
     * @return
     */
    @PostMapping("/node/auditor")
    @ApiOperation(value = "配置节点审批人")
    public Result<Void> saveOrUpdateNodeAuditor(@RequestBody AuditorParam auditorParam){
        processService.saveNodeAuditor(auditorParam);
        return Result.success("success");

    }


    /**
     * 获取流程节点可决策类型
     */
    @PostMapping("/node/decision")
    @ApiOperation(value = "获取流程节点可决策类型")
    public Result<List<DecisionVO>> findNodeDecision(@RequestBody DecisionParam decisionParam){
        List<DecisionVO> ls = processService.findNodeDecision(decisionParam);
        return Result.success(ls);
    }

    /**
     * 根据流程id获取流程审批记录流程图
     */
    @GetMapping("/image/{processinstid}")
    @ApiOperation(value = "根据流程id获取流程审批记录流程图")
    public Result<String> findNodeDecision(@PathVariable("processinstid")String processInstId){
        String flowImgByInstanceId = actUtils.getFlowImgByInstanceId(processInstId);
        return Result.success(flowImgByInstanceId,"success");
    }



}
