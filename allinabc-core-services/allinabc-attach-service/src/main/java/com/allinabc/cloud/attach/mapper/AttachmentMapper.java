package com.allinabc.cloud.attach.mapper;


import com.allinabc.cloud.attach.pojo.dto.AttachmentInfoDTO;
import com.allinabc.cloud.attach.pojo.dto.AttachmentParam;
import com.allinabc.cloud.attach.pojo.dto.AttachmentQueryParam;
import com.allinabc.cloud.attach.pojo.po.Attachment;
import com.allinabc.cloud.attach.pojo.vo.AttachmentInfoResponse;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 附件表 Mapper 接口
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-11-16
 */
public interface AttachmentMapper extends MybatisCommonBaseMapper<Attachment> {

    /**
     * 根据AttachmentId查询附件类型和附件基本信息
     */
    AttachmentInfoDTO selectAttachmentInfoById(@Param("attachmentId") String attachmentId);

    /**
     * 据传入的bizType以及bizId查询附件信息
     */
    List<AttachmentInfoResponse> selectAttachmentInfoByBizIdAndBizType(AttachmentQueryParam attachmentQueryParam);

    /**
     * 删除附件(逻辑删除)
     */
    int deleteAttachmentById(@Param("id") String attachmentId);

    /**
     * 根据bizId查询附件信息
     */
    /*List<AttachmentInfoDTO> selectAttachmentInfoByBizId(@Param("bizId") String bizId);*/

    /**
     * 根据bizId查询附件信息
     */
    List<Attachment> selectAttachmentInfoByBizId(@Param("bizId") String bizId);

    /**
     * 根据bizId删除附件信息
     */
    int deleteAttachmentByBizId(@Param("bizId") String useCaseId);

    /**
     * 批量更新bizId
     */
    int updateBizIds(@Param("ls") List<AttachmentParam> attachmentParams);
}
