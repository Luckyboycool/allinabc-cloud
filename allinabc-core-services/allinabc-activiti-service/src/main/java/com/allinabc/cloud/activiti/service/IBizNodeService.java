//package com.allinabc.cloud.activiti.service;
//
//import com.allinabc.cloud.activiti.pojo.po.ActReProcdef;
//import com.allinabc.cloud.activiti.pojo.po.BizNode;
//import com.allinabc.cloud.activiti.pojo.vo.ProcessNodeVo;
//import com.allinabc.common.mybatis.service.MybatisCommonService;
//
//import java.util.List;
//import java.util.Set;
//
///**
// * 节点Service接口
// *
// * @author ruoyi
// * @date 2020-01-07
// */
//public interface IBizNodeService extends MybatisCommonService<BizNode> {
//
//    /**
//     * 设置节点视图
//     */
//    public ProcessNodeVo setAuditors(ProcessNodeVo node);
//
//    /**
//     * 更新节点配置
//     *
//     * @param node
//     * @return
//     * @author zmr
//     */
//    public int updateBizNode(ProcessNodeVo node);
//
//    /**
//     * 根据节点id获取所有审核人的编号
//     * @param nodeId 流程节点编号
//     * @param userId 当前用户编号
//     * @return
//     * @author zmr
//     */
//    public Set<String> getAuditors(String nodeId, String userId);
//}
