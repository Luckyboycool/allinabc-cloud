package com.allinabc.cloud.portal.service;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.portal.pojo.dto.AnnouncementInsertDTO;
import com.allinabc.cloud.portal.pojo.dto.AnnouncementPageDto;
import com.allinabc.cloud.portal.pojo.dto.AnnouncementUpdateDTO;
import com.allinabc.cloud.portal.pojo.po.Announcement;
import com.allinabc.cloud.portal.pojo.vo.AnnouncementDetailVO;
import com.allinabc.cloud.portal.pojo.vo.AnnouncementVO;
import com.allinabc.common.mybatis.service.MybatisCommonService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/25/21 5:03 PM
 **/
public interface AnnouncementService extends MybatisCommonService<Announcement> {

    /**
     * 插入公告
     * @param insertDTO
     * @return
     */
    Result<Void> insert(AnnouncementInsertDTO insertDTO);

    /**
     * 更新公告
     * @param updateDTO
     * @return
     */
    Result<Void> update(AnnouncementUpdateDTO updateDTO);

    /**
     * 查询公告信息
     * @param id
     * @return
     */
    Result<AnnouncementDetailVO> getById(String id);

    /**
     * 查询公告列表
     * @param announcementPageDto
     * @return
     */
    IPage<AnnouncementVO> findList(AnnouncementPageDto announcementPageDto);

    /**
     * 查询有效的公告列表
     * @param announcementPageDto
     * @return
     */
    IPage<AnnouncementVO> findVisibleList(AnnouncementPageDto announcementPageDto);
    /**
     * 前几条公告
     * @param num 几条
     * @return
     */
    Result<List<AnnouncementVO>> limitInfoByNum(Integer num);
}
