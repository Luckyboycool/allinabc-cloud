package com.allinabc.cloud.admin.service;


import com.allinabc.cloud.admin.pojo.dto.CidbUpdateDTO;
import com.allinabc.cloud.admin.pojo.dto.QueryCidbParam;
import com.allinabc.cloud.admin.pojo.vo.CidbBasicInfoVo;
import com.allinabc.cloud.admin.pojo.vo.CidbBasicSimpleVO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CidbBasicInfoService {

    IPage<CidbBasicInfoVo> findList(QueryCidbParam queryCidbParam, int pageNo, int pageSize);

    /**
     * 查询客户列表(未分页)
     * @param code 客户三位代码
     * @param name 客户名称
     * @return
     */
    List<CidbBasicSimpleVO> findAll(String code, String name);

    /**
     * 查询客户信息
     * @param custCode 客户三位代码
     * @return
     */
    Result<CidbBasicSimpleVO> getByCustCode(String custCode);

    /**
     * 查询客户列表
     * @param custCodes 客户三位代码List
     * @return
     */
    Result<List<CidbBasicSimpleVO>> findListByCustCodes(List<String> custCodes);

    /**
     * 更新CIDB
     * @param cidbUpdateDTO
     * @return
     */
    Result update(CidbUpdateDTO cidbUpdateDTO);
}
