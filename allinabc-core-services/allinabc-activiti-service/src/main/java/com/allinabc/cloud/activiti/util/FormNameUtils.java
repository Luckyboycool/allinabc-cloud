package com.allinabc.cloud.activiti.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.activiti.mapper.ProcessIdMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FormType name 初始化
 * @author Simon.Xue
 * @date 2021/5/8 10:17 上午
 **/
@Slf4j
@Component
public class FormNameUtils {

    @Resource
    private ProcessIdMapper processIdMapper;

    public static Map<String, String> map;

    @Bean
    public void init() {
        List<Map<String, String>> list = processIdMapper.selectFormNameList();
        map = new HashMap<>(16);
        list.forEach(a-> {
            map.put(a.get("FORM_TYPE"), a.get("FORM_NAME"));
        });
        log.info("初始化了formName = {}", JSONObject.toJSONString(map));
    }

    /**
     * 获得formType Name
     * @param formType
     * @return
     */
    public static String getFormName(String formType) {
        if (StrUtil.isBlank(formType)) {
            return "";
        }
        if (CollUtil.isEmpty(map)) {
            return "";
        }

        return map.get(formType);
    }
}
