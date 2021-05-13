package com.allinabc.cloud.admin.service;

import com.allinabc.cloud.admin.pojo.po.OperLog;
import com.allinabc.cloud.common.web.pojo.param.QueryParam;
import com.allinabc.common.mybatis.service.MybatisCommonService;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2020-12-16
 */
public interface OperLogService extends MybatisCommonService<OperLog> {


    /**
     * 批量删除系统操作日志
     *
     * @param id 需要删除的数据
     * @return 结果
     */
    @Override
    void deleteById(String id);


    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    void deleteByIds(String[] ids);


    /**
     * 查询操作日志详细
     *
     * @param id 操作ID
     * @return 操作日志对象
     */
    @Override
    OperLog selectById(String id);


    /**
     * 清空操作日志
     */
    void cleanOperLog();



    /**
     * 在检索之前执行，返回false终止操作
     * @param param 查询参数
     * @return 前置处理结果(false会停止继续执行)
     */
    boolean beforeFindPage(QueryParam param);

}
