package com.allinabc.cloud.activiti.api.service.api;

import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.activiti.pojo.params.ProcessStartParam;
import com.allinabc.cloud.activiti.pojo.vo.ProcessIdModel;
import com.allinabc.cloud.activiti.pojo.vo.TaskModel;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/25 16:54
 **/
public interface ApiProcessService {

   /**
    * 启动流程实例
    * @return
    */
   TaskModel startProcess(ProcessStartParam processStartParam);


   /**
    * 执行一个流程（任务）
    * @return
    */
   List<String> executeProcess(ProcessExecuteParam processExecuteParam);

   /**
    * 根据formType查询ProcessId
    * @param formType
    * @return
    */
   ProcessIdModel findProcessIdByFormType(String formType);


   /**
    * 获取流程审批记录 历史表
    */
   TaskModel getBizNodeInfoByBizId(String basicInfoId);
}
