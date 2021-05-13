package com.allinabc.cloud.notice.service;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.notice.pojo.dto.ModifyNoticeTemplateDTO;
import com.allinabc.cloud.notice.pojo.dto.QueryNoticeTemplateDTO;
import com.allinabc.cloud.notice.pojo.po.NoticeTemplate;
import com.allinabc.cloud.notice.pojo.vo.NoticeTemplateVO;
import com.allinabc.common.mybatis.service.MybatisCommonService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangtaifeng
 * @since 2021-01-20
 */
public interface NoticeTemplateService {

    /**
     * 模板列表查询
     * @param noticeTemplateDTO
     * @return
     */
    Result<List<NoticeTemplateVO>> findTemplateList(QueryNoticeTemplateDTO noticeTemplateDTO);

    /**
     * 新增模板
     * @param noticeTemplateDTO
     * @return
     */
    Result<String> addTemplate(ModifyNoticeTemplateDTO noticeTemplateDTO);

    /**
     * 模板修改
     * @param noticeTemplateDTO
     * @return
     */
    Result<String> modifyTemplate(ModifyNoticeTemplateDTO noticeTemplateDTO);

    /**
     * 模板删除
     * @param id
     * @return
     */
    Result<String> removeTemplate(String id);
}
