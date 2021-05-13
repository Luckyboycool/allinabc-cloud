package com.allinabc.cloud.admin.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author simon
 * @date 2021-04-06 14:19:59
 */
@Data
@ApiModel(value="AdmDldbDetail对象")
public class DldbDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	private String id;
	/**
	 * $column.comments
	 */
	private String dldbInfoId;
	/**
	 * 产品名称
	 */
	private String deviceName;
	/**
	 * 产品状态
	 */
	private String productIdStatus;
	/**
	 * 状态说明
	 */
	private String statusRemark;
	/**
	 * 签署MPA
	 */
	private String mpa;
	/**
	 * 产品对应PIE/TD Group
	 */
	private String pieTdGroup;
	/**
	 * Product ID
	 */
	private String prodId;
	/**
	 * SAP code
	 */
	private String sapCode;
	/**
	 * Process Name
	 */
	private String processName;
	/**
	 * 光刻层数
	 */
	private String lithoLayerNum;
	/**
	 * 分销渠道:
	 */
	private String channelDistribution;
	/**
	 * 客户衬底
	 */
	private String customerBase;
	/**
	 * 是否是第三方业务模式 
	 */
	private String thirdPartyBusiness;
	/**
	 * 是否是跨厂区业务模式 
	 */
	private String crossSiteBusiness;
	/**
	 * 参考HC的Product ID
	 */
	private String hcProdId;
	/**
	 * 参考HC的Device ID
	 */
	private String hcDeviceId;
	/**
	 * 对应LG的Product ID
	 */
	private String lgProdId;
	/**
	 * 对应LG的Device ID
	 */
	private String lgDeviceId;
	/**
	 * GTA Sub(销售文本)
	 */
	private String gtaSub;
	/**
	 * Original Product ID
	 */
	private String originalProdId;
	/**
	 * NPIT 号
	 */
	private String npitCode;
	/**
	 * Risk剩余额度
	 */
	private String riskSurplusQuota;
	/**
	 * Risk初始额度
	 */
	private String riskInitialQuota;
	/**
	 * 累计额度
	 */
	private String accumulativeQuota;
	/**
	 * 扣除额度
	 */
	private String deductQuota;

}
