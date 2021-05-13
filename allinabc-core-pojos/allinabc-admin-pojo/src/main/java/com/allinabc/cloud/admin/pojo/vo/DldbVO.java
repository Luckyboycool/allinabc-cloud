package com.allinabc.cloud.admin.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Simon.Xue
 * @date 2021/4/6 2:30 下午
 **/
@Data
public class DldbVO implements Serializable {

    private String id;
    /**
     * 客户代码
     */
    @ApiModelProperty(value = "客户代码")
    private String custCode;
    /**
     * Customer Code all
     */
    @ApiModelProperty(value = "Customer Code all")
    private String custName;
    /**
     * Foundry 区域
     */
    @ApiModelProperty(value = "Foundry 区域")
    private String foundryLocal;
    /**
     * GTA Foundry厂
     */
    @ApiModelProperty(value = "GTA Foundry厂")
    private String fab;
    /**
     * Inch
     */
    @ApiModelProperty(value = "Inch")
    private String inch;
    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String deviceName;
    /**
     * 技术分类
     */
    @ApiModelProperty(value = "技术分类")
    private String technologyFamily;
    /**
     * 技术节点
     */
    @ApiModelProperty(value = "技术节点")
    private String technologyNode;
    /**
     * 技术类型
     */
    @ApiModelProperty(value = "技术类型")
    private String tech;
    /**
     * 特征描述/平台
     */
    @ApiModelProperty(value = "特征描述/平台")
    private String characterization;
    /**
     * _P(Poly)_M(Metal)
     */
    @ApiModelProperty(value = "_P(Poly)_M(Metal)")
    private String pM;
    /**
     * 核心电压:
     */
    @ApiModelProperty(value = "核心电压")
    private String coreVoltage;
    /**
     * IO器件电压:
     */
    @ApiModelProperty(value = "IO器件电压")
    private String ioDeviceVoltage;
    /**
     * 高压器件电压:
     */
    @ApiModelProperty(value = "高压器件电压")
    private String hvDeviceVoltage;
    /**
     * Vds (击穿)/Vce:
     */
    @ApiModelProperty(value = "Vds (击穿)/Vce")
    private String vdsVce;
    /**
     * 芯片电流:
     */
    @ApiModelProperty(value = "芯片电流")
    private String chipCurrent;
    /**
     * 产品应用
     */
    @ApiModelProperty(value = "产品应用")
    private String productApplication;
    /**
     * 主要应用范围:
     */
    @ApiModelProperty(value = "主要应用范围")
    private String primaryApplication;
    /**
     * 是否有初测
     */
    @ApiModelProperty(value = "是否有初测")
    private String needCpTest;
    /**
     * 是否有减薄
     */
    @ApiModelProperty(value = "是否有减薄")
    private String needBackGrinding;
    /**
     * 是否有蒸金
     */
    @ApiModelProperty(value = "是否有蒸金")
    private String needBackMetal;
    /**
     * 是否有划片
     */
    @ApiModelProperty(value = "是否有划片")
    private String needDicing;
    /**
     * Sub采用模式
     */
    @ApiModelProperty(value = "Sub采用模式")
    private String subPurchaseModel;
    /**
     * 芯片代号(12NC of die)
     */
    @ApiModelProperty(value = "芯片代号(12NC of die)")
    private String twelveNcOfDie;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTm;
    /**
     * 更新者
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTm;
    /**
     * 备注
     */
    private String remark;
    /**
     * Die长度(mm)
     */
    @ApiModelProperty(value = "Die长度(mm)")
    private String dieLength;
    /**
     * Die宽度(mm)
     */
    @ApiModelProperty(value = "Die宽度(mm)")
    private String dieWidth;
    /**
     * 有效芯片数
     */
    @ApiModelProperty(value = "有效芯片数")
    private String grossDie;
    /**
     * 销售部门
     */
    @ApiModelProperty(value = "销售部门")
    private String salesDept;
    /**
     * 对应销售
     */
    @ApiModelProperty(value = "对应销售")
    private String sales;
    /**
     * 对应CE
     */
    @ApiModelProperty(value = "对应CE")
    private String ce;
    /**
     * 对应Backup CE
     */
    @ApiModelProperty(value = "对应Backup CE")
    private String backupCe;
    /**
     * 产品建立时间
     */
    @ApiModelProperty(value = "产品建立时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date deviceSetupTime;

    /**
     * 产品状态
     */
    @ApiModelProperty(value = "产品状态")
    private String productIdStatus;
    /**
     * 状态说明
     */
    @ApiModelProperty(value = "状态说明")
    private String statusRemark;
    /**
     * 签署MPA
     */
    @ApiModelProperty(value = "签署MPA")
    private String mpa;
    /**
     * 产品对应PIE/TD Group
     */
    @ApiModelProperty(value = "产品对应PIE/TD Group")
    private String pieTdGroup;
    /**
     * Product ID
     */
    @ApiModelProperty(value = "Product ID")
    private String prodId;
    /**
     * SAP code
     */
    @ApiModelProperty(value = "SAP code")
    private String sapCode;
    /**
     * Process Name
     */
    @ApiModelProperty(value = "Process Name")
    private String processName;
    /**
     * 光刻层数
     */
    @ApiModelProperty(value = "光刻层数")
    private String lithoLayerNum;
    /**
     * 分销渠道:
     */
    @ApiModelProperty(value = "分销渠道")
    private String channelDistribution;
    /**
     * 客户衬底
     */
    @ApiModelProperty(value = "客户衬底")
    private String customerBase;
    /**
     * 是否是第三方业务模式
     */
    @ApiModelProperty(value = "是否是第三方业务模式")
    private String thirdPartyBusiness;
    /**
     * 是否是跨厂区业务模式
     */
    @ApiModelProperty(value = "是否是跨厂区业务模式")
    private String crossSiteBusiness;
    /**
     * 参考HC的Product ID
     */
    @ApiModelProperty(value = "参考HC的Product ID")
    private String hcProdId;
    /**
     * 参考HC的Device ID
     */
    @ApiModelProperty(value = "参考HC的Device ID")
    private String hcDeviceId;
    /**
     * 对应LG的Product ID
     */
    @ApiModelProperty(value = "对应LG的Product ID")
    private String lgProdId;
    /**
     * 对应LG的Device ID
     */
    @ApiModelProperty(value = "对应LG的Device ID")
    private String lgDeviceId;
    /**
     * GTA Sub(销售文本)
     */
    @ApiModelProperty(value = "GTA Sub(销售文本)")
    private String gtaSub;
    /**
     * Original Product ID
     */
    @ApiModelProperty(value = "Original Product ID")
    private String originalProdId;
    /**
     * NPIT 号
     */
    @ApiModelProperty(value = "NPIT 号")
    private String npitCode;
    /**
     * Risk剩余额度
     */
    @ApiModelProperty(value = "Risk剩余额度")
    private String riskSurplusQuota;
    /**
     * Risk初始额度
     */
    @ApiModelProperty(value = "Risk初始额度")
    private String riskInitialQuota;
    /**
     * 累计额度
     */
    @ApiModelProperty(value = "累计额度")
    private String accumulativeQuota;
    /**
     * 扣除额度
     */
    @ApiModelProperty(value = "扣除额度")
    private String deductQuota;
}
