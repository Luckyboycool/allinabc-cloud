package com.allinabc.cloud.org.controller;

import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.org.pojo.dto.CustomerAccountCreatDTO;
import com.allinabc.cloud.org.pojo.dto.CustomerAccountSearchDTO;
import com.allinabc.cloud.org.pojo.dto.CustomerAccountUpdateDTO;
import com.allinabc.cloud.org.pojo.po.CustomerAccount;
import com.allinabc.cloud.org.pojo.po.CustomerData;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountBasicVO;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountDetailVO;
import com.allinabc.cloud.org.pojo.vo.CustomerAccountVO;
import com.allinabc.cloud.org.pojo.vo.CustomerVO;
import com.allinabc.cloud.org.service.CustomerAccountService;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import com.allinabc.common.mybatis.util.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Simon.Xue
 * @date 2/24/21 10:35 AM
 **/
@Api(tags = "客户账号")
@RestController
@RequestMapping("/account")
public class CustomerAccountController extends MybatisBaseCrudController {

    @Autowired
    private CustomerAccountService customerAccountService;

    @Override
    protected CommonService getService() {
        return customerAccountService;
    }

    /**
     * 添加客户账号
     * @param creatDTO
     * @return
     */
    @ApiOperation(value = "创建客户账号", notes = "创建客户账号并授权")
    @PostMapping("/save")
    public Result<Void> add(@RequestBody @Validated CustomerAccountCreatDTO creatDTO) {
        return customerAccountService.add(creatDTO);
    }

    /**
     * 更新客户账号
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "更新客户账号", notes = "")
    @PutMapping("/update/id")
    public Result<Void> updateById(@RequestBody @Validated CustomerAccountUpdateDTO updateDTO) {
        return customerAccountService.updateById(updateDTO);
    }

    /**
     * 查询客户账号详情
     * @param id
     * @return
     */
    @ApiOperation(value = "查询客户账号详情")
    @ApiImplicitParam(name = "id", value = "客户ID", required = true, paramType = "path")
    @GetMapping("/get_detail/id/{id}")
    public Result<CustomerAccountDetailVO> getDetailById(@PathVariable("id") String id) {
        return customerAccountService.getDetailById(id);
    }

    /**
     * 查询客户列表
     * @param searchDTO
     * @return
     */
    @ApiOperation(value = "查询客户账号列表")
    @PostMapping("/find/page")
    public Result<Page<CustomerAccountVO>> findPage(@RequestBody CustomerAccountSearchDTO searchDTO) {
        Page<CustomerAccountVO> page = PageHelper.convert(customerAccountService.findPage(searchDTO));
        return Result.success(page, "查询成功");
    }

    /**
     * 查询客户账号
     * 远程调用(登录接口)
     * @param username
     * @return
     */
    @GetMapping("/get_by_username")
    public Result<CustomerAccount> getByUsername(@RequestParam("username") String username) {
        return customerAccountService.getByUsername(username);
    }

    /**
     * 查询客户账号下的客户列表
     * @return
     */
    @GetMapping("/find/list")
    public Result<List<CustomerVO>> findCustomerList() {
        return customerAccountService.findList();
    }


    /**
     * 获得模拟客户信息列表
     * @return
     */
    @PostMapping("/find_customer_data")
    public Result<List<CustomerData>> findCustomerData(@RequestBody JSONObject json) {
        List<CustomerData> customerDataList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            CustomerData customerData = new CustomerData();
            customerData.setId(String.valueOf(i));
            customerData.setName("小明"+i);
            customerData.setCompanyName("众壹云"+i);
            customerData.setCompanyAddress("上海浦东新区张江电子港"+i+"号");
            customerDataList.add(customerData);
        }
        String name = json.getString("name");
        if (StringUtils.isNotEmpty(name)) {
            customerDataList = customerDataList.stream().filter(a -> a.getName().contains(name)).collect(Collectors.toList());
        }
        return Result.success(customerDataList);
    }

    /**
     * 查询指定ID的客户子账号和主账号列表
     * @param id
     * @return
     */
    @ApiOperation(value = "查询指定ID的客户子账号和主账号列表")
    @GetMapping("/find_child_parent")
    public Result<List<CustomerAccountBasicVO>> findChildAndParentById(@RequestParam("id") String id) {
        return customerAccountService.findChildAndParentById(id);
    }


}
