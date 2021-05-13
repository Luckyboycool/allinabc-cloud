package com.allinabc.cloud.admin.service;

import com.allinabc.cloud.admin.pojo.dto.DldbSendDTO;
import com.allinabc.cloud.admin.pojo.dto.DldbUpdateDTO;
import com.allinabc.cloud.admin.pojo.vo.DldbSingleVO;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author Simon.Xue
 * @date 2021/4/6 2:33 下午
 **/
public interface DldbInfoService {
    /**
     * 查询产品信息列表
     * @param pageNum
     * @param pageSize
     * @param deviceName
     * @param productId
     * @return
     */
    IPage findList(Integer pageNum, Integer pageSize, String deviceName, String productId);

    /**
     * 更新产品信息维护信息
     * @param dldbUpdateDTO
     * @return
     */
    Result update(DldbUpdateDTO dldbUpdateDTO);

    /**
     * 发送通知邮件
     * @param dldbSendDTO
     * @return
     */
    Result sendNoticeMail(DldbSendDTO dldbSendDTO);

    /**
     * 查询产品信息详情
     * @param id
     * @return
     */
    Result<DldbSingleVO> getById(String id);
}
