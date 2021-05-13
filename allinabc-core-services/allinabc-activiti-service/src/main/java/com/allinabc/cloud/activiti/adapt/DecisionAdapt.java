package com.allinabc.cloud.activiti.adapt;

import com.allinabc.cloud.activiti.pojo.params.ProcessExecuteParam;
import com.allinabc.cloud.common.core.domain.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/4/7 17:59
 **/
public interface DecisionAdapt {

    List<String> complateTask(ProcessInstance processInstance, ProcessExecuteParam processExecuteParam, Task currentTask, User currentUser,String basicInfoId, Map<String, Object> variables);
}
