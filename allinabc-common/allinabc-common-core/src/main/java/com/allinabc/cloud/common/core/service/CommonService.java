package com.allinabc.cloud.common.core.service;


import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.common.web.pojo.param.PropertyFilter;
import com.allinabc.cloud.common.web.pojo.param.QueryParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * 通用service方法
 */
public interface CommonService<T> {

    /**
     * 在新增操作之前执行，返回false终止执行
     * @param entity 参数实体
     * @return 前置处理结果(false会停止继续执行)
     */
    boolean beforeInsert(T entity);

    /**
     * 新增操作
     * @param entity 参数实体
     * @return 持久化实体
     */
    T insert(T entity);

    /**
     * 在新增操作之后执行，返回false终止执行
     * @param entity 参数实体
     * @return 后置处理结果(false会停止继续执行)
     */
    boolean afterInsert(T entity);

    /**
     * 批量插入
     * @param entities
     * @return
     */
    void batchInsert(List<T> entities);

    /**
     * 在删除操作之前执行，返回false终止操作
     * @param id 对象的主键
     * @return 前置处理结果(false会停止继续执行)
     */
    boolean beforeDeleteById(String id);

    /**
     * 根据主键删除对象
     * @param id 对象的主键
     */
    void deleteById(String id);

    /**
     * 在删除操作之后执行
     * @param id 对象的主键
     * @return 后置处理结果(false会停止继续执行)
     */
    boolean afterDeleteById(String id);

    /**
     * 根据多个主键删除对象
     * @param ids 多个主键(逗号分隔)
     */
    void deleteByIds(String ids);

    /**
     * 根据多个主键删除对象
     * @param ids 多个主键(数组)
     */
    void deleteByIds(String[] ids);

    /**
     * 根据多个主键删除对象
     * @param ids 多个主键(列表)
     */
    void deleteByIds(List<String> ids);

    /**
     * 条件删除
     * @param param 条件
     */
    void delete(T param);

    /**
     * 在更新之前执行
     * @param id 主键
     * @param entity 参数实体
     * @return 前置处理结果(false会停止继续执行)
     */
    boolean beforeUpdate(String id, T entity);

    /**
     * 更新实体
     * @param id 主键
     * @param entity 参数实体
     * @return 持久化实体
     */
    T update(String id, T entity);

    /**
     * 在更新之后执行
     * @param id 主键
     * @param entity 参数实体
     * @return 后置处理结果(false会停止继续执行)
     */
    boolean afterUpdate(String id, T entity);

    /**
     * 根据主键获取实体
     * @param id 主键
     * @return 持久化实体
     */
    T selectById(String id);

    /**
     * 根据多个主键获取多个实体
     * @param ids 主键(列表)
     * @return 持久化实体集合
     */
    List<T> selectByIds(List<String> ids);

    /**
     * 通用列表查询(where)
     * @param filters 查询条件
     * @return 持久化实体集合
     */
    List<T> selectList(List<PropertyFilter> filters);

    /**
     * 通用列表查询(where/order)
     * @param param 查询条件
     * @return 持久化实体集合
     */
    List<T> selectList(QueryParam param);

    /**
     * 条件查询列表
     * @param param 条件
     * @return
     */
    List<T> selectList(T param);

    /**
     * 在检索之前执行，返回false终止操作
     * @param param 查询参数
     * @return 前置处理结果(false会停止继续执行)
     */
    boolean beforeSelectList(QueryParam param);

    /**
     * 在检索之后执行，返回false终止操作
     * @param param 查询参数
     * @param page 分页对象
     * @return 后置处理结果(false会停止继续执行)
     */
    boolean afterSelectList(QueryParam param, List<T> page);

    /**
     * 在检索之前执行，返回false终止操作
     * @param param 查询参数
     * @return 前置处理结果(false会停止继续执行)
     */
    boolean beforeFindPage(QueryParam param);

    /**
     * 带参数分页查询
     * @param pageNo
     * @param pageSize
     * @param params
     * @return
     */
    Page<T> findPage(int pageNo, int pageSize, QueryParam params);

    /**
     * 通用分页查询(where)
     * @param pageNo 页码
     * @param pageSize 页面条目数
     * @param filters where
     * @return 持久化实体集合
     */
    Page<T> findPage(int pageNo, int pageSize, List<PropertyFilter> filters);

    /**
     * 分页查询后执行
     * @param param
     * @param page
     * @return
     */
    boolean afterFindPage(QueryParam param, Page<T> page);

    /**
     * 获取当前登录用户(by token)
     * @return 当前登录用户
     */
    User getCurrentUser();

    /**
     * 获取当前登录用户ID(by token)
     * @return 当前登录用户ID
     */
    String getCurrentUserId();

    /**
     * 获取实体的类
     * @return 类
     */
    Class<T> getEntityClass();

    /**
     * 校验 XXX字段值为 XXX 在XXX表存在
     * tableName  表名
     * fieldNames 字段名
     * value      条件值
     * @return int
     */
    int checkExist(String tableName, String fieldNames, String value, String id);

    /**
     *
     * 导出（word/excel）
     * @param type
     * @param param
     * @param response
     */
    void export(String type,QueryParam param, HttpServletResponse response);

    /**
     * 通用导入
     * @param type 文件类型
     * @param file
     * @return
     */
    Result commonImport(String type, MultipartFile file);

}
