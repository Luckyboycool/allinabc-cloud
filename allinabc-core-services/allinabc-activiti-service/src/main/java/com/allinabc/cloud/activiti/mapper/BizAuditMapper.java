package com.allinabc.cloud.activiti.mapper;

import com.allinabc.cloud.activiti.pojo.po.BizAudit;
import com.allinabc.cloud.activiti.pojo.vo.BizAuditVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BizAuditMapper extends MybatisCommonBaseMapper<BizAudit> {

    List<BizAudit> selectAuditorByProcessinsId(@Param("processInsId") String processInsId, @Param("processDefinitionKey")String processDefinitionKey, @Param("auditNodeKey")String auditNodeKey);

    List<BizAuditVO> selectTaskCommentByProcessId(@Param("processInstId") String processInstId);

    /**
     * 根据实例Id和businessId查询审核记录
     * @param basicInfoId
     * @param processInstId
     * @return
     */
    List<BizAudit> selectBizAuditByBasicInfoIdAndProcessInstId(@Param("basicInfoId") String basicInfoId, @Param("processInstId") String processInstId);
}
