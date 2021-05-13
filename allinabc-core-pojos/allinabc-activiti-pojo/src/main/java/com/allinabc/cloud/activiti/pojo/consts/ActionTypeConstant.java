package com.allinabc.cloud.activiti.pojo.consts;

import java.io.Serializable;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/10 19:27
 **/
public class ActionTypeConstant implements Serializable {


    /**
     * 创建
     */
    public static final String CREATE = "submit" ;

    /**
     * 通过
     */
    public static final String PASS = "通过" ;

    /**
     * 驳回
     */
    public static final String REJECT = "驳回" ;

    /**
     * 取消
     */
    public static final String CANCLE = "取消" ;

    /**
     * 结束
     */
    public static final String END = "结束" ;

    /**
     * 转办
     */
    public static final String TRANSFER = "转办" ;

    /**
     * 委托
     */
    public static final String ENTRUST = "加签" ;
}
