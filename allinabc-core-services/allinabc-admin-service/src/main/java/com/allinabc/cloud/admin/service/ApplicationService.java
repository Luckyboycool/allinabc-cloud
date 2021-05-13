package com.allinabc.cloud.admin.service;

import com.allinabc.cloud.admin.pojo.po.Application;
import com.allinabc.cloud.admin.pojo.vo.AppPathVO;
import com.allinabc.cloud.admin.pojo.vo.ApplicationVO;
import com.allinabc.common.mybatis.service.MybatisCommonService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangtaifeng
 * @since 2020-12-14
 */
public interface ApplicationService extends MybatisCommonService<Application> {

    /**
     * 根据 appCode 查询 icon，admin ，image path 信息
     * @param
     * @return
     */
    List<ApplicationVO> findAppCode ();

    /**
     * 获取 系统路径
     * @return
     */
    List<AppPathVO>  findAppPaht();

}
