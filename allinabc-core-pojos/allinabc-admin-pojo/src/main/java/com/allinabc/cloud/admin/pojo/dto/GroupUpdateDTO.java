package com.allinabc.cloud.admin.pojo.dto;

import com.allinabc.cloud.common.core.domain.BasicInfo;
import com.allinabc.cloud.org.pojo.vo.DeptBaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
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
public class GroupUpdateDTO extends BasicInfo {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "ID")
    @NotBlank(message = "ID不能为空")
    private String id;

    @ApiModelProperty(value = "程序代码")
    private String appCode;

    @ApiModelProperty(value = "群组名称")
    @NotBlank(message = "群组名称不能为空")
    private String groupName;

    @ApiModelProperty(value = "群组类型(1:主管群组)")
    private String type;

    @ApiModelProperty(value = "是否可用")
    @NotBlank(message = "是否可用不能为空")
    private String isAvailable;

    @ApiModelProperty(value = "是否隐藏")
    private String isHidden;

    @ApiModelProperty(value = "群组描述")
    @NotBlank(message = "群组描述不能为空")
    private String description;


    /** 群组成员 */
    //@JsonIgnoreProperties(value = {"createdBy", "updatedBy", "roles"})
    private List<SysUserDTO> members;

    /** 群组成员ID列表 */
    private List<GroupDeptDTO> depts;

}
