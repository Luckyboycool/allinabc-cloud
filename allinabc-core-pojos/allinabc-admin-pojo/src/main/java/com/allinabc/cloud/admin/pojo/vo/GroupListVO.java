package com.allinabc.cloud.admin.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/4 6:56 下午
 **/
@Data
public class GroupListVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "群组名称")
    private String groupName;


    @ApiModelProperty(value = "是否可用")
    private String isAvailable;


    @ApiModelProperty(value = "群组描述")
    private String description;

    @JsonIgnore
    private String usernames;


    /** 群组成员 */
    private List<String> usernameList;

    /** 群组成员ID列表 */
    @JsonIgnore
    private String depts;

    private List<String> deptList;
}
