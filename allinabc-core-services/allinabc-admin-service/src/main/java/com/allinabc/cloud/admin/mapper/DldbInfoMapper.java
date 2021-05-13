package com.allinabc.cloud.admin.mapper;
import com.allinabc.cloud.admin.pojo.po.DldbInfo;
import com.allinabc.cloud.admin.pojo.vo.DldbVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author Simon.Xue
 * @date 2021/4/6 2:48 下午
 **/


public interface DldbInfoMapper extends MybatisCommonBaseMapper<DldbInfo> {
    /**
     * 查询产品信息列表
     * @param page
     * @param deviceName
     * @param productId
     * @return
     */
    IPage<DldbVO> findList(Page page, @Param("deviceName") String deviceName, @Param("productId") String productId);
}
