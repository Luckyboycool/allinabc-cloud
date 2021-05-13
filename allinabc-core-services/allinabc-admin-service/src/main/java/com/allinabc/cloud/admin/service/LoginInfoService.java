package com.allinabc.cloud.admin.service;

import com.allinabc.cloud.admin.pojo.dto.LoginInfoSearchDTO;
import com.allinabc.cloud.admin.pojo.po.LoginInfo;
import com.allinabc.cloud.common.web.pojo.param.QueryParam;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.service.MybatisCommonService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2020-12-17
 */
public interface LoginInfoService extends MybatisCommonService<LoginInfo> {


    void cleanLoginInfo();

    /**
     * 在检索之前执行，返回false终止操作
     * @param param 查询参数
     * @return 前置处理结果(false会停止继续执行)
     */
    boolean beforeFindPage(QueryParam param);

    /**
     * 查询所有日志
     * @param searchDTO
     * @return
     */
    IPage<LoginInfo> pageList(LoginInfoSearchDTO searchDTO);


    /**
     * 查询当前用户登录成功后的上一次记录
     * @return
     */
    Result<LoginInfo> getLastByUsername();
}
