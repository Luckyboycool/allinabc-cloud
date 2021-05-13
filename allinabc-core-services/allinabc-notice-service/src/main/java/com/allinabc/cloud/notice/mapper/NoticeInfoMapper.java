package com.allinabc.cloud.notice.mapper;

import com.allinabc.cloud.notice.pojo.dto.NoticeInfoModel;
import com.allinabc.cloud.notice.pojo.po.NoticeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 通知信息内容表 Mapper 接口
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-07
 */
public interface NoticeInfoMapper extends BaseMapper<NoticeInfo> {

    /**
     * 更新邮件发送的状态
     */
    int updateStatusById(@Param("id") String noticeId, @Param("status") String status);

    /**
     * 更新重试次数
     */
    int updateRetryCountById(@Param("id") String noticeId, @Param("retryCount") int retryCount);

    /**
     * 根据查询的类型查出所有定时任务
     */
    List<NoticeInfoModel> selectNoticeInfo(@Param("sendType") String sendType);
}
