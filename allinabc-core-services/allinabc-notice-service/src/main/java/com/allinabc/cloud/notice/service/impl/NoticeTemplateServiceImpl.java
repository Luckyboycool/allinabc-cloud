package com.allinabc.cloud.notice.service.impl;

import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.utils.bean.BeanUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.notice.mapper.NoticeTemplateMapper;
import com.allinabc.cloud.notice.pojo.dto.ModifyNoticeTemplateDTO;
import com.allinabc.cloud.notice.pojo.dto.QueryNoticeTemplateDTO;
import com.allinabc.cloud.notice.pojo.po.NoticeTemplate;
import com.allinabc.cloud.notice.pojo.vo.NoticeTemplateVO;
import com.allinabc.cloud.notice.service.NoticeTemplateService;
import com.allinabc.cloud.starter.web.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-20
 */
@Service
@Slf4j
public class NoticeTemplateServiceImpl implements NoticeTemplateService {

    @Resource
    private NoticeTemplateMapper noticeTemplateMapper;

    @Override
    public Result<List<NoticeTemplateVO>> findTemplateList(QueryNoticeTemplateDTO noticeTemplateDTO) {
        List<NoticeTemplateVO> list = noticeTemplateMapper.selectTemplateList(noticeTemplateDTO);
        return Result.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> addTemplate(ModifyNoticeTemplateDTO noticeTemplateDTO) {
        Date now = new Date();
        User currentUser = CurrentUserUtil.getCurrentUser();
        NoticeTemplate nteNoticeTemplate = new NoticeTemplate(currentUser.getId(),now,currentUser.getId(),now);
        BeanUtils.copyBeanProp(nteNoticeTemplate,noticeTemplateDTO);
        int var1 = noticeTemplateMapper.insert(nteNoticeTemplate);
        if(var1!=1){
            log.error("新增模板失败");
            throw new RuntimeException("新增模板失败");
        }
        //返回ID到前端
        return Result.success(nteNoticeTemplate.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> modifyTemplate(ModifyNoticeTemplateDTO noticeTemplateDTO) {
        Date now = new Date();
        User currentUser = CurrentUserUtil.getCurrentUser();
        NoticeTemplate nteNoticeTemplate = new NoticeTemplate();
        BeanUtils.copyBeanProp(nteNoticeTemplate,noticeTemplateDTO);
        nteNoticeTemplate.setUpdatedBy(currentUser.getId());
        nteNoticeTemplate.setUpdateTm(now);
        int var1 = noticeTemplateMapper.updateById(nteNoticeTemplate);
        if(var1!=1){
            log.error("修改模板失败");
            throw new RuntimeException("修改模板失败");
        }
        return Result.success("修改成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> removeTemplate(String id) {
        Date now = new Date();
        User currentUser = CurrentUserUtil.getCurrentUser();
        NoticeTemplate nteNoticeTemplate = new NoticeTemplate();
        nteNoticeTemplate.setUpdatedBy(currentUser.getId());
        nteNoticeTemplate.setUpdateTm(now);
        nteNoticeTemplate.setIsAvailable("N");
        nteNoticeTemplate.setId(id);
        int var1 = noticeTemplateMapper.updateById(nteNoticeTemplate);
        if(var1!=1){
            log.error("删除模板失败");
            throw new RuntimeException("删除模板失败");
        }
        return Result.success("删除成功");
    }
}
