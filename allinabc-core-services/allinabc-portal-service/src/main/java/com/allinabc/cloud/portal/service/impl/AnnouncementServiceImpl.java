package com.allinabc.cloud.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.enums.AnnounceEnums;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.portal.mapper.AnnouncementMapper;
import com.allinabc.cloud.portal.pojo.dto.AnnouncementInsertDTO;
import com.allinabc.cloud.portal.pojo.dto.AnnouncementPageDto;
import com.allinabc.cloud.portal.pojo.dto.AnnouncementUpdateDTO;
import com.allinabc.cloud.portal.pojo.po.Announcement;
import com.allinabc.cloud.portal.pojo.vo.AnnouncementDetailVO;
import com.allinabc.cloud.portal.pojo.vo.AnnouncementVO;
import com.allinabc.cloud.portal.service.AnnouncementService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/25/21 5:04 PM
 **/
@Slf4j
@Service
public class AnnouncementServiceImpl extends MybatisCommonServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    @Override
    protected MybatisCommonBaseMapper<Announcement> getRepository() {
        return announcementMapper;
    }

    @Override
    public Result<Void> insert(AnnouncementInsertDTO insertDTO) {
        String timeType = insertDTO.getTimeType();
        this.checkIsExist(insertDTO.getType(), timeType, insertDTO.getReceivePerson());
        this.checkSendDate(timeType, insertDTO.getSendDate());

        Announcement announcement = new Announcement();
        BeanUtil.copyProperties(insertDTO, announcement);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("TITLE", insertDTO.getTitle());
        Announcement announ = announcementMapper.selectOne(wrapper);
        if (null != announ) {
            log.warn("公告标题不能重复, title = {}", insertDTO.getTitle());
            return Result.failed("公告标题不能重复");
        }
        announcement.setId(IdUtil.getSnowflake(0, 0).nextIdStr());
        announcement.setCreatedBy(getCurrentUserId());
        announcement.setCreateTm(new Date());
        announcementMapper.insert(announcement);
        return Result.success("添加成功");
    }

    /**
     * 处理类型统一校验
     * @param type 公告类型
     * @param timeType 发布类型
     * @param receivePerson 发布对象类型
     */
    private void checkIsExist(String type, String timeType, String receivePerson) {
        Assert.isTrue(AnnounceEnums.TYPE.isExist(type), "公告类型不存在");
        Assert.isTrue(AnnounceEnums.TIME_TYPE.isExist(timeType), "发布类型不存在");
        Assert.isTrue(AnnounceEnums.RECEIVE_PERSON.isExist(receivePerson), "发布对象类型不存在");
    }

    /**
     *
     * @param timeType
     * @param sendDate
     */
    private void checkSendDate(String timeType, Date sendDate) {
        if (AnnounceEnums.TIME_TYPE.TIMING.getCode().equals(timeType)) {
            Assert.isTrue(null != sendDate, "公告定时需要填写发布日期");
        }
    }

    @Override
    public Result<Void> update(AnnouncementUpdateDTO updateDTO) {
        String timeType = updateDTO.getTimeType();
        this.checkIsExist(updateDTO.getType(), timeType, updateDTO.getReceivePerson());
        this.checkSendDate(timeType, updateDTO.getSendDate());

        Announcement announcement = new Announcement();
        BeanUtil.copyProperties(updateDTO, announcement);
        Announcement announce = announcementMapper.selectById(announcement.getId());
        if (null == announce) {
            log.error("公告信息不存在，id = {}", announcement.getId());
            return Result.failed("公告信息不存在");
        }

        QueryWrapper<Announcement> wrapper = new QueryWrapper<>();
        wrapper.eq("TITLE", updateDTO.getTitle());
        Announcement announcementRes = announcementMapper.selectOne(wrapper);
        if (announcementRes != null
                && !announcement.getId().equals(announcementRes.getId())) {
            log.warn("公告标题已存在, title = {}", updateDTO.getTitle());
            return Result.failed("公告标题已存在");
        }
        announcement.setUpdatedBy(getCurrentUserId());
        announcement.setUpdateTm(new Date());
        announcementMapper.updateById(announcement);
        return Result.success("更新成功");
    }

    @Override
    public Result<AnnouncementDetailVO> getById(String id) {
        Announcement announcement = announcementMapper.selectById(id);
        AnnouncementDetailVO announcementDetailVO = new AnnouncementDetailVO();
        BeanUtil.copyProperties(announcement, announcementDetailVO);
        return Result.success(announcementDetailVO, "查询成功");
    }

    @Override
    public IPage<AnnouncementVO> findList(AnnouncementPageDto pageDto) {
        Page<AnnouncementVO> page = new Page<>();
        page.setCurrent(pageDto.getPageNum());
        page.setSize(pageDto.getPageSize());
        return announcementMapper.findList(page, pageDto.getTitle(), pageDto.getType(), pageDto.getTimeType());
    }

    @Override
    public IPage<AnnouncementVO> findVisibleList(AnnouncementPageDto pageDto) {
        Page<AnnouncementVO> page = new Page<>();
        page.setCurrent(pageDto.getPageNum());
        page.setSize(pageDto.getPageSize());
        User currentUser = getCurrentUser();
        String accountType = currentUser.getAccountType();
        return announcementMapper.findVisibleList(page, accountType);
    }

    @Override
    public Result<List<AnnouncementVO>> limitInfoByNum(Integer num) {
        User currentUser = getCurrentUser();
        String receivePerson = currentUser.getAccountType();
        List<AnnouncementVO> visibleList = announcementMapper.findVisibleListByPerson(receivePerson, num);
        return Result.success(visibleList, "查询成功");
    }
}
