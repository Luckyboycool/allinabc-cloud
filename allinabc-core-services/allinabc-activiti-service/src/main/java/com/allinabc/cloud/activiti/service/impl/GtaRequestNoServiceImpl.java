package com.allinabc.cloud.activiti.service.impl;

import cn.hutool.core.util.IdUtil;
import com.allinabc.cloud.activiti.mapper.GtaRequestNoMapper;
import com.allinabc.cloud.activiti.pojo.po.GtaRequestNo;
import com.allinabc.cloud.activiti.service.GtaRequestNoService;
import com.allinabc.cloud.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GtaRequestNoServiceImpl implements GtaRequestNoService {
    @Autowired
    private GtaRequestNoMapper gtaRequestNoMapper;

    @Override
    public synchronized String  getRequestNo(String key, Integer length) {
        String value = gtaRequestNoMapper.selectByKey(key);
        if (StringUtils.isEmpty(value)) {
            GtaRequestNo instance = GtaRequestNo.getInstance();
            instance.setId(IdUtil.getSnowflake(0,0).nextIdStr());
            instance.setKey(key);
            value= String.format("%0"+length+"d", 1); ;
            instance.setValue(value);
            gtaRequestNoMapper.insert(instance);
        } else {
            Integer integer = Integer.valueOf(value);
            value= String.format("%0"+length+"d", integer+1); ;
            gtaRequestNoMapper.updateByKey(key, value);
        }
        return String.format("%s%s", key, value);
    }
}
