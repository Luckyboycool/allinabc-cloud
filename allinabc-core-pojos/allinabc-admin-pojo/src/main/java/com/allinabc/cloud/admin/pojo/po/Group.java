package com.allinabc.cloud.admin.pojo.po;

import com.allinabc.cloud.admin.pojo.dto.GroupDeptDTO;
import com.allinabc.cloud.admin.pojo.dto.SysUserDTO;
import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangtaifeng-mybatis-plus-generator
 * @since 2020-12-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="AdmGroup对象", description="")
@TableName(value = "ADM_GROUP")
public class Group extends BasicInfo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "程序代码")
    @TableField("APP_CODE")
    private String appCode;

    @ApiModelProperty(value = "群组名称")
    @TableField("GROUP_NAME")
    private String groupName;

    @ApiModelProperty(value = "群组类型(1:主管群组)")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty(value = "是否可用")
    @TableField("IS_AVAILABLE")
    private String isAvailable;

    @ApiModelProperty(value = "是否隐藏")
    @TableField("IS_HIDDEN")
    private String isHidden;

    @ApiModelProperty(value = "群组描述")
    @TableField("DESCRIPTION")
    private String description;


    /** 群组成员 */
   // @OneToMany(fetch = FetchType.EAGER)
   // @JoinTable(name = "SYS_GROUP_USER", joinColumns = {@JoinColumn(name = "GROUP_ID")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    @TableField(exist = false)
    //@JsonIgnoreProperties(value = {"createdBy", "updatedBy", "roles"})
    private List<SysUserDTO> members;

    /** 群组成员ID列表 */
//    @TableField(exist = false)
//    private List<String> memberIds;

    //@TableField(exist = false)
    //private List<String> deptIds;

    @TableField(exist = false)
    private List<GroupDeptDTO> depts;


}
