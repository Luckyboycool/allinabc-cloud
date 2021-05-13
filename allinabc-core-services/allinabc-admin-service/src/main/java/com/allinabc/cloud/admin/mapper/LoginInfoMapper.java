package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.po.LoginInfo;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-17
 */
public interface LoginInfoMapper extends MybatisCommonBaseMapper<LoginInfo> {

    void cleanLoginInfo();

    /**
     * 查看登录日志列表
     * @param page
     * @param username
     * @return
     */
    IPage<LoginInfo> pageList(Page<LoginInfo> page, @Param("username") String username);


    /**
     * 查询当前用户登录成功后的上一次记录
     * @param userId
     * @param userType
     * @return
     */
    LoginInfo getLastLoginInfo(@Param("userId") String userId,
                               @Param("userType") String userType);

    /**
     * 查询当前用户上次登录信息
     * @param userId
     * @param userType
     * @return
     */
    LoginInfo lastSuccessInfo(@Param("userId") String userId, @Param("userType") String userType);

    /**
     * 更新当前日志 的退出时间
     * @param id
     */
    void updateLogoutTime(@Param("id") String id);
}
