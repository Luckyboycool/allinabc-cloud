package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.dto.QueryCidbParam;
import com.allinabc.cloud.admin.pojo.po.CidbBasicInfo;
import com.allinabc.cloud.admin.pojo.vo.CidbBasicInfoVo;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface CidbBasicInfoMapper extends MybatisCommonBaseMapper<CidbBasicInfo> {
    int deleteByPrimaryKey(String id);

    int insertSelective(CidbBasicInfo record);

    CidbBasicInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CidbBasicInfo record);

    /**
     * 此分页有问题
     * Mybatis Plus中分页逻辑是先分页后映射
     * @param page
     * @param param
     * @return
     */
    IPage<CidbBasicInfoVo> findList(Page page, @Param("param") QueryCidbParam param);

    IPage<CidbBasicInfoVo> list(Page page,@Param("param") QueryCidbParam param);
}