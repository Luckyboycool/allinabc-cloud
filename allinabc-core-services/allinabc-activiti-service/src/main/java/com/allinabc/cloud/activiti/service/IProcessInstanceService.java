package com.allinabc.cloud.activiti.service;

import com.allinabc.cloud.activiti.pojo.dto.TaskParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessStartParam;
import com.allinabc.cloud.activiti.pojo.vo.TaskModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface IProcessInstanceService {

    /**
     * 启动一个流程实例
     */
    TaskModel startProcess(ProcessStartParam processStartParam);

    /**
     * 执行流程

     */
    List<String> executeProcess(ProcessExecuteParam processExecuteParam);


    /**
     * 根据instanceID，查询taskID
     * @param instanceId
     * @return
     */
    List<String> findTaskIdByInstanceId(String instanceId);

    /**
     * 根据basicInfoId获取流程节点信息
     * @param basicInfoId
     * @return
     */
    List<TaskModel> findBizNodeInfoByBizId(String basicInfoId);

    /**
     * 查询代办信息
     * @param taskParam
     * @return
     */
//    Page<TaskModel> findTaskIng(TaskParam taskParam);

    /**
     * 查询已经办理的任务
     * @param taskParam
     * @return
     */
//    Page<TaskModel> findTaskDone(TaskParam taskParam);

    /**
     * 通过processId查询流程流转历史
     * @param taskParam
     * @return
     */
    List<TaskModel> findTaskFlow(TaskParam taskParam);

//    /**
//     * 通过processId查询流程流转历史
//     * @param taskParam
//     * @return
//     */
//    List<TaskModel> findTaskFlow2(TaskParam taskParam);

    /**
     * 通过任务Id，查询节点信息
     * @param taskParam
     * @return
     */
    TaskModel findBizNodeInfoByTaskId(TaskParam taskParam);

    /**
     * 取消流程
     * @param taskParam
     */
   // void cancleTask(TaskParam taskParam);

    /**
     * 转办
     * @param taskParam
     */
   // void transferTask(TaskParam taskParam);

    /**
     * 加签(委托)
     * @param taskParam
     */
   // void delegateTask(TaskParam taskParam);

    /**
     * 模糊查询待办列表分页
     * @param taskParam
     * @return
     */
    Page<TaskModel> findTaskIngSearch(TaskParam taskParam);

    /**
     * 模糊查询已办分页
     * @param taskParam
     * @return
     */
    Page<TaskModel> findTaskDoneSearch(TaskParam taskParam);
}
