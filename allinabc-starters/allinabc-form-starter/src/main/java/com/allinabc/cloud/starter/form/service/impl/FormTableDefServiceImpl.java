package com.allinabc.cloud.starter.form.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.form.domain.FormActivitiField;
import com.allinabc.cloud.starter.form.domain.FormFieldDef;
import com.allinabc.cloud.starter.form.domain.FormTableDef;
import com.allinabc.cloud.starter.form.mapper.FormActivitiFieldMapper;
import com.allinabc.cloud.starter.form.mapper.FormFieldDefMapper;
import com.allinabc.cloud.starter.form.mapper.FormTableDefMapper;
import com.allinabc.cloud.starter.form.resp.FormFieldResp;
import com.allinabc.cloud.starter.form.service.FormTableDefService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Simon.Xue
 * @date 2021/3/1 1:55 下午
 **/
@Service
public class FormTableDefServiceImpl extends MybatisCommonServiceImpl<FormTableDefMapper, FormTableDef> implements FormTableDefService {

    @Resource
    private FormTableDefMapper formTableDefMapper;
    @Resource
    private FormFieldDefMapper formFieldDefMapper;
    @Resource
    private FormActivitiFieldMapper formActivitiFieldMapper;

    @Override
    protected MybatisCommonBaseMapper<FormTableDef> getRepository() {
        return formTableDefMapper;
    }

    @Override
    public Result<Map<String, Map<String, FormFieldResp>>> listByFormTypeV1(String formType) {
        QueryWrapper<FormTableDef> formTableDefWrapper = new QueryWrapper<>();
        formTableDefWrapper.eq("FORM_TYPE", formType);
        List<FormTableDef> formTableDefs = formTableDefMapper.selectList(formTableDefWrapper);
        Map<String, Map<String, FormFieldResp>> resultMap = new HashMap<>();
        if (CollectionUtil.isEmpty(formTableDefs)) {
            return Result.success(resultMap, "查询成功");
        }

        List<String> idList = formTableDefs.stream()
                .map(FormTableDef::getId)
                .collect(Collectors.toList());
        QueryWrapper<FormFieldDef> formFieldDefWrapper = new QueryWrapper<>();
        formFieldDefWrapper.in("TABLE_ID", idList);
        List<FormFieldDef> formFieldDefs = formFieldDefMapper.selectList(formFieldDefWrapper);

        List<String> fieldList = formFieldDefs.stream()
                .map(FormFieldDef::getId)
                .collect(Collectors.toList());
        QueryWrapper<FormActivitiField> formActivitiFieldDefWrapper = new QueryWrapper<>();
        formFieldDefWrapper.in("FIELD_ID", fieldList);
        List<FormActivitiField> formActivitiFields = formActivitiFieldMapper.selectList(formActivitiFieldDefWrapper);

        Map<String, List<FormFieldResp>> collect = formFieldDefs.stream().map(f -> {
            FormFieldResp formFieldResp = new FormFieldResp();
            formFieldResp.setFieldId(f.getId());
            formFieldResp.setFieldType(f.getFieldType());
            formFieldResp.setFieldName(f.getFieldName());
            formFieldResp.setComponentType(f.getComponentType());
            formTableDefs.forEach(ft -> {
                if (f.getTableId().equals(ft.getId())) {
                    formFieldResp.setEntityKey(ft.getEntityKey());
                }
            });

            formActivitiFields.forEach(faf -> {
                if (f.getId().equals(faf.getFieldId())) {
                    formFieldResp.setRw(faf.getRw());
                }
            });
            return formFieldResp;
        }).collect(Collectors.groupingBy(FormFieldResp::getEntityKey));

        collect.entrySet().forEach(a-> {
            Map<String, FormFieldResp> childMap = a.getValue().stream()
                    .collect(Collectors.toMap(FormFieldResp::getFieldName, Function.identity()));
            resultMap.put(a.getKey(), childMap);
        });

        return Result.success(resultMap, "查询成功");
    }

    @Override
    public Result<Map<String, Map<String, String>>> listByFormTypeV2(String formType) {
        QueryWrapper<FormTableDef> formTableDefWrapper = new QueryWrapper<>();
        formTableDefWrapper.eq("FORM_TYPE", formType);
        List<FormTableDef> formTableDefs = formTableDefMapper.selectList(formTableDefWrapper);
        Map<String, Map<String, String>> resultMap = new HashMap<>();
        if (CollectionUtil.isEmpty(formTableDefs)) {
            return Result.success(resultMap, "查询成功");
        }

        List<String> idList = formTableDefs.stream()
                .map(FormTableDef::getId)
                .collect(Collectors.toList());
        QueryWrapper<FormFieldDef> formFieldDefWrapper = new QueryWrapper<>();
        formFieldDefWrapper.in("TABLE_ID", idList);
        List<FormFieldDef> formFieldDefs = formFieldDefMapper.selectList(formFieldDefWrapper);

        List<String> fieldList = formFieldDefs.stream()
                .map(FormFieldDef::getId)
                .collect(Collectors.toList());
        QueryWrapper<FormActivitiField> formActivitiFieldDefWrapper = new QueryWrapper<>();
        formFieldDefWrapper.in("FIELD_ID", fieldList);
        List<FormActivitiField> formActivitiFields = formActivitiFieldMapper.selectList(formActivitiFieldDefWrapper);

        Map<String, List<FormFieldResp>> parent = formFieldDefs.stream().map(f -> {
            FormFieldResp formFieldResp = new FormFieldResp();
            formFieldResp.setFieldId(f.getId());
            formFieldResp.setFieldType(f.getFieldType());
            formFieldResp.setFieldName(f.getFieldName());
            formFieldResp.setComponentType(f.getComponentType());
            formTableDefs.forEach(ft -> {
                if (f.getTableId().equals(ft.getId())) {
                    formFieldResp.setEntityKey(ft.getEntityKey());
                }
            });

            formActivitiFields.forEach(faf -> {
                if (f.getId().equals(faf.getFieldId())) {
                    formFieldResp.setRw(faf.getRw());
                }
            });
            return formFieldResp;
        }).collect(Collectors.groupingBy(FormFieldResp::getEntityKey));

        parent.entrySet().forEach(a-> {
            Map<String, String> childMap = a.getValue().stream()
                    .collect(Collectors.toMap(FormFieldResp::getFieldName, FormFieldResp::getRw));
            resultMap.put(a.getKey(), childMap);
        });

        return Result.success(resultMap, "查询成功");
    }

    @Override
    public Result<Map<String, Map<String, Map<String, String>>>> listByFormType(String formType, String nodeKey) {
        QueryWrapper<FormTableDef> formTableDefWrapper = new QueryWrapper<>();
        formTableDefWrapper.eq("FORM_TYPE", formType);
        List<FormTableDef> formTableDefs = formTableDefMapper.selectList(formTableDefWrapper);
        Map<String, Map<String, Map<String, String>>> resultMap = new HashMap<>(16);
        if (formTableDefs.isEmpty()) {
            return Result.success(resultMap, "查询成功");
        }
        List<FormFieldResp> formFieldResps = formActivitiFieldMapper.listByNodeKey(nodeKey, formType);
        if (formFieldResps.isEmpty()) {
            return Result.success(resultMap, "查询成功");
        }

        formFieldResps.forEach(r-> {
            formTableDefs.forEach(f-> {
                if (r.getTableId().equals(f.getId())) {
                    r.setEntityKey(f.getEntityKey());
                }
            });
        });

        //TODO 把没有匹配到的TableId,查询原有的EntityKey
        List<String> tableIds = formFieldResps.stream()
                .filter(a -> StringUtils.isEmpty(a.getEntityKey()))
                .map(FormFieldResp::getTableId).distinct().collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(tableIds)) {
            formTableDefWrapper = new QueryWrapper<>();
            formTableDefWrapper.in("ID", tableIds);
            List<FormTableDef> formTableDefsSingle = formTableDefMapper.selectList(formTableDefWrapper);
            formFieldResps.forEach(r-> {
                formTableDefsSingle.forEach(f-> {
                    if (r.getTableId().equals(f.getId())) {
                        r.setEntityKey(f.getEntityKey());
                    }
                });
            });
        }

        // 最外层的Map   KEY是EntityKey
        Map<String, List<FormFieldResp>> parent = formFieldResps.stream()
                .collect(Collectors.groupingBy(FormFieldResp::getEntityKey));


        parent.entrySet().forEach(p-> {
            Map<String, List<FormFieldResp>> tempMap = p.getValue().stream().collect(Collectors.groupingBy(FormFieldResp::getType));

            Map<String, Map<String, String>> childMap = Maps.newHashMap();
            tempMap.entrySet().forEach(t-> {
                Map<String, String> tempChildMap = t.getValue().stream().collect(Collectors.toMap(FormFieldResp::getFieldName, FormFieldResp::getRw));
                childMap.put(t.getKey(), tempChildMap);
            });
            resultMap.put(p.getKey(), childMap);
        });
        return Result.success(resultMap, "查询成功");
    }
}
