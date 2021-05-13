package com.allinabc.cloud.org.service;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.dto.CustomerAccountCreatDTO;
import com.allinabc.cloud.org.pojo.dto.CustomerAccountSearchDTO;
import com.allinabc.cloud.org.pojo.dto.CustomerAccountUpdateDTO;
import com.allinabc.cloud.org.pojo.po.CustomerAccount;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountBasicVO;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountDetailVO;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountVO;
import com.allinabc.cloud.org.pojo.vo.CustomerVO;
import com.allinabc.common.mybatis.service.MybatisCommonService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/24/21 10:36 AM
 **/
public interface CustomerAccountService extends MybatisCommonService<CustomerAccount> {
    /**
     * 添加客户账户
     * @param creatDTO
     * @return
     */
    Result<Void> add(CustomerAccountCreatDTO creatDTO);

    /**
     * 更新客户账户
     * @param updateDTO
     * @return
     */
    Result<Void> updateById(CustomerAccountUpdateDTO updateDTO);

    /**
     * 查询客户列表
     * @param searchDTO
     * @return
     */
    IPage<CustomerAccountVO> findPage(CustomerAccountSearchDTO searchDTO);

    /**
     * 查询客户详情
     * @param id
     * @return
     */
    Result<CustomerAccountDetailVO> getDetailById(String id);

    /**
     * 查询客户账号
     * @param username
     * @return
     */
    Result<CustomerAccount> getByUsername(String username);

    /**
     * 查询客户账号绑定的客户列表
     * @return
     */
    Result<List<CustomerVO>> findList();

    /**
     * 查询指定ID的客户子账号和主账号列表
     * @param id
     * @return
     */
    Result<List<CustomerAccountBasicVO>> findChildAndParentById(String id);
}
