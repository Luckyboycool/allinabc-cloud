package com.allinabc.cloud.activiti.service;

public interface GtaRequestNoService {
    /**
     * 获取申请单编号
     * @param pre 模块前缀（例：Device-20200305）
     * @param length 流水码位数（例：3位流水码，length=3）
     * @return
     */
    String getRequestNo(String pre,Integer length);
}
