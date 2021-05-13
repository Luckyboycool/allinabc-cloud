package com.allinabc.cloud.admin.pojo.dto;

import cn.hutool.core.collection.CollUtil;
import com.allinabc.cloud.common.core.domain.BasicInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
public class GroupInsertDTO extends BasicInfo {

    private static final long serialVersionUID = 1L;

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
    private List<GroupDeptDTO> deptDtoList;

    private List<GroupDeptInsertDto> depts;

    public void setDepts(List<GroupDeptInsertDto> depts) {
        this.depts = depts;
        if (CollUtil.isNotEmpty(depts)) {
            List<GroupDeptDTO> collect = depts.stream().map(a -> {
                GroupDeptDTO groupDeptDTO = new GroupDeptDTO();
                groupDeptDTO.setId(a.getDepartId());
                groupDeptDTO.setName(a.getDepartName());
                return groupDeptDTO;
            }).collect(Collectors.toList());
            this.deptDtoList = collect;
        }
    }

    @Data
    private static class GroupDeptInsertDto implements Serializable {
        private String departId;
        private String departName;
    }
}
