package com.allinabc.cloud.admin.service.impl;

import com.allinabc.cloud.admin.mapper.ConfigMapper;
import com.allinabc.cloud.admin.pojo.po.Config;
import com.allinabc.cloud.admin.pojo.vo.SysConfigVO;
import com.allinabc.cloud.admin.service.ConfigService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2020-12-16
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class ConfigServiceImpl extends MybatisCommonServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Resource
    private ConfigMapper configMapper;

    @Override
    public Config selectByKey(String key) {
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("config_key", key);
        List<Config> list = configMapper.selectByMap(columnMap);

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public Config selectByKeyAndApp(String appCode, String key) {
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("app_code", appCode);
        columnMap.put("config_key", key);
        List<Config> list = configMapper.selectByMap(columnMap);

        return null != list && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public String selectValueByKey(String configKey) {
        Config sysConfig = selectByKey(configKey);
        return null != sysConfig ? sysConfig.getConfigValue() : "";
    }

    @Override
    public List<SysConfigVO> selectMapConfigByAppCode(String appCode) {
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("app_code", appCode);
        List<Config>   sysConfigList = configMapper.selectByMap(columnMap);
        List<SysConfigVO> sysConfigVOList = new ArrayList<>();
        sysConfigList.forEach(cfg->{
            SysConfigVO vo = new SysConfigVO();
            vo.setCode(cfg.getConfigKey());
            vo.setValue(cfg.getConfigValue());
            sysConfigVOList.add(vo);
        });
        return sysConfigVOList;
    }

    @Override
    protected MybatisCommonBaseMapper<Config> getRepository() {
        return configMapper;
    }

}
