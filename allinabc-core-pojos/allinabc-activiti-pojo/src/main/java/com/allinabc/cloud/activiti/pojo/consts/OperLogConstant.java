package com.allinabc.cloud.activiti.pojo.consts;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/5/9 16:01
 **/
public class OperLogConstant {


    /**
     * update
     */
    public static final String UPDATE ="Update";


    /**
     * GRAP
     */
    public static final String GRAP ="Grap";


    /**
     * ACTIVATE
     */
    public static final String ACTIVATE ="Activate";


    /**
     * CANCLE
     */
    public static final String CANCLE ="Cancle";


    /**
     * PROCESS_END
     */
    public static final String PROCESS_END ="Process End";


    /**
     * Reassign
     */
    public static final String REASSIGN ="Reassign";



    /**
     * update
     */
    public static final String UPDATE_REMARK ="Job Editor修改申请单#${basicFormId}#";


    /**
     * GRAP
     */
    public static final String GRAP_REMARK ="Job Editor抓去申请单#${basicFormId}#至#${taskName}#";


    /**
     * ACTIVATE
     */
    public static final String ACTIVATE_REMARK ="Job Editor重新激活申请单#${basicFormId}#流程";


    /**
     * CANCLE
     */
    public static final String CANCLE_REMARK ="Job Editor取消申请单#${basicFormId}#流程";


    /**
     * PROCESS_END
     */
    public static final String PROCESS_END_REMARK ="Job Editor将申请单#${basicFormId}#调用流程结束服务";


    /**
     * Reassign
     */
    public static final String REASSIGN_REMARK ="Job Editor将申请单#{}#的节点#{}#处理人指派为#{}#";
}
