package com.allinabc.cloud.demo.service;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.demo.pojo.po.Leave;
import com.allinabc.cloud.starter.form.param.BasicFormParam;
import com.allinabc.common.mybatis.service.MybatisCommonService;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/18 21:54
 **/
public interface LeaveService extends MybatisCommonService<Leave> {

    Result<Void> add(BasicFormParam basicFormParam);

//    /**
//     * 新增
//     * @param request
//     * @return
//     */
//    Result<?> add(Request<LeaveDTO> request);
//
//    /**
//     * 根据ID查询请假记录
//     * @param request
//     * @return
//     */
//    Result<LeaveVO> findByID(Request<LeaveDTO> request);
//
//    /**
//     * 修改请假申请
//     * @param request
//     * @return
//     */
//    Result<Void> modify(Request<LeaveDTO> request);
//
//    /**
//     * 删除请假申请
//     * @param request
//     * @return
//     */
//    Result<Void> delete(Request<LeaveDTO> request);
}
