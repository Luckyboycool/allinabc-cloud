package com.allinabc.cloud.admin.api.service.api;

import com.allinabc.cloud.admin.pojo.dto.UserDTO;
import com.allinabc.cloud.admin.pojo.po.SysUser;

import java.util.List;

public interface ApiUserService {

    SysUser get(String userId);

    SysUser getUserByName(String userName);

    void updateLoginRecord(UserDTO user);

    List<String> selectUserIdsHasRoles(List<String> roleIds);

    List<String> selectUserIdsInDeparts(List<String> departIds);

}
