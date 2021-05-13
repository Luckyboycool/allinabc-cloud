package com.allinabc.cloud.activiti.pojo.consts;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/10 19:11
 **/
public class DecisionConstant implements Serializable {

    /**
     * 同意
     */
    public static final String AGREE = "Y" ;

    /**
     * 驳回
     */
    public static final String DISAGREE = "N" ;

    /**
     * 取消
     */
    public static final String CANCLE = "C" ;

    /**
     * 转办
     */
    public static final String TRANSFER = "T" ;

    /**
     * 委托
     */
    public static final String ENTRUST = "E" ;


    /**
     * 提交
     */
    public static final String SUBMIT = "submit";

    /**
     * 加签同意
     */
    public static final String ENTRUST_AGREE = "C_Y" ;

    /**
     * 加签同意
     */
    public static final String ENTRUST_DISAGREE = "C_N" ;
}
