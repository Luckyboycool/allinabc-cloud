package com.allinabc.cloud.activiti.service.impl;

import com.allinabc.cloud.activiti.mapper.ProcessIdMapper;
import com.allinabc.cloud.activiti.pojo.dto.DraftParam;
import com.allinabc.cloud.activiti.pojo.vo.DraftVO;
import com.allinabc.cloud.activiti.service.DraftService;
import com.allinabc.cloud.activiti.util.FormNameUtils;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.utils.SortParamUtils;
import com.allinabc.cloud.starter.web.util.CurrentUserUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/3 17:15
 **/
@Service
public class DraftServiceImpl implements DraftService {

    @Resource
    private ProcessIdMapper draftMapper;

    @Override
    public Page<DraftVO> findDraftList(DraftParam draftParam) {
        User currentUser = CurrentUserUtil.getCurrentUser();
        if(currentUser==null){
            throw new RuntimeException("用户信息获取失败");
        }
        String formType = draftParam.getFormType();
        String[] split = formType.split(",");
        if(split[0].equals("")){
            throw new RuntimeException("数据为空");
        }
        List<String> formTypeList = Arrays.asList(split);
        IPage<DraftVO> page = new Page<>(draftParam.getPageNum(),draftParam.getPageSize());
        // 处理处理
        Map<String, String> sortParam = draftParam.getSortParam();
        draftParam.setSortParam(SortParamUtils.handleSortParam(sortParam));
        Page<DraftVO> re = draftMapper.selectDraftList(page,formTypeList,currentUser.getId(),currentUser.getAccountType(), draftParam);

        re.getRecords().forEach(a-> {
            a.setFormName(FormNameUtils.getFormName(a.getFormType()));
        });
        return re;
    }
}
