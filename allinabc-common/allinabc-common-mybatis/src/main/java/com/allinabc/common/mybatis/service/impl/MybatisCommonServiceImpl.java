package com.allinabc.common.mybatis.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.utils.CamelTool;
import com.allinabc.cloud.common.core.utils.ServletUtils;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.common.web.pojo.param.PropertyFilter;
import com.allinabc.cloud.common.web.pojo.param.QueryParam;
import com.allinabc.cloud.starter.redis.util.RedisHelper;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.MybatisCommonService;
import com.allinabc.common.mybatis.util.PageHelper;
import com.allinabc.common.mybatis.util.PropertyFilterHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.allinabc.cloud.common.core.utils.constant.Constants.ACCESS_TOKEN;
import static com.allinabc.cloud.common.core.utils.constant.Constants.TOKEN;


public abstract class MybatisCommonServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<BaseMapper<T>, T> implements MybatisCommonService<T> {

    private static final Logger logger = LoggerFactory.getLogger(MybatisCommonServiceImpl.class);

    protected abstract MybatisCommonBaseMapper<T> getRepository();
    
    @Resource
    private RedisHelper redis;

    @Override
    public boolean beforeInsert(T entity) {
        if(BasicInfo.class.isAssignableFrom(getEntityClass())) {
            BasicInfo basic = (BasicInfo) entity;
            Assert.isTrue(Strings.isNullOrEmpty(basic.getId()), "id is not null");
            //暂时写成这样，后续改善
            basic.setCreatedBy(getCurrentUserId());
            basic.setCreateTm(new Date());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T insert(T entity) {
        Assert.isTrue(this.beforeInsert(entity), "error occurred before insert");
        getRepository().insert(entity);
        Assert.isTrue(this.afterInsert(entity), "error occurred after insert");
        return (T) entity;
    }

    @Override
    public boolean afterInsert(T entity) {
        return true;
    }

    @Override
    public void batchInsert(List<T> entities) {
        if(null != entities && entities.size() != 0) {
            entities.forEach(this::insert);
        }
    }

    @Override
    public boolean beforeDeleteById(String id) {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        Assert.isTrue(this.beforeDeleteById(id), "error occurred before deleteById");
        getRepository().deleteById(id);
        Assert.isTrue(this.afterDeleteById(id), "error occurred after deleteById");
    }

    @Override
    public boolean afterDeleteById(String id) {
        return true;
    }

    @Override
    public void delete(T queryParam) {
//        QueryWrapper wrapper = new QueryWrapper(this.currentModelClass());
//        QueryWrapper wrapper = new QueryWrapper((Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);;
//        wrapper.setEntity(queryParam);
        getRepository().delete(new QueryWrapper(this.currentModelClass()).setEntity(queryParam));
    }

    @Override
    public void deleteByIds(String ids) {
        String[] idArr = Convert.toStrArray(ids);
        if(null == idArr || idArr.length == 0) {
            return;
        }
        List idList = Arrays.stream(idArr).collect(Collectors.toList());
        this.deleteByIds(idList);
    }

    @Override
    public void deleteByIds(String[] idArr) {
        if(null == idArr || idArr.length == 0) {
            return;
        }
        List<String> idList = Lists.newArrayList(idArr);
        this.deleteByIds(idList);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        if(null == ids || ids.size() == 0) {
            return;
        }
        ids.forEach(this::deleteById);
    }

    @Override
    public boolean beforeUpdate(String id, T entity) {
        if(BasicInfo.class.isAssignableFrom(getEntityClass())) {
            BasicInfo basic = (BasicInfo) entity;
            basic.setUpdatedBy(getCurrentUserId());
            basic.setUpdateTm(new Date());
            basic.setId((String) id);
//            T dbEntity = this.selectById(id);
//            BeanMergeUtils.merge(entity, dbEntity);
//            BeanMergeUtils.merge(dbEntity, entity);
//            entityManager.clear();
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T update(String id, T entity) {
        this.beforeUpdate(id, entity);
        getRepository().updateById(entity);
        this.afterUpdate(id, entity);
        return (T) entity;
    }

    @Override
    public boolean afterUpdate(String id, T entity) {
        return true;
    }

    @Override
    public T selectById(String id) {
        return getRepository().selectById(id);
    }

    @Override
    public List<T> selectByIds(List<String> ids) {
        if(null == ids || ids.size() == 0){
            return null;
        }
        List<T> retList = Lists.newArrayList();
        ids.forEach(i -> {
            T tmp = this.selectById(i);
            if(null != tmp) {
                retList.add(tmp);
            }
        });
        return retList;
    }

    @Override
    public boolean beforeSelectList(QueryParam param) {
        return true;
    }

    @Override
    public boolean afterSelectList(QueryParam param, List<T> page) {
        return true;
    }

    @Override
    public List<T> selectList(List<PropertyFilter> filters) {
        QueryParam param = new QueryParam(filters);
        Assert.isTrue(this.beforeSelectList(param), "error occurred before selectList");
        QueryWrapper queryWrapper = PropertyFilterHelper.createQueryWrapper(param);
        List list = getRepository().selectList(queryWrapper);
        Assert.isTrue(this.afterSelectList(param, list), "error occurred after selectList");

        return list;
    }

    @Override
    public List<T> selectList(QueryParam param) {
        Assert.isTrue(this.beforeSelectList(param), "error occurred before selectList");
        QueryWrapper queryWrapper = PropertyFilterHelper.createQueryWrapper(param);
        List list = getRepository().selectList(queryWrapper);
        Assert.isTrue(this.afterSelectList(param, list), "error occurred after selectList");
        return list;
    }

    @Override
    public List<T> selectList(T queryParam) {
        QueryWrapper wrapper = new QueryWrapper((Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);;
        wrapper.setEntity(queryParam);
        return getRepository().selectList(wrapper);
    }

    @Override
    public Page<T> findPage(int pageNo, int pageSize, List<PropertyFilter> filters) {
        QueryParam param = new QueryParam(filters);
        Assert.isTrue(this.beforeFindPage(param), "error occurred before findPage");
        QueryWrapper queryWapper = PropertyFilterHelper.createQueryWrapper(param);
        IPage iPage = getRepository().selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNo, pageSize), queryWapper);
        Page rPage = PageHelper.convert(iPage);
        Assert.isTrue(this.afterFindPage(param, rPage), "error occurred after findPage");
        return rPage;
    }

    @Override
    public Page<T> findPage(int pageNo, int pageSize, QueryParam param) {
        Assert.isTrue(this.beforeFindPage(param), "error occurred before findPage");
        QueryWrapper queryWapper = PropertyFilterHelper.createQueryWrapper(param);
        IPage iPage = getRepository().selectPage(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNo, pageSize), queryWapper);
        Page rPage = PageHelper.convert(iPage);
        Assert.isTrue(this.afterFindPage(param, rPage), "error occurred after findPage");
        return rPage;
    }

    @Override
    public boolean beforeFindPage(QueryParam param) {
        return true;
    }

    @Override
    public boolean afterFindPage(QueryParam param, Page<T> page) {
        return true;
    }

    @Override
    public User getCurrentUser(){
        User retUser = new User();
        String token = null;
        if(null != ServletUtils.getRequest()){
            HttpServletRequest request = ServletUtils.getRequest();
            token = request.getHeader(TOKEN);
        }

        if(!Strings.isNullOrEmpty(token)) {
            retUser = redis.getBean(ACCESS_TOKEN + token, User.class);
        }
        return retUser;
    }

    @Override
    public String getCurrentUserId() {
        User user = this.getCurrentUser();
        return null != user ? user.getId() : null;
    }

    @Override
    public Class<T> getEntityClass() {
        return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }








    @Override
    public int checkExist(String tableName,String fieldNames,String value,String id){
        QueryWrapper queryWrapper = new QueryWrapper();
        ConcurrentHashMap<String, Object> hs = new ConcurrentHashMap<>();
        hs.put(fieldNames,value);
        if(!StringUtils.isEmpty(id)){
            hs.put("id",id);
        }
        queryWrapper.allEq(hs);
        return getRepository().countNum(queryWrapper, tableName);

    }


    @Override
    public void export(String type ,QueryParam queryParam, HttpServletResponse response) {
        OutputStream outputStream = null;
        try {
            List<T> list ;
            if(queryParam==null){
                list = getRepository().selectList(null) ;
            }else {
                list = selectList(queryParam);
            }

            if(type.equals(Constants.EXPORT_WORD)){
                //导出成word ！！！支持不友好(目前只支持模板word导出)  此功能可由各模块单独实现
                 //WordExportUtil.exportWord07();

            }else{
                //导出成excel
                String table_name = CamelTool.humpToLine2(this.entityClass.getSimpleName().replace("Mapper","")).substring(1);
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(table_name,"data"),(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0], list);
                response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(table_name+".xlsx", "UTF-8"));
                outputStream = response.getOutputStream();
                workbook.write(outputStream);
            }
        }catch (Exception e){
            logger.error("下载异常"+e.getMessage(),e);
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result commonImport(String type, MultipartFile file) {
        if(type.equals(Constants.EXPORT_WORD)){
            //
        }else{
            //Excel导入
            try {
                ImportParams params = new ImportParams();
                params.setTitleRows(1);
                ExcelImportResult<T> importResult = ExcelImportUtil.importExcelMore(file.getInputStream(), (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0], params);
                List<T> list = importResult.getList();
                logger.info(JSONUtil.toJsonStr(list));
                list.forEach(s->{
                    getRepository().insert(s);
                });

            } catch (Exception e) {
                logger.error("上传文件报错："+e.getMessage(),e);
                throw new RuntimeException("导入文件失败");
            }

        }
        return Result.success("导入成功");
    }

}
