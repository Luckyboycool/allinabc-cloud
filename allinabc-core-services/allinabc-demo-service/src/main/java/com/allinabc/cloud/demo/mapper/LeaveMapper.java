package com.allinabc.cloud.demo.mapper;

import com.allinabc.cloud.demo.pojo.po.Leave;
import com.allinabc.cloud.demo.pojo.vo.LeaveVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/18 22:02
 **/
public interface LeaveMapper extends MybatisCommonBaseMapper<Leave> {

    LeaveVO selectLeaveById(String pid);
}
