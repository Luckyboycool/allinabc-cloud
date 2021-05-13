package com.allinabc.cloud.admin.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/3/11 2:08 下午
 **/
@Data
public class RoleDetailVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "角色标识")
    @TableField("ROLE_KEY")
    private String roleKey;

    @ApiModelProperty(value = "角色名称")
    @TableField("ROLE_NAME")
    private String roleName;

    @ApiModelProperty(value = "是否可用")
    @TableField("IS_AVAILABLE")
    private String isAvailable;

    /**
     * 备注
     */
    @ApiModelProperty(value = "是否可用")
    private String remark;

    private List<RUserVO> users;

    private List<RGroupVO> groups;


    @Data
    public static class RUserVO {
        private String id;
        private String name;
    }

    @Data
    public static class RGroupVO {
        private String id;
        private String groupName;
    }
}
