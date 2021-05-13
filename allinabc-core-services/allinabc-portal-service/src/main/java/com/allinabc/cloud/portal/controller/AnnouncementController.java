package com.allinabc.cloud.portal.controller;

import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.portal.pojo.dto.AnnouncementInsertDTO;
import com.allinabc.cloud.portal.pojo.dto.AnnouncementPageDto;
import com.allinabc.cloud.portal.pojo.dto.AnnouncementUpdateDTO;
import com.allinabc.cloud.portal.pojo.po.Announcement;
import com.allinabc.cloud.portal.pojo.vo.AnnouncementDetailVO;
import com.allinabc.cloud.portal.pojo.vo.AnnouncementVO;
import com.allinabc.cloud.portal.service.AnnouncementService;
import com.allinabc.common.mybatis.controller.MybatisBaseCrudController;
import com.allinabc.common.mybatis.util.PageHelper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2/25/21 4:58 PM
 **/
@Api(tags = "公告配置")
@RestController
@RequestMapping("/annou")
public class AnnouncementController extends MybatisBaseCrudController<Announcement> {
    @Autowired
    private AnnouncementService announcementService;

    /**
     * 添加公告
     * @return
     */
    @ApiOperation(value = "添加公告")
    @PostMapping("/cus/insert")
    public Result<Void> insert(@RequestBody @Validated AnnouncementInsertDTO insertDTO) {
        return announcementService.insert(insertDTO);
    }

    /**
     * 更新公告
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "更新公告")
    @PutMapping("/cus/update")
    public Result<Void> update(@RequestBody @Validated AnnouncementUpdateDTO updateDTO) {
        return announcementService.update(updateDTO);
    }

    /**
     * 查询公告
     * @param id
     * @return
     */
    @ApiOperation(value = "查询公告详情")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String", paramType = "query")
    @GetMapping("/cus/get")
    public Result<AnnouncementDetailVO> getById(@RequestParam("id") String id) {
        return announcementService.getById(id);
    }

    /**
     * 查询公告列表(包含所有)
     * @param announcementPageDto
     * @return
     */
    @ApiOperation(value = "查询所有公告列表")
    @PostMapping("/find/list")
    public Result<Page<AnnouncementVO>> findList(@RequestBody AnnouncementPageDto announcementPageDto) {
        IPage<AnnouncementVO> iPage = announcementService.findList(announcementPageDto);
        Page page = PageHelper.convert(iPage);
        return Result.success(page, "查询成功");
    }

    /**
     * 查询有效公告列表
     * @return
     */
    @ApiOperation(value = "查询有效公告列表")
    @PostMapping("find/visible_list")
    public Result<Page<AnnouncementVO>> findVisibleList(@RequestBody AnnouncementPageDto announcementPageDto) {
        IPage<AnnouncementVO> iPage = announcementService.findVisibleList(announcementPageDto);
        Page page = PageHelper.convert(iPage);
        return Result.success(page, "查询成功");
    }

    /**
     * 查询前5个有效公告
     * @return
     */
    @ApiOperation(value = "查询前5个有效公告")
    @GetMapping("/limit_info")
    public Result<List<AnnouncementVO>> limitInfoByNum() {
        Integer num = 5;
        return announcementService.limitInfoByNum(num);
    }

    @Override
    protected CommonService<Announcement> getService() {
        return announcementService;
    }
}
