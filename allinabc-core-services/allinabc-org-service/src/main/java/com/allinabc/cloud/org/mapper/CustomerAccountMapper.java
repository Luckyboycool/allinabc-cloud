package com.allinabc.cloud.org.mapper;

import com.allinabc.cloud.org.pojo.po.CustomerAccount;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountBasicVO;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/24/21 10:42 AM
 **/
public interface CustomerAccountMapper extends MybatisCommonBaseMapper<CustomerAccount> {
    /**
     * 查询客户账户列表
     * @param page
     * @param username
     * @param name
     * @return
     */
    IPage<CustomerAccountVO> findPage(Page page, @Param("username") String username,
                                      @Param("name") String name);

    /**
     * 查询指定ID的上级和下级客户
     * @param id
     * @return
     */
    List<CustomerAccountBasicVO> findChildAndParent(@Param("id") String id);

    /**
     * 查询客户下的客户账户列表
     * @param page
     * @param username
     * @param name
     * @param currentUserId
     * @return
     */
    IPage<CustomerAccountVO> findCustomerPage(Page page,
                                              @Param("username") String username,
                                              @Param("name") String name,
                                              @Param("currentUserId") String currentUserId);
}
