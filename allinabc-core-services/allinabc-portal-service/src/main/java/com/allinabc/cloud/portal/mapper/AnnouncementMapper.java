package com.allinabc.cloud.portal.mapper;

import com.allinabc.cloud.portal.pojo.po.Announcement;
import com.allinabc.cloud.portal.pojo.vo.AnnouncementVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/25/21 5:04 PM
 **/
public interface AnnouncementMapper extends MybatisCommonBaseMapper<Announcement> {
    /**
     * 查询公告列表
     * @param page
     * @param title
     * @param type
     * @param timeType
     * @return
     */
    IPage<AnnouncementVO> findList(Page page, @Param("title") String title,
                                   @Param("type") String type, @Param("timeType") String timeType);

    /**
     * 查询公告列表(有效)
     * @param page
     * @param accountType
     * @return
     */
    IPage<AnnouncementVO> findVisibleList(Page page, @Param("accountType") String accountType);


    /**
     * 查询公告列表
     * @param receivePerson
     * @param num
     * @return
     */
    List<AnnouncementVO> findVisibleListByPerson(@Param("receivePerson") String receivePerson,
                                                 @Param("num") Integer num);

}
