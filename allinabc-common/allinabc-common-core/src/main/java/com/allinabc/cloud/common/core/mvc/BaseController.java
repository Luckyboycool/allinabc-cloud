package com.allinabc.cloud.common.core.mvc;

import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.common.web.pojo.param.QueryParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description  BaseController接口规范
 * @Author wangtaifeng
 * @Date 2020/12/18 10:46
 **/
public abstract class BaseController<T> {

     @ApiOperation("新增")
     @RequestMapping(value = "/insert", method = RequestMethod.POST)
     public Result<T> insert(@RequestBody T entity) {
          T t = getService().insert(entity);
          return Result.success(t,"success");
     }

     @ApiOperation("删除")
     @RequestMapping(value = "/delete/{ids}", method = RequestMethod.DELETE)
     public Result<Void> remove(@PathVariable("ids") String ids) {
          getService().deleteByIds(ids);
          return Result.success(null,"success");
     }

     @ApiOperation("修改")
     @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
     public Result<T> edit(@PathVariable("id") String id, @RequestBody T entity) {
          getService().update(id, entity);
          return Result.success(entity, "update success");
     }

     @ApiOperation("单个查询")
     @RequestMapping(value = "/get/id/{id}", method = RequestMethod.GET)
     public Result<T> get(@PathVariable("id") String id) {
          T entity = (T) getService().selectById(id);
          return Result.success(entity);
     }

     @ApiOperation("列表查询")
     @RequestMapping(value = "/list", method = {RequestMethod.POST})
     public Result<List<T>> findList(@RequestBody QueryParam queryParam) {
          List<T> list = getService().selectList(queryParam);
          return Result.success(list, "find all success");
     }

     @ApiOperation("分页查询")
     @RequestMapping(value = "/page/{pageNo}/{pageSize}", method = {RequestMethod.POST})
     public Result<Page<T>> findPage(@PathVariable int pageNo, @PathVariable int pageSize, @RequestBody QueryParam queryParam) {
          Page<T> page = getService().findPage(pageNo, pageSize, queryParam);
          return Result.success(page,"success");
     }

     @ApiOperation("通用导出文件")
     @GetMapping("/export/{type}")
     public void export(@PathVariable String type, @RequestBody(required = false) QueryParam queryParam, HttpServletResponse response) {
          getService().export(type,queryParam,response);
     }

     @ApiOperation("通用导入文件")
     @PostMapping("/import/{type}")
     public Result<?> commonImport(@PathVariable String type, @RequestParam("file") MultipartFile file) {
          return getService().commonImport(type,file);
     }

     protected abstract CommonService<T> getService();


}
