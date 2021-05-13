package com.allinabc.cloud.admin.controller;

import cn.hutool.core.lang.Assert;
import com.allinabc.cloud.admin.pojo.dto.CidbUpdateDTO;
import com.allinabc.cloud.admin.pojo.dto.QueryCidbParam;
import com.allinabc.cloud.admin.pojo.vo.CidbBasicInfoVo;
import com.allinabc.cloud.admin.pojo.vo.CidbBasicSimpleVO;
import com.allinabc.cloud.admin.service.CidbBasicInfoService;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.util.PageHelper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * techid 控制器
 * </p>
 *
 * @author gaoxiang
 * @since 2021-3-3
 */
@Api(tags = "cidb信息")
@RequestMapping("cidb")
@RestController
public class CidbController {

    @Autowired
    private CidbBasicInfoService cidbBasicInfoService;

    @ApiOperation(value = "cidb查询", notes = "cidb查询")
    @ApiImplicitParam(name = "queryCidbParam",value = "cidb查询入参",required=true)
    @PostMapping("/page/{pageNo}/{pageSize}")
    public Result<Page<CidbBasicInfoVo>> findList(@RequestBody QueryCidbParam queryCidbParam, @PathVariable int pageNo, @PathVariable int pageSize) {
        IPage<CidbBasicInfoVo> page = cidbBasicInfoService.findList(queryCidbParam, pageNo, pageSize);
        return Result.success(PageHelper.convert(page),"查询成功");
    }


    @ApiOperation(value = "cidb更新")
    @PostMapping("/update")
    public Result update(@RequestBody @Validated CidbUpdateDTO cidbUpdateDTO) {
        return cidbBasicInfoService.update(cidbUpdateDTO);
    }

    /**
     * 获得客户的列表(未分页)
     * @param custCode
     * @param custName
     * @return
     */
    @ApiOperation(value = "获得客户的列表(未分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "custCode", value = "客户三位代码"),
            @ApiImplicitParam(name = "custName", value = "客户名称")
    })
    @GetMapping("/find_all")
    public Result<List<CidbBasicSimpleVO>> findAll(@RequestParam(name = "custCode", required = false) String custCode,
                                                   @RequestParam(name = "custName", required = false) String custName) {
        return Result.success(cidbBasicInfoService.findAll(custCode, custName), "查询成功");
    }


    /**
     * 获得客户信息
     * @param custCode 根据客户CODE
     * @return
     */
    @ApiOperation(value = "获得客户信息")
    @ApiImplicitParam(name = "custCode", value = "客户三位代码")
    @GetMapping("get_info")
    public Result<CidbBasicSimpleVO> getByCustCode(@RequestParam("custCode") String custCode) {
        return cidbBasicInfoService.getByCustCode(custCode);
    }


    /**
     * 查询部分客户列表
     * 远程调用使用
     * @param custCodes
     * @return
     */
    @GetMapping("/find_by_cust_codes")
    public Result<List<CidbBasicSimpleVO>> findListByCustCodes(@RequestParam("custCodes") List<String> custCodes) {
        Assert.notEmpty(custCodes, "custCodes must not null");
        return cidbBasicInfoService.findListByCustCodes(custCodes);
    }
}
