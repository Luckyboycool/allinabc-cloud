package com.allinabc.cloud.admin.service;

import com.allinabc.cloud.admin.pojo.po.Config;
import com.allinabc.cloud.admin.pojo.vo.SysConfigVO;
import com.allinabc.common.mybatis.service.MybatisCommonService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2020-12-16
 */
public interface ConfigService extends MybatisCommonService<Config> {

    Config selectByKey(String key);

    Config selectByKeyAndApp(String appCode, String key);

    String selectValueByKey(String configKey);

    List<SysConfigVO> selectMapConfigByAppCode(String appCode);
}
