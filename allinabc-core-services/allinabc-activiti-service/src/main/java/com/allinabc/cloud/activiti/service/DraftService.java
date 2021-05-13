package com.allinabc.cloud.activiti.service;

import com.allinabc.cloud.activiti.pojo.dto.DraftParam;
import com.allinabc.cloud.activiti.pojo.vo.DraftVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/3 13:59
 **/
public interface DraftService {

    /**
     * 查询草稿状态表单
     * @param draftParam
     * @return
     */
    Page<DraftVO> findDraftList(DraftParam draftParam);
}
