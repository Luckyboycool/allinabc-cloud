package com.allinabc.cloud.admin.service;

import cn.hutool.core.lang.tree.Tree;
import com.allinabc.cloud.admin.pojo.dto.ResourceSearchDTO;
import com.allinabc.cloud.admin.pojo.po.Resource;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.common.mybatis.service.MybatisCommonService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2020-12-16
 */
public interface ResourceService extends MybatisCommonService<Resource> {

    List<String> selectPermissionByUser(String appCode, String userId);

    List<Resource> selectByUser(String appCode, User user);

    List<Resource> selectByRole(String appCode, String roleId);

    /**
     * 更新默认功能项
     * @param id
     * @param defaulted
     * @return
     */
    Result<Void> updateDefault(String id, String defaulted);

    /**
     *
     * @return
     */
    Result<List<Tree<String>>> findResultTree();

    /**
     *
     * @param userId
     * @return
     */
    Result<List<Tree<String>>> listEmpByUid(String userId);

    /**
     * 查询功能项列表
     * @param searchDTO
     * @return
     */
    IPage<Resource> listPage(ResourceSearchDTO searchDTO);

    /**
     * 查询功能项列表(用于授权)
     * @param userId
     * @return
     */
    Result<List<Tree<String>>> listByUid(String userId);

    /**
     * 查询功能项列表(非默认)
     * @return
     */
    Result<List<Tree<String>>> listPageNoDefault(String name);
}
