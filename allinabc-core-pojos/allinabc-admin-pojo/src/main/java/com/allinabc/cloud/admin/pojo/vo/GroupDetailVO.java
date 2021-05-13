package com.allinabc.cloud.admin.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/4 7:44 下午
 **/
@Data
public class GroupDetailVO implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "群组名称")
    private String groupName;


    @ApiModelProperty(value = "是否可用")
    private String isAvailable;

    @ApiModelProperty(value = "群组描述")
    private String description;

    private List<GroupUserDetailVO> userList;

    private List<GroupDeptDetailVO> deptList;

    @Data
    public static class GroupUserDetailVO {
        private String id;
        private String name;
    }
    @Data
    public static class GroupDeptDetailVO {
        private String id;
        private String name;
    }
}


