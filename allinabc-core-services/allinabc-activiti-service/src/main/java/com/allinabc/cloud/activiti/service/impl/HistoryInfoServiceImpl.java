//package com.allinabc.cloud.activiti.service.impl;
//
//import com.allinabc.cloud.activiti.mapper.HistoryMapper;
//import com.allinabc.cloud.activiti.pojo.vo.HiProcInsVo;
//import com.allinabc.cloud.activiti.service.IHistoryInfoService;
//import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
//import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// *
// *
// * @Auther: Ace Lee
// * @Date: 2019/3/7 16:55
// */
//@Service
//public class HistoryInfoServiceImpl extends MybatisCommonServiceImpl<HiProcInsVo> implements IHistoryInfoService {
//
//    @Autowired
//    private HistoryMapper historyMapper;
//
//    @Override
//    public List<HiProcInsVo> getHiProcInsListDone(HiProcInsVo hiProcInsVo) {
//        return historyMapper.getHiProcInsListDone(hiProcInsVo);
//    }
//
//    @Override
//    protected MybatisCommonBaseMapper<HiProcInsVo> getRepository() {
//        return historyMapper;
//    }
//
//}
