package com.allinabc.cloud.admin.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author simon
 * @date 2021-04-06 14:20:02
 */
@Data
@ApiModel(value="AdmDldbInfo对象")
public class DldbInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private String id;
	/**
	 * 客户代码
	 */
	private String custCode;
	/**
	 * Customer Code all
	 */
	private String custName;
	/**
	 * Foundry 区域
	 */
	private String foundryLocal;
	/**
	 * GTA Foundry厂
	 */
	private String fab;
	/**
	 * Inch
	 */
	private String inch;
	/**
	 * 产品名称
	 */
	private String deviceName;
	/**
	 * 技术分类
	 */
	private String technologyFamily;
	/**
	 * 技术节点
	 */
	private String technologyNode;
	/**
	 * 技术类型
	 */
	private String tech;
	/**
	 * 特征描述/平台
	 */
	private String characterization;
	/**
	 * _P(Poly)_M(Metal)
	 */
	private String pM;
	/**
	 * 核心电压:
	 */
	private String coreVoltage;
	/**
	 * IO器件电压:
	 */
	private String ioDeviceVoltage;
	/**
	 * 高压器件电压:
	 */
	private String hvDeviceVoltage;
	/**
	 * Vds (击穿)/Vce:
	 */
	private String vdsVce;
	/**
	 * 芯片电流:
	 */
	private String chipCurrent;
	/**
	 * 产品应用
	 */
	private String productApplication;
	/**
	 * 主要应用范围:
	 */
	private String primaryApplication;
	/**
	 * 是否有初测
	 */
	private String needCpTest;
	/**
	 * 是否有减薄
	 */
	private String needBackGrinding;
	/**
	 * 是否有蒸金
	 */
	private String needBackMetal;
	/**
	 * 是否有划片
	 */
	private String needDicing;
	/**
	 * Sub采用模式
	 */
	private String subPurchaseModel;
	/**
	 * 芯片代号(12NC of die)
	 */
	private String twelveNcOfDie;
	/**
	 * 创建人
	 */
	private String createdBy;
	/**
	 * 创建时间
	 */
	private Date createTm;
	/**
	 * 更新者
	 */
	private String updatedBy;
	/**
	 * 更新时间
	 */
	private Date updateTm;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * Die长度(mm)
	 */
	private String dieLength;
	/**
	 * Die宽度(mm)
	 */
	private String dieWidth;
	/**
	 * 有效芯片数
	 */
	private String grossDie;
	/**
	 * 销售部门
	 */
	private String salesDept;
	/**
	 * 对应销售
	 */
	private String sales;
	/**
	 * 对应CE
	 */
	private String ce;
	/**
	 * 对应Backup CE
	 */
	private String backupCe;
	/**
	 * 产品建立时间
	 */
	private Date deviceSetupTime;

}
