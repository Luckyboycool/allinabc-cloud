package com.allinabc.cloud.org.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.admin.api.service.api.ApiCidbService;
import com.allinabc.cloud.admin.api.service.api.ApiRoleService;
import com.allinabc.cloud.admin.api.service.api.ApiRoleUserService;
import com.allinabc.cloud.admin.pojo.po.RoleUser;
import com.allinabc.cloud.admin.pojo.vo.CidbBasicSimpleVO;
import com.allinabc.cloud.admin.pojo.vo.RoleBaseVO;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.enums.UserLoginType;
import com.allinabc.cloud.common.core.utils.RandomUtil;
import com.allinabc.cloud.common.core.utils.security.Md5Utils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.mapper.CustomerAccountMapper;
import com.allinabc.cloud.org.mapper.CustomerMapper;
import com.allinabc.cloud.org.pojo.dto.CustomerAccountCreatDTO;
import com.allinabc.cloud.org.pojo.dto.CustomerAccountSearchDTO;
import com.allinabc.cloud.org.pojo.dto.CustomerAccountUpdateDTO;
import com.allinabc.cloud.org.pojo.po.Customer;
import com.allinabc.cloud.org.pojo.po.CustomerAccount;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountBasicVO;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountDetailVO;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountVO;
import com.allinabc.cloud.org.pojo.vo.CustomerVO;
import com.allinabc.cloud.org.service.CustomerAccountService;
import com.allinabc.cloud.org.service.CustomerService;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Simon.Xue
 * @date 2/24/21 10:37 AM
 **/

@Slf4j
@Service
public class CustomerAccountServiceImpl extends MybatisCommonServiceImpl<CustomerAccountMapper, CustomerAccount> implements CustomerAccountService {

    @Resource
    private CustomerAccountMapper customerAccountMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerService customerService;

    @Resource
    private ApiRoleUserService apiRoleUserService;
    @Resource
    private ApiRoleService apiRoleService;
    @Resource
    private ApiCidbService apiCidbService;


    @Override
    protected MybatisCommonBaseMapper<CustomerAccount> getRepository() {
        return customerAccountMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> add(CustomerAccountCreatDTO creatDTO) {
        if (this.checkUsernameIsExist(creatDTO.getUsername())) {
            log.warn("?????????????????????, username = {}", creatDTO.getUsername());
            return Result.failed("?????????????????????");
        }
        CustomerAccount customerAccount = new CustomerAccount();
        BeanUtil.copyProperties(creatDTO, customerAccount);
        String customerAccountId = IdUtil.getSnowflake(0, 0).nextIdStr();
        customerAccount.setId(customerAccountId);
        List<String> custCodes = creatDTO.getCustCodes();
        this.checkCustomerExistGroup(custCodes);
        List<Customer> customers = custCodes.stream().map(a -> {
            Customer customer = new Customer();
            customer.setId(IdUtil.getSnowflake(0, 0).nextIdStr());
            customer.setCustomerAccountId(customerAccountId);
            customer.setCustCode(a);
            return customer;
        }).collect(Collectors.toList());

        customerService.batchInsert(customers);

        // ????????????
        List<String> roleIdList = creatDTO.getRoleIdList();
        roleIdList.removeIf(Objects::isNull);
        if (CollectionUtil.isNotEmpty(roleIdList)) {
            List<RoleUser> roleUserList = roleIdList.stream().map(s -> {
                RoleUser roleUser = new RoleUser();
                roleUser.setRoleId(s);
                roleUser.setUserId(customerAccount.getId());
                return roleUser;
            }).collect(Collectors.toList());

            apiRoleUserService.insertBatch(roleUserList);
        }


        // ??????????????????
        customerAccount.setSalt(RandomUtil.randomStr(6));
        customerAccount.setPassword(Md5Utils.hash(customerAccount.getUsername() + customerAccount.getPassword() + customerAccount.getSalt()));
        customerAccount.setCreatedBy(getCurrentUserId());
        customerAccount.setCreateTm(new Date());
        int insertResult = customerAccountMapper.insert(customerAccount);
        if (insertResult != 1) {
            log.error("???????????????????????????account = {}", JSONObject.toJSONString(customerAccount));
            throw new RuntimeException("????????????????????????");
        }
        return Result.success("????????????????????????");
    }

    /**
     * ?????????????????????????????????
     * @param custCodeList
     */
    private void checkCustomerExistGroup(final List<String> custCodeList) {
        Assert.notEmpty(custCodeList, "custCodeList must not null");
        List<CidbBasicSimpleVO> cidbBasicSimpleVOList = apiCidbService.findListByCustCodes(custCodeList);
        long count = cidbBasicSimpleVOList.stream().map(CidbBasicSimpleVO::getCustGroup).distinct().count();
        Assert.isTrue(count == 1, "?????????????????????????????????");
    }

    /**
     * ???????????????????????????
     * @param username
     * @return
     */
    public boolean checkUsernameIsExist(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("USER_NAME", username);
        CustomerAccount customerAccount = customerAccountMapper.selectOne(wrapper);
        if (null != customerAccount) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateById(CustomerAccountUpdateDTO updateDTO) {

        CustomerAccount customerAccount = new CustomerAccount();
        BeanUtil.copyProperties(updateDTO, customerAccount);
        List<String> custCodes = updateDTO.getCustCodes();
        this.checkCustomerExistGroup(custCodes);

        List<String> roleIdList = updateDTO.getRoleIdList();
        roleIdList.removeIf(Objects::isNull);
        String id = customerAccount.getId();
        CustomerAccount customerAccountResp = customerAccountMapper.selectById(id);
        if (null == customerAccountResp) {
            log.error("????????????????????????id = {}", id);
            return Result.failed("?????????????????????");
        }

        QueryWrapper<CustomerAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("USER_NAME", updateDTO.getUsername());
        CustomerAccount customerAccountTemp = customerAccountMapper.selectOne(wrapper);

        if (null != customerAccountTemp && !id.equals(customerAccountTemp.getId())) {
            log.warn("?????????????????????, username = {}", updateDTO.getUsername());
            return Result.failed("?????????????????????");
        }

        //????????????
        if (StringUtils.isNotEmpty(updateDTO.getPassword())
                && StringUtils.isNotEmpty(updateDTO.getRePassword())) {
            Assert.isTrue(StringUtils.equals(updateDTO.getPassword(), updateDTO.getRePassword()), "??????????????????");
            customerAccount.setSalt(RandomUtil.randomStr(6));
            customerAccount.setPassword(Md5Utils.hash(customerAccount.getUsername() + updateDTO.getPassword() + customerAccount.getSalt()));
        } else {
            //?????????????????????   ??????????????????set??????
            customerAccount.setPassword(customerAccountResp.getPassword());
        }

        //????????????????????????
        Map<String, Object> map = Maps.newHashMap();
        map.put("CUSTOMER_ACCOUNT_ID", id);
        int deleteCount = customerMapper.deleteByMap(map);
        if (deleteCount == 0) {
            log.error("?????????????????????????????????customerList = {}", JSONObject.toJSONString(customerAccountResp.getCustomerId()));
            throw new RuntimeException("??????????????????????????????");
        }

        //???????????????????????????
        if (CollectionUtil.isNotEmpty(roleIdList)) {
            List<RoleUser> roleUserList = roleIdList.stream().distinct().map(s -> {
                RoleUser roleUser = new RoleUser();
                roleUser.setUserId(customerAccount.getId());
                roleUser.setRoleId(s);
                return roleUser;
            }).collect(Collectors.toList());
            apiRoleUserService.updateBatch(roleUserList);
        }


        // ????????????????????????
        List<Customer> customers = custCodes.stream().map(c -> {
            Customer customer = new Customer();
            customer.setId(IdUtil.getSnowflake(0, 0).nextIdStr());
            customer.setCustomerAccountId(id);
            customer.setCustCode(c);
            return customer;
        }).collect(Collectors.toList());

        customerService.batchInsert(customers);

        customerAccount.setUpdatedBy(getCurrentUserId());
        customerAccount.setUpdateTm(new Date());
        int updateCount = customerAccountMapper.updateById(customerAccount);
        if (updateCount != 1) {
            log.error("???????????????????????????account = {}", JSONObject.toJSONString(customerAccount));
            throw new RuntimeException("????????????????????????");
        }
        return Result.success("????????????????????????");
    }

    @Override
    public IPage<CustomerAccountVO> findPage(CustomerAccountSearchDTO searchDTO) {
        Page<CustomerAccountVO> page = new Page<>();
        page.setCurrent(searchDTO.getPageNum());
        page.setSize(searchDTO.getPageSize());
        String username = searchDTO.getUsername();
        String name = searchDTO.getName();
        /**
         * TODO ???????????????????????????????????????
         */
        //????????????????????????
        User currentUser = getCurrentUser();
        Assert.notNull(currentUser, "????????????????????????");

        String accountType = currentUser.getAccountType();
        // ??????
        if (UserLoginType.EMPLOYEE.getCode().equals(accountType)) {
            return this.handleEmployeeCustomer(page, username, name);
        }

        // ??????
        if (UserLoginType.ACCOUNT.getCode().equals(accountType)) {
            return this.handleCustomerChild(page, username, name);
        }

        return page;
    }

    /**
     * ????????????????????????
     * @param page
     * @param username
     * @param name
     * @return
     */
    private IPage<CustomerAccountVO> handleEmployeeCustomer(final Page<CustomerAccountVO> page,
                                                            final String username,
                                                            final String name) {
        Assert.notNull(page, "Page must not null");

        IPage<CustomerAccountVO> iPage = customerAccountMapper.findPage(page, username, name);
        this.handlerCustomerPage(iPage);
        return iPage;
    }

    /**
     * ???????????????????????????
     * @param page
     * @param username
     * @param name
     * @return
     */
    private IPage<CustomerAccountVO> handleCustomerChild(final Page<CustomerAccountVO> page,
                                                         final String username,
                                                         final String name) {
        Assert.notNull(page, "Page must not null");
        String currentUserId = this.getCurrentUserId();

        IPage<CustomerAccountVO> customerPage = customerAccountMapper.findCustomerPage(page, username, name, currentUserId);
        this.handlerCustomerPage(customerPage);

        return customerPage;
    }

    /**
     * ????????????????????????????????????
     * @param iPage
     */
    private void handlerCustomerPage(IPage<CustomerAccountVO> iPage) {
        List<String> ids = iPage.getRecords().stream().map(CustomerAccountVO::getId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(ids)) {
            return;
        }
        List<RoleBaseVO> batchBaseInfos = apiRoleService.getBatchBaseInfo(ids);
        if (CollectionUtil.isEmpty(batchBaseInfos)) {
            return;
        }

        iPage.getRecords().forEach(a-> {
            // ??????????????????
            List<CustomerAccountVO.CustomerAccountRoleVO> roles = batchBaseInfos.stream()
                    .filter(b -> a.getId().equals(b.getUserId()))
                    .map(c-> {
                        CustomerAccountVO.CustomerAccountRoleVO roleVO = new CustomerAccountVO.CustomerAccountRoleVO();
                        roleVO.setId(c.getId());
                        roleVO.setName(c.getRoleName());
                        return roleVO;
                    })
                    .collect(Collectors.toList());
            a.setRoles(roles);
        });
    }



    @Override
    public Result<CustomerAccountDetailVO> getDetailById(String id) {
        CustomerAccount customerAccount = customerAccountMapper.selectById(id);
        Assert.notNull(customerAccount, "?????????????????????");

        CustomerAccountDetailVO customerAccountDetailVO = BeanUtil.copyProperties(customerAccount, CustomerAccountDetailVO.class);

        List<Customer> customers = this.getCustomers(id);
        List<String> custCodes = customers.stream().map(Customer::getCustCode).collect(Collectors.toList());
        List<CidbBasicSimpleVO> cidbBasicSimpleVOList = apiCidbService.findListByCustCodes(custCodes);
        List<CustomerVO> customerVOList = cidbBasicSimpleVOList.stream()
                .map(a ->BeanUtil.copyProperties(a, CustomerVO.class))
                .collect(Collectors.toList());
        customerAccountDetailVO.setCustomerList(customerVOList);

        List<RoleBaseVO> roleBaseInfo = apiRoleService.getRoleBaseInfo(id);
        List<CustomerAccountDetailVO.RoleVO> roleVOList = roleBaseInfo.stream().map(roleBaseVO -> {
            CustomerAccountDetailVO.RoleVO roleVO = new CustomerAccountDetailVO.RoleVO();
            roleVO.setId(roleBaseVO.getId());
            roleVO.setName(roleBaseVO.getRoleName());
            return roleVO;
        }).collect(Collectors.toList());

        customerAccountDetailVO.setRoleList(roleVOList);
        return Result.success(customerAccountDetailVO, "????????????");
    }

    @Override
    public Result<CustomerAccount> getByUsername(String username) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("USER_NAME", username);
        List<CustomerAccount> customerAccounts = customerAccountMapper.selectByMap(map);
        if (customerAccounts.isEmpty()) {
            return Result.success("????????????");
        }
        CustomerAccount customerAccount = customerAccounts.stream().findFirst().get();
        return Result.success(customerAccount, "????????????");
    }

    @Override
    public Result<List<CustomerVO>> findList() {
        User currentUser = getCurrentUser();
        Assert.notNull(currentUser, "??????????????????");
        if (!currentUser.getAccountType().equals(UserLoginType.ACCOUNT.getCode())) {
            return Result.success(Collections.EMPTY_LIST, "????????????");
        }

        CustomerAccount customerAccount = customerAccountMapper.selectById(currentUser.getId());
        if (null == customerAccount) {
        }
        List<Customer> customers = getCustomers(currentUser.getId());
        List<String> custCodeList = customers.stream().map(Customer::getCustCode).collect(Collectors.toList());
        List<CidbBasicSimpleVO> cidbBasicSimpleVOList = apiCidbService.findListByCustCodes(custCodeList);
        List<CustomerVO> customerVOList = cidbBasicSimpleVOList.stream()
                .map(a -> BeanUtil.copyProperties(a, CustomerVO.class))
                .collect(Collectors.toList());
        return Result.success(customerVOList);
    }

    /**
     * ??????????????????
     * @param id ????????????ID
     * @return
     */
    private List<Customer> getCustomers(@NotNull final String id) {
        QueryWrapper<Customer> wrapper = new QueryWrapper();
        wrapper.eq("CUSTOMER_ACCOUNT_ID", id);
        List<Customer> customers = customerMapper.selectList(wrapper);
        Assert.notEmpty(customers, "customers must not empty");
        return customers;
    }


    @Override
    public Result<List<CustomerAccountBasicVO>> findChildAndParentById(String id) {
        List<CustomerAccountBasicVO> customerAccountBasicVOS = customerAccountMapper.findChildAndParent(id);
        return Result.success(customerAccountBasicVOS, "????????????");
    }
}
