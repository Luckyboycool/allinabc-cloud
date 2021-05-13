
package com.allinabc.cloud.activiti.util;

import cn.hutool.core.codec.Base64;
import com.allinabc.cloud.activiti.cover.CustomProcessDiagramGenerator;
import com.allinabc.cloud.activiti.pojo.consts.ActivitiConstant;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 流程工具类
 *
 */
@Component
@Slf4j
public class ActUtils {
	

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;

	/**
	 * 根据流程实例Id,获取实时流程图片
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public  String getFlowImgByInstanceId(String processInstanceId) {
		byte[] data = null;
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		try {
			if (StringUtils.isEmpty(processInstanceId)) {
				log.error("processInstanceId is null");
				return "processInstanceId is null";
			}
			// 获取历史流程实例
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			// 获取流程中已经执行的节点，按照执行先后顺序排序
			List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
					.orderByHistoricActivityInstanceId().asc().list();
			// 高亮已经执行流程节点ID集合
			List<String> highLightedActivitiIds = new ArrayList<>();
			for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
				highLightedActivitiIds.add(historicActivityInstance.getActivityId());
			}

			//List<HistoricProcessInstance> historicFinishedProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).finished().list();
			CustomProcessDiagramGenerator customProcessDiagramGenerator  = new CustomProcessDiagramGenerator();;


			BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
			// 高亮流程已发生流转的线id集合
			Set<String> currIds = runtimeService.createExecutionQuery().processInstanceId(historicProcessInstance.getId()).list().stream().map(e -> e.getActivityId()).collect(Collectors.toSet());
			List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstances);
			// 使用默认配置获得流程图表生成器，并生成追踪图片字符流
			InputStream imageStream = customProcessDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitiIds, highLightedFlowIds, "宋体", "宋体", "宋体", null, 1.0, new Color[]{ActivitiConstant.COLOR_NORMAL, ActivitiConstant.COLOR_CURRENT}, currIds);
			swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = imageStream.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			data = swapStream.toByteArray();
			log.info("转成base64编码格式。。。。。。。。");
			return Base64.encode(data);
		} catch (Exception e) {
			log.error("processInstanceId" + processInstanceId + "生成流程图失败，原因：" + e.getMessage(), e);
			return "error";
		}finally {
			if(swapStream!=null){
				try {
					swapStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
	}


	/**
	 * 获取已流经的流程线，需要高亮显示高亮流程已发生流转的线id集合
	 *
	 * @param bpmnModel
	 * @param historicActivityInstanceList
	 * @return
	 */
	public List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstanceList) {
		// 已流经的流程线，需要高亮显示
		List<String> highLightedFlowIdList = new ArrayList<>();
		// 全部活动节点
		List<FlowNode> allHistoricActivityNodeList = new ArrayList<>();
		// 已完成的历史活动节点
		List<HistoricActivityInstance> finishedActivityInstanceList = new ArrayList<>();

		for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
			// 获取流程节点
			FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance
					.getActivityId(), true);
			allHistoricActivityNodeList.add(flowNode);
			// 结束时间不为空，当前节点则已经完成
			if (historicActivityInstance.getEndTime() != null) {
				finishedActivityInstanceList.add(historicActivityInstance);
			}
		}

		FlowNode currentFlowNode = null;
		FlowNode targetFlowNode = null;
		HistoricActivityInstance currentActivityInstance;
		// 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
		for (int k = 0; k < finishedActivityInstanceList.size(); k++) {
			currentActivityInstance = finishedActivityInstanceList.get(k);
			// 获得当前活动对应的节点信息及outgoingFlows信息
			currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance
					.getActivityId(), true);
			// 当前节点的所有流出线
			List<SequenceFlow> outgoingFlowList = currentFlowNode.getOutgoingFlows();

			/**
			 * 遍历outgoingFlows并找到已流转的 满足如下条件认为已流转：
			 * 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
			 * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
			 * (第2点有问题，有过驳回的，会只绘制驳回的流程线，通过走向下一级的流程线没有高亮显示)
			 */
			if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(
					currentActivityInstance.getActivityType())) {
				// 遍历历史活动节点，找到匹配流程目标节点的
				for (SequenceFlow outgoingFlow : outgoingFlowList) {
					// 获取当前节点流程线对应的下级节点
					targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(outgoingFlow.getTargetRef(),
							true);
					// 如果下级节点包含在所有历史节点中，则将当前节点的流出线高亮显示
					if (allHistoricActivityNodeList.contains(targetFlowNode)) {
						highLightedFlowIdList.add(outgoingFlow.getId());
					}
				}
			} else {
				/**
				 * 2、当前节点不是并行网关或兼容网关
				 * 【已解决-问题】如果当前节点有驳回功能，驳回到申请节点，
				 * 则因为申请节点在历史节点中，导致当前节点驳回到申请节点的流程线被高亮显示，但实际并没有进行驳回操作
				 */
				// 当前节点ID
				String currentActivityId = currentActivityInstance.getActivityId();
				int size = historicActivityInstanceList.size();
				boolean ifStartFind = false;
				boolean ifFinded = false;
				HistoricActivityInstance historicActivityInstance;
				// 循环当前节点的所有流出线
				// 循环所有历史节点
				log.info("【开始】-匹配当前节点-ActivityId=【{}】需要高亮显示的流出线", currentActivityId);
				log.info("循环历史节点");
				for (int i = 0; i < historicActivityInstanceList.size(); i++) {
					// // 如果当前节点流程线对应的下级节点在历史节点中，则该条流程线进行高亮显示（【问题】有驳回流程线时，即使没有进行驳回操作，因为申请节点在历史节点中，也会将驳回流程线高亮显示-_-||）
					// if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
					// Map<String, Object> map = new HashMap<>();
					// map.put("highLightedFlowId", sequenceFlow.getId());
					// map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
					// tempMapList.add(map);
					// // highLightedFlowIdList.add(sequenceFlow.getId());
					// }
					// 历史节点
					historicActivityInstance = historicActivityInstanceList.get(i);
					log.info("第【{}/{}】个历史节点-ActivityId=[{}]", i + 1, size, historicActivityInstance.getActivityId());
					// 如果循环历史节点中的id等于当前节点id，从当前历史节点继续先后查找是否有当前节点流程线等于的节点
					// 历史节点的序号需要大于等于已完成历史节点的序号，防止驳回重审一个节点经过两次是只取第一次的流出线高亮显示，第二次的不显示
					if (i >= k && historicActivityInstance.getActivityId().equals(currentActivityId)) {
						log.info("第[{}]个历史节点和当前节点一致-ActivityId=[{}]", i + 1, historicActivityInstance
								.getActivityId());
						ifStartFind = true;
						// 跳过当前节点继续查找下一个节点
						continue;
					}
					if (ifStartFind) {
						log.info("[开始]-循环当前节点-ActivityId=【{}】的所有流出线", currentActivityId);

						ifFinded = false;
						for (SequenceFlow sequenceFlow : outgoingFlowList) {
							// 如果当前节点流程线对应的下级节点在其后面的历史节点中，则该条流程线进行高亮显示
							// 【问题】
							log.info("当前流出线的下级节点=[{}]", sequenceFlow.getTargetRef());
							if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
								log.info("当前节点[{}]需高亮显示的流出线=[{}]", currentActivityId, sequenceFlow.getId());
								highLightedFlowIdList.add(sequenceFlow.getId());
								// 暂时默认找到离当前节点最近的下一级节点即退出循环，否则有多条流出线时将全部被高亮显示
								ifFinded = true;
								break;
							}
						}
						log.info("[完成]-循环当前节点-ActivityId=【{}】的所有流出线", currentActivityId);
					}
					if (ifFinded) {
						// 暂时默认找到离当前节点最近的下一级节点即退出历史节点循环，否则有多条流出线时将全部被高亮显示
						break;
					}
				}
				log.info("【完成】-匹配当前节点-ActivityId=【{}】需要高亮显示的流出线", currentActivityId);
			}

		}
		return highLightedFlowIdList;
	}



//	public void getFlowImgByInstanceId2(String processInstanceId, OutputStream outputStream) {
//		try {
//			if (StringUtils.isEmpty(processInstanceId)) {
//				log.error("processInstanceId is null");
//				return ;
//			}
//			// 获取历史流程实例
//			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//			// 获取流程中已经执行的节点，按照执行先后顺序排序
//			List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
//					.orderByHistoricActivityInstanceId().asc().list();
//			// 高亮已经执行流程节点ID集合
//			List<String> highLightedActivitiIds = new ArrayList<>();
//			for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
//				highLightedActivitiIds.add(historicActivityInstance.getActivityId());
//			}
//
//			CustomProcessDiagramGenerator customProcessDiagramGenerator  = new CustomProcessDiagramGenerator();;
//
//
//			BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
//			// 高亮流程已发生流转的线id集合
//			Set<String> currIds = runtimeService.createExecutionQuery().processInstanceId(historicProcessInstance.getId()).list().stream().map(e -> e.getActivityId()).collect(Collectors.toSet());
//			List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstances);
//			// 使用默认配置获得流程图表生成器，并生成追踪图片字符流
//			InputStream imageStream = customProcessDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitiIds, highLightedFlowIds, "宋体", "宋体", "宋体", null, 1.0, new Color[]{ActivitiConstant.COLOR_NORMAL, ActivitiConstant.COLOR_CURRENT}, currIds);
//			// 输出图片内容
//			byte[] b = new byte[1024];
//			int len;
//			while ((len = imageStream.read(b, 0, 1024)) != -1) {
//				outputStream.write(b, 0, len);
//			}
//		} catch (Exception e) {
//			log.error("processInstanceId" + processInstanceId + "生成流程图失败，原因：" + e.getMessage(), e);
//		}finally {
//			if(outputStream!=null){
//				try {
//					outputStream.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//		}
//	}
}
