package com.allinabc.cloud.admin.api.service.api;

import com.allinabc.cloud.org.pojo.vo.EmployeeBasicVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/29 10:58 上午
 **/
public interface ApiGroupService {
    /**
     * 查询群组下的员工列表
     * @param groupName
     * @return
     */
    List<EmployeeBasicVO> findEmployeeByGroupName(@RequestParam("groupName") String groupName);
}
