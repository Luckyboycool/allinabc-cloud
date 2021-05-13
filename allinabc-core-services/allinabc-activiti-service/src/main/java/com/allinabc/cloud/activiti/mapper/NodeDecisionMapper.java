package com.allinabc.cloud.activiti.mapper;

import com.allinabc.cloud.activiti.pojo.params.DecisionParam;
import com.allinabc.cloud.activiti.pojo.po.NodeDecision;
import com.allinabc.cloud.activiti.pojo.vo.DecisionVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/8 15:34
 **/
public interface NodeDecisionMapper extends MybatisCommonBaseMapper<NodeDecision> {

    /**
     * 流程节点
     * @param decisionParam
     * @return
     */
    List<DecisionVO> selectNodeDecison(DecisionParam decisionParam);

    DecisionVO selectDecisionNameBykey(@Param("actionKey") String actionKey, @Param("nodeKey") String nodeKey);
}
