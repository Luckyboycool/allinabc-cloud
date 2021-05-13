package com.allinabc.cloud.demo.controller;

import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.demo.service.LeaveService;
import com.allinabc.cloud.starter.form.param.BasicFormParam;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/18 17:29
 **/
@Api(tags = "请假Demo接口")
@RestController
@RequestMapping("/demo")
public class LeaveController{

    @Autowired
    private LeaveService leaveService;

//    @PostMapping("/add")
//    @ApiOperation(value = "新增请假申请")
//    public Result<?> add(@RequestBody LeaveDTO> request){
//        return leaveService.add(request);
//    }
//
//    @GetMapping("/find")
//    @ApiOperation(value = "查询请假申请")
//    public Result<LeaveVO> findByID(@RequestBody Request<LeaveDTO> request){
//        return leaveService.findByID(request);
//    }
//
//    @PutMapping("/modify")
//    @ApiOperation(value = "修改请假申请")
//    public Result<Void> modify(@RequestBody Request<LeaveDTO> request){
//        return leaveService.modify(request);
//    }
//
//    @DeleteMapping("/remove")
//    @ApiOperation(value = "删除请假申请")
//    public Result<Void> delete(@RequestBody Request<LeaveDTO> request){
//        return leaveService.delete(request);
//    }

    @RequestMapping("/insert")
    public Result<Void> add(BasicFormParam basicFormParam){
        return leaveService.add(basicFormParam);
    }


}
