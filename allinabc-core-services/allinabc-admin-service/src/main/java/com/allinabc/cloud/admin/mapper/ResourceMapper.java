package com.allinabc.cloud.admin.mapper;

import com.allinabc.cloud.admin.pojo.po.Resource;
import com.allinabc.cloud.admin.pojo.vo.ResourceRoleVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-16
 */
public interface ResourceMapper extends MybatisCommonBaseMapper<Resource> {

    List<Resource> selectResourcesByUserId(@Param("appCode")String appCode, @Param("userId") String userId);

    List<Resource> selectResourcesNormalAll(@Param("appCode")String appCode);

    List<Resource> selectResourcesByRoleId(@Param("appCode")String appCode, @Param("roleId")String roleId);

    /**
     * 查看员工下的资源
     * @param userId
     * @return
     */
    List<Resource> listEmpByUid(@Param("userId") String userId);

    /**
     * 查看SysUser下的资源列表
     * @param userId
     * @return
     */
    List<Resource> listResUserByUid(@Param("userId") String userId);

    /**
     * 查询部门下的资源列表
     * @param deptId
     * @return
     */
    List<Resource> listResDeptById(@Param("deptId") String deptId);

    /**
     * 查询群组下的资源列表
     * @param userId
     * @return
     */
    List<Resource> listResGroupByUserId(@Param("userId") String userId);

    /**
     * 查询资源列表
     * @param page
     * @param resKey
     * @param resName
     * @param path
     * @return
     */
    IPage<Resource> pageBySearch(Page<Resource> page, @Param("resKey") String resKey,
                                 @Param("resName") String resName, @Param("path") String path);

    /**
     * 查询资源列表(带roleId)
     * @param roleIds
     * @return
     */
    List<ResourceRoleVO> findByRoleIds(@Param("roleIds") List<String> roleIds);

    /**
     * 查询群组ALL下的权限
     * @param groupAllId
     * @return
     */
    List<Resource> searchGroupAll(@Param("groupAllId") String groupAllId);
}
