package com.allinabc.cloud.admin.api.service.api;

import com.allinabc.cloud.admin.pojo.vo.CidbBasicSimpleVO;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/23 10:42 上午
 **/
public interface ApiCidbService {

    /**
     * 查询部分客户列表
     * @param custCodes
     * @return
     */
    List<CidbBasicSimpleVO> findListByCustCodes(List<String> custCodes);
}
