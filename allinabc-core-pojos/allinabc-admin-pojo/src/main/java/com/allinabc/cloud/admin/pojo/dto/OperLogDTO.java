package com.allinabc.cloud.admin.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/31 10:53
 **/
@Data
public class OperLogDTO implements Serializable {

    private static final long serialVersionUID = -6787641333059720106L;

    /**
     * 操作模块
     */
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private int businessType;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    private int operatorType;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 请求url
     */
    private String operUrl;

    /**
     * 操作地址
     */
    private String operIp;

    /**
     * 操作地点
     */
    private String operLocation;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态（0=异常,1=正常）
     */
    private int status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private Date operTime;

    /**
     * 主键
     */
    private String  id;

    /**
     * 备注
     */
    private String remark;

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
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTm;
}
