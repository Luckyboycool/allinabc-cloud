package com.allinabc.cloud.notice.mapper;

import com.allinabc.cloud.notice.pojo.po.NoticeParticipant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 通知参与表 Mapper 接口
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-07
 */
public interface NoticeParticipantMapper extends BaseMapper<NoticeParticipant> {

    /**
     * 批量插入数据
     */
    int insertList(@Param("ls") List<NoticeParticipant> ls);

    /**
     * 根据组Ids查询所有组内成员的邮箱
     */
    List<String> selectMailByGroupIds(@Param("ls") List<String> ls);

    /**
     * 根据userIds查询所有成员的邮箱
     */
    List<String> selectMailByUserIds(@Param("ls")List<String> userIdList);
}
