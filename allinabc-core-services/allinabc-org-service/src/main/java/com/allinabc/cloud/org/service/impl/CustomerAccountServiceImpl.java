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
            log.warn("客户账号已存在, username = {}", creatDTO.getUsername());
            return Result.failed("客户账号已存在");
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

        // 添加角色
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


        // 添加客户账户
        customerAccount.setSalt(RandomUtil.randomStr(6));
        customerAccount.setPassword(Md5Utils.hash(customerAccount.getUsername() + customerAccount.getPassword() + customerAccount.getSalt()));
        customerAccount.setCreatedBy(getCurrentUserId());
        customerAccount.setCreateTm(new Date());
        int insertResult = customerAccountMapper.insert(customerAccount);
        if (insertResult != 1) {
            log.error("添加客户账户失败，account = {}", JSONObject.toJSONString(customerAccount));
            throw new RuntimeException("添加客户账户失败");
        }
        return Result.success("添加客户账户成功");
    }

    /**
     * 检查客户是否在同一组中
     * @param custCodeList
     */
    private void checkCustomerExistGroup(final List<String> custCodeList) {
        Assert.notEmpty(custCodeList, "custCodeList must not null");
        List<CidbBasicSimpleVO> cidbBasicSimpleVOList = apiCidbService.findListByCustCodes(custCodeList);
        long count = cidbBasicSimpleVOList.stream().map(CidbBasicSimpleVO::getCustGroup).distinct().count();
        Assert.isTrue(count == 1, "客户必须选择同一个组内");
    }

    /**
     * 查询用户名是否存在
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
            log.error("客户账号不存在，id = {}", id);
            return Result.failed("客户账号不存在");
        }

        QueryWrapper<CustomerAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("USER_NAME", updateDTO.getUsername());
        CustomerAccount customerAccountTemp = customerAccountMapper.selectOne(wrapper);

        if (null != customerAccountTemp && !id.equals(customerAccountTemp.getId())) {
            log.warn("客户账号已存在, username = {}", updateDTO.getUsername());
            return Result.failed("客户账号已存在");
        }

        //处理密码
        if (StringUtils.isNotEmpty(updateDTO.getPassword())
                && StringUtils.isNotEmpty(updateDTO.getRePassword())) {
            Assert.isTrue(StringUtils.equals(updateDTO.getPassword(), updateDTO.getRePassword()), "两个密码不致");
            customerAccount.setSalt(RandomUtil.randomStr(6));
            customerAccount.setPassword(Md5Utils.hash(customerAccount.getUsername() + updateDTO.getPassword() + customerAccount.getSalt()));
        } else {
            //前台传空字段串   就把原来密码set进去
            customerAccount.setPassword(customerAccountResp.getPassword());
        }

        //删除客户绑定关系
        Map<String, Object> map = Maps.newHashMap();
        map.put("CUSTOMER_ACCOUNT_ID", id);
        int deleteCount = customerMapper.deleteByMap(map);
        if (deleteCount == 0) {
            log.error("批量删除客户信息失败，customerList = {}", JSONObject.toJSONString(customerAccountResp.getCustomerId()));
            throw new RuntimeException("批量删除客户信息失败");
        }

        //更新客户和角色关系
        if (CollectionUtil.isNotEmpty(roleIdList)) {
            List<RoleUser> roleUserList = roleIdList.stream().distinct().map(s -> {
                RoleUser roleUser = new RoleUser();
                roleUser.setUserId(customerAccount.getId());
                roleUser.setRoleId(s);
                return roleUser;
            }).collect(Collectors.toList());
            apiRoleUserService.updateBatch(roleUserList);
        }


        // 批量添加客户信息
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
            log.error("更新客户帐户失败，account = {}", JSONObject.toJSONString(customerAccount));
            throw new RuntimeException("更新客户账户失败");
        }
        return Result.success("更新客户账户成功");
    }

    @Override
    public IPage<CustomerAccountVO> findPage(CustomerAccountSearchDTO searchDTO) {
        Page<CustomerAccountVO> page = new Page<>();
        page.setCurrent(searchDTO.getPageNum());
        page.setSize(searchDTO.getPageSize());
        String username = searchDTO.getUsername();
        String name = searchDTO.getName();
        /**
         * TODO 处理登录账号类型，来做判断
         */
        //当前登录账号信息
        User currentUser = getCurrentUser();
        Assert.notNull(currentUser, "获取登录信息失败");

        String accountType = currentUser.getAccountType();
        // 员工
        if (UserLoginType.EMPLOYEE.getCode().equals(accountType)) {
            return this.handleEmployeeCustomer(page, username, name);
        }

        // 客户
        if (UserLoginType.ACCOUNT.getCode().equals(accountType)) {
            return this.handleCustomerChild(page, username, name);
        }

        return page;
    }

    /**
     * 员工下的客户账号
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
     * 客户下的子客户账号
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
     * 处理客户列表分页后的数据
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
            // 处理角色列表
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
        Assert.notNull(customerAccount, "客户账号不存在");

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
        return Result.success(customerAccountDetailVO, "查询成功");
    }

    @Override
    public Result<CustomerAccount> getByUsername(String username) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("USER_NAME", username);
        List<CustomerAccount> customerAccounts = customerAccountMapper.selectByMap(map);
        if (customerAccounts.isEmpty()) {
            return Result.success("查询成功");
        }
        CustomerAccount customerAccount = customerAccounts.stream().findFirst().get();
        return Result.success(customerAccount, "查询成功");
    }

    @Override
    public Result<List<CustomerVO>> findList() {
        User currentUser = getCurrentUser();
        Assert.notNull(currentUser, "获取数据异常");
        if (!currentUser.getAccountType().equals(UserLoginType.ACCOUNT.getCode())) {
            return Result.success(Collections.EMPTY_LIST, "查询成功");
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
     * 获得客户列表
     * @param id 客户账号ID
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
        return Result.success(customerAccountBasicVOS, "查询成功");
    }
}
