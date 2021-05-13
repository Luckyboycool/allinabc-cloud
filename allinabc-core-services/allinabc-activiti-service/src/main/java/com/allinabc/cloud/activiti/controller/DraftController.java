package com.allinabc.cloud.activiti.controller;

import com.allinabc.cloud.activiti.pojo.dto.DraftParam;
import com.allinabc.cloud.activiti.pojo.vo.DraftVO;
import com.allinabc.cloud.activiti.service.DraftService;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/3 13:38
 **/
@RestController
@RequestMapping("/draft")
public class DraftController {

    @Autowired
    private DraftService draftService;

    @PostMapping("/list")
    @ApiOperation(value = "查询草稿状态表单")
    public Result<Page<DraftVO>> findDraftList(@RequestBody DraftParam draftParam){
        Page<DraftVO> draftList = draftService.findDraftList(draftParam);
        return Result.success(draftList);
    }
}
