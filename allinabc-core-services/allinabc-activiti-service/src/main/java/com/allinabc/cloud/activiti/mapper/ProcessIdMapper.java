package com.allinabc.cloud.activiti.mapper;

import com.allinabc.cloud.activiti.pojo.dto.DraftParam;
import com.allinabc.cloud.activiti.pojo.po.ProcessId;
import com.allinabc.cloud.activiti.pojo.vo.DraftVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/26 14:08
 **/
public interface ProcessIdMapper extends MybatisCommonBaseMapper<ProcessId> {


    /**
     * 查询所有草稿状态的表单
     *
     * @param page
     * @param formTypeList
     * @param userId
     * @param userType
     * @param draftParam
     * @return
     */
    Page<DraftVO> selectDraftList(IPage<DraftVO> page, @Param("ls") List<String> formTypeList,
                                  @Param("userId") String userId, @Param("userType") String userType,
                                  @Param("draftParam") DraftParam draftParam);

    /**
     * 查询表单的名字(FORM_NAME)
     * @return
     */
    List<Map<String, String>> selectFormNameList();
}
