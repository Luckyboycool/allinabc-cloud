package com.allinabc.cloud.notice.mapper;

import com.allinabc.cloud.notice.pojo.dto.QueryNoticeTemplateDTO;
import com.allinabc.cloud.notice.pojo.po.NoticeTemplate;
import com.allinabc.cloud.notice.pojo.vo.NoticeTemplateVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-27
 */
public interface NoticeTemplateMapper extends MybatisCommonBaseMapper<NoticeTemplate> {

    /**
     * 查询模板列表
     * @param noticeTemplateDTO
     * @return
     */
    List<NoticeTemplateVO> selectTemplateList(QueryNoticeTemplateDTO noticeTemplateDTO);
}
