package com.allinabc.cloud.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.allinabc.cloud.admin.mapper.CidbBasicInfoMapper;
import com.allinabc.cloud.admin.mapper.CidbDetailMapper;
import com.allinabc.cloud.admin.pojo.dto.CidbUpdateDTO;
import com.allinabc.cloud.admin.pojo.dto.QueryCidbParam;
import com.allinabc.cloud.admin.pojo.po.CidbBasicInfo;
import com.allinabc.cloud.admin.pojo.po.CidbDetail;
import com.allinabc.cloud.admin.pojo.vo.CidbBasicInfoVo;
import com.allinabc.cloud.admin.pojo.vo.CidbBasicSimpleVO;
import com.allinabc.cloud.admin.service.CidbBasicInfoService;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.api.service.api.ApiEmployeeService;
import com.allinabc.cloud.org.pojo.po.Employee;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author gaoxiang
 * @Date 2021/3/22
 **/
@Slf4j
@Service
public class CidbBasicInfoServiceImpl implements CidbBasicInfoService {
    @Resource
    private CidbBasicInfoMapper cidbBasicInfoMapper;
    @Resource
    private CidbDetailMapper cidbDetailMapper;
    @Resource
    private ApiEmployeeService apiEmployeeService;

    @Override
    public IPage<CidbBasicInfoVo> findList(QueryCidbParam queryCidbParam, int pageNo, int pageSize) {
        IPage<CidbBasicInfoVo> list = cidbBasicInfoMapper.list(new Page<>(pageNo,pageSize),queryCidbParam);
        List<CidbBasicInfoVo> records = list.getRecords();
        if (CollUtil.isNotEmpty(records)){
            for (CidbBasicInfoVo record : records) {
                List<CidbDetail> cidbDetailList = record.getCidbDetailList();
                if (CollUtil.isNotEmpty(cidbDetailList)){
                    this.handlePageResult(record, cidbDetailList);
                }
            }
        }
        return list;
    }

    /**
     * 处理分页后的结果
     * @param record
     * @param cidbDetailList
     */
    private void handlePageResult(CidbBasicInfoVo record, List<CidbDetail> cidbDetailList) {
        List<String> members = cidbDetailList.stream().map(CidbDetail::getMembers).collect(Collectors.toList());
        List<String> empIdList = new ArrayList<>();
        members.forEach(a-> {
            a = Joiner.on(",").skipNulls().join(a.split(","));
            empIdList.addAll(Arrays.asList(a.split(",")));
        });

        List<Employee> employees = apiEmployeeService.findEmployees(empIdList);

        Map<String, Map<String, String>> collect = cidbDetailList.stream().distinct()
                .collect(Collectors.groupingBy(CidbDetail::getArea,
                        Collectors.toMap(CidbDetail::getDept, CidbDetail::getMembers)));

        Map<String, Map<String, List<CidbBasicInfoVo.EmpVO>>> map = new HashMap<>(16);

        collect.entrySet().forEach(a-> {
            Map<String, List<CidbBasicInfoVo.EmpVO>> tempMap = new HashMap<>(16);

            a.getValue().entrySet().forEach(b-> {
                // 取出员工ID List
                List<CidbBasicInfoVo.EmpVO> empVOList = Arrays.stream(b.getValue().split(",")).map(c -> {
                    CidbBasicInfoVo.EmpVO empVO = new CidbBasicInfoVo.EmpVO();
                    empVO.setId(c);
                    return empVO;
                }).collect(Collectors.toList());

                // 设置员工信息
                empVOList.forEach(c-> {
                    employees.forEach(d-> {
                        if (c.getId().equals(d.getId())) {
                            c.setName(d.getName()+"("+d.getJobNumber()+")");
                        }
                    });
                });

                // 设置Map值 Map<String, List<CidbBasicInfoVo.EmpVO>>
                tempMap.put(b.getKey(), empVOList);

            });

            // 设置Map 值 Map<String, Map<String, List<CidbBasicInfoVo.EmpVO>>>
            map.put(a.getKey(), tempMap);
        });
        record.setMaps(map);
    }

    @Override
    public List<CidbBasicSimpleVO> findAll(String code, String name) {
        QueryWrapper<CidbBasicInfo> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(code)) {
            wrapper.like("UPPER(CUST_CODE)", code.toUpperCase());
        }

        if (StrUtil.isNotBlank(name)) {
            wrapper.like("UPPER(CUST_NAME)", name.toUpperCase());
        }
        List<CidbBasicInfo> cidbBasicInfos = cidbBasicInfoMapper.selectList(wrapper);
        List<CidbBasicSimpleVO> cidbBasicSimpleVOList = cidbBasicInfos.stream()
                .map(cidbBasicInfo -> BeanUtil.copyProperties(cidbBasicInfo, CidbBasicSimpleVO.class))
                .collect(Collectors.toList());

        return cidbBasicSimpleVOList;
    }

    @Override
    public Result<CidbBasicSimpleVO> getByCustCode(String custCode) {
        QueryWrapper<CidbBasicInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("CUST_CODE", custCode);
        CidbBasicInfo cidbBasicInfo = cidbBasicInfoMapper.selectOne(wrapper);
        if (null == cidbBasicInfo) {
            return Result.success("查询成功");
        }
        CidbBasicSimpleVO cidbBasicSimpleVO = BeanUtil.copyProperties(cidbBasicInfo, CidbBasicSimpleVO.class);
        return Result.success(cidbBasicSimpleVO, "查询成功");
    }

    @Override
    public Result<List<CidbBasicSimpleVO>> findListByCustCodes(List<String> custCodes) {
        QueryWrapper<CidbBasicInfo> wrapper = new QueryWrapper<>();
        custCodes = custCodes.stream().distinct().collect(Collectors.toList());
        wrapper.in("CUST_CODE", custCodes);
        List<CidbBasicInfo> cidbBasicInfos = cidbBasicInfoMapper.selectList(wrapper);
        List<CidbBasicSimpleVO> cidbBasicSimpleVOList = cidbBasicInfos.stream().map(c -> {
            CidbBasicSimpleVO cidbBasicSimpleVO = new CidbBasicSimpleVO();
            BeanUtil.copyProperties(c, cidbBasicSimpleVO);
            return cidbBasicSimpleVO;
        }).collect(Collectors.toList());
        return Result.success(cidbBasicSimpleVOList, "查询成功");
    }

    @Transactional
    @Override
    public Result update(CidbUpdateDTO cidbUpdateDTO) {
        String id = cidbUpdateDTO.getId();
        CidbBasicInfo cidbBasicInfo = cidbBasicInfoMapper.selectById(id);
        if (cidbBasicInfo == null) {
            log.warn("CIDB数据不存在，ID = {}", id);
            return Result.failed("数据不存在，请重试");
        }
        CidbUpdateDTO.CidbDetailUpdateDTO hc = cidbUpdateDTO.getHc();
        CidbUpdateDTO.CidbDetailUpdateDTO lg = cidbUpdateDTO.getLg();

        String custCode = cidbBasicInfo.getCustCode();
        Assert.notNull(custCode, "custCode must not null");
        // 删除HC
        if (Objects.nonNull(hc)) {
            QueryWrapper<CidbDetail> wrapper = new QueryWrapper<>();
            wrapper.eq("AREA", "HC");
            wrapper.eq("CUST_CODE", custCode);
            cidbDetailMapper.delete(wrapper);
        }
        // 删除LG
        if (Objects.nonNull(lg)) {
            QueryWrapper<CidbDetail> wrapper = new QueryWrapper<>();
            wrapper.eq("AREA", "LG");
            wrapper.eq("CUST_CODE", custCode);
            cidbDetailMapper.delete(wrapper);
        }
        Map<String, Object> hcMap = BeanUtil.beanToMap(hc);

        List<CidbDetail> hcCidbDetails = hcMap.entrySet().stream().map(a -> {
            CidbDetail cidbDetail = new CidbDetail();
            cidbDetail.setId(IdUtil.getSnowflake(0, 0).nextIdStr());
            cidbDetail.setArea("HC");
            cidbDetail.setCustCode(custCode);
            cidbDetail.setDept(a.getKey());
            Object value = a.getValue();
            if (null != value) {
                List<Object> arr = (List)(value);
                String join = StringUtils.join(arr, ',');
                cidbDetail.setMembers(join);
            }
            return cidbDetail;
        }).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(hcCidbDetails)) {
            cidbDetailMapper.batchInsert(hcCidbDetails);
        }

        Map<String, Object> lgMap = BeanUtil.beanToMap(lg);
        List<CidbDetail> lgCidbDetails = lgMap.entrySet().stream().map(a -> {
            CidbDetail cidbDetail = new CidbDetail();
            cidbDetail.setId(IdUtil.getSnowflake(0, 0).nextIdStr());
            cidbDetail.setArea("LG");
            cidbDetail.setCustCode(custCode);
            cidbDetail.setDept(a.getKey());
            Object value = a.getValue();
            if (null != value) {
                List<Object> arr = (List)(value);
                String join = StringUtils.join(arr, ',');
                cidbDetail.setMembers(join);
            }
            return cidbDetail;
        }).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(lgCidbDetails)) {
            cidbDetailMapper.batchInsert(lgCidbDetails);
        }

        log.info("更新CIDB成功");
        return Result.success("更新成功");
    }
}
