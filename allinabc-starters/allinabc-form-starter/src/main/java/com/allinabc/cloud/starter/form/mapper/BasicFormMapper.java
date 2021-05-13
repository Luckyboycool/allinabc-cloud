package com.allinabc.cloud.starter.form.mapper;

import com.allinabc.cloud.activiti.pojo.bo.NameModel;
import com.allinabc.cloud.starter.form.domain.BasicForm;
import com.allinabc.cloud.starter.form.resp.BasicFormVO;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/18 22:44
 **/
public interface BasicFormMapper extends MybatisCommonBaseMapper<BasicForm> {

    @Select("select id, job_number, name ,name||'('||job_number||')' as nameAndJobNumber from ORGDA1.ORG_EMPLOYEE where id=#{id}")
    NameModel selectEmployeeUserNameById(String id);

    @Select("select id, user_name as name,USER_NAME as nameAndJobNumber from ORGDA1.ORG_CUSTOMER_ACCOUNT where id=#{id}")
    NameModel selectAccountNameById(String id);

    @Select("select a.id,a.pid,a.version,a.subject,\n" +
            "       a.request_no,\n" +
            "       a.app_code,\n" +
            "       a.form_type,\n" +
            "       b.form_name,\n" +
            "       a.process_id,\n" +
            "       a.instance_id,\n" +
            "       a.drafter,\n" +
            "       a.requester,\n" +
            "       a.request_time,\n" +
            "       a.status,\n" +
            "       a.remark,\n" +
            "       a.created_by,\n" +
            "       a.create_tm,\n" +
            "       a.updated_by,\n" +
            "       a.update_tm,\n" +
            "       a.is_draft,\n" +
            "       a.main_id,\n" +
            "       a.user_type,\n" +
            "       a.request_time as requestDate,\n" +
            "       decode(a.STATUS, 'CREATE', '创建', 'DRAFT', '草稿', 'PROCESSING', '流程中', 'COMPLETED', '已完成', 'CANCEL', '取消',\n" +
            "              'REDESIGN', '改版', 'WASTEVERSION','废版','')                             as statusCN,\n" +
            "       decode(a.STATUS, 'CREATE', 'Create', 'DRAFT', 'Draft', 'PROCESSING', 'Processing', 'COMPLETED', 'Completed',\n" +
            "              'CANCEL', 'Canceled', 'REDESIGN', 'Redesign', 'WASTEVERSION','Wasteversion','''') as statusEN\n" +
            "from ADMINDA1.ADM_BASIC_FORM a LEFT JOIN ADMINDA1.ADM_FORM_TYPE_NAME b ON a.form_type = b.form_type\n" +
            "where a.id =#{id}")
    BasicFormVO selectBasicFormById(String id);

    @Select("SELECT DEPT_ID FROM ORGDA1.ORG_EMPLOYEE where  id=#{userId}")
    String selectDeptmentIdByUserId(String userId);

    @Select("select GROUP_NAME from ADMINDA1.ADM_GROUP_USER where USER_ID=#{userId}")
    List<String> selectGroupNameByUserId(String userId);

    @Select("<script> select distinct BASIC_FORM_ID from ADMINDA1.ADM_FORM_PERMISSION\n" +
            "            <where>\n" +
            "                FORM_TYPE=#{formType}\n" +
            "                and (BIZ_USER_TYPE_ID=#{userId} \n" +
            "                <if test=\"ls!=null and ls.size>0\">\n" +
            "                 or BIZ_USER_TYPE_ID in\n" +
            "                <foreach collection=\"ls\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n" +
            "                    #{item}\n" +
            "                </foreach>\n" +
            "                </if>\n" +
            "                or BIZ_USER_TYPE_ID=#{deptId})\n" +
            "                and BIZ_PERMISSION=#{status} and IS_AVAILABLE='Y' </where></script>")
    List<String> selectBasicFormPermissionByUserIdAndFormType(@Param("formType") String formType, @Param("userId")String userId, @Param("depetId")String depetId, @Param("groupId")String groupId,@Param("status")String status);

    /**
     * 通过groupName查询basicInfoId
     * @param groupIds
     * @return
     */
    @Select("<script> select distinct BASIC_FORM_ID from ADMINDA1.ADM_FORM_PERMISSION\n" +
            "            <where>\n" +
            "                FORM_TYPE=#{formType}\n" +
            "                <if test=\"ls!=null and ls.size>0\">\n" +
            "                   and BIZ_USER_TYPE='3'\n" +
            "                   and  BIZ_USER_TYPE_ID IN\n" +
            "                 <foreach collection=\"ls\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n" +
            "                     #{item}\n" +
            "                 </foreach>\n" +
            "                </if>\n" +
            "                 and IS_AVAILABLE='Y' </where></script>")
    List<String> selectPermissionByGroupName(@Param("ls") List<String> groupIds,@Param("formType") String formType);

    /**
     * 通过部门ID查询basicInfoId
     * @param deptmentId
     * @param formType
     * @return
     */
    @Select("<script> select distinct BASIC_FORM_ID from ADMINDA1.ADM_FORM_PERMISSION\n" +
            "            <where>\n" +
            "                FORM_TYPE=#{formType}\n" +
            "                and BIZ_USER_TYPE='4'\n" +
            "                and BIZ_USER_TYPE_ID=#{deptmentId}\n" +
            "                and IS_AVAILABLE='Y'\n" +
            "            </where></script>")
    List<String> selectPermissionByDeptId(@Param("deptmentId") String deptmentId, @Param("formType") String formType);

    @Select("select distinct BASIC_FORM_ID from ADMINDA1.ADM_FORM_PERMISSION where BIZ_USER_TYPE=#{accountType} and FORM_TYPE=#{formType} and BIZ_USER_TYPE_ID=#{userId} and IS_AVAILABLE='Y'")
    List<String> selctPermissionByUserId(@Param("accountType") String accountType,@Param("userId") String userId, @Param("formType")String formType);

    @Select("<script>select distinct BASIC_FORM_ID from ADMINDA1.ADM_FORM_PERMISSION\n" +
            "<where>\n" +
            " BASIC_FORM_ID=#{basicInfoId}\n" +
            " and FORM_TYPE=#{formType}\n" +
            " and ((BIZ_USER_TYPE=#{accountType} and BIZ_USER_TYPE_ID=#{userId})\n" +
            " <if test=\"ls!=null and ls.size>0\">\n" +
            "     or (BIZ_USER_TYPE='3'and BIZ_USER_TYPE_ID in\n" +
            "     <foreach collection=\"ls\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n" +
            "         #{item}\n" +
            "     </foreach>\n" +
            "     )\n" +
            " </if>\n" +
            " <if test=\"deptmentId!=null and deptmentId!=''\">\n" +
            "     or (BIZ_USER_TYPE='4' and BIZ_USER_TYPE_ID=#{deptmentId})\n" +
            " </if>\n" +
            " )\n" +
            "and BIZ_PERMISSION=#{status}\n" +
            "and IS_AVAILABLE='Y'\n" +
            "</where></script>")
    List<String> selectFormRevisionPermissionByRevision(@Param("accountType") String accountType,@Param("basicInfoId") String basicInfoId, @Param("formType") String formType, @Param("userId")String userId, @Param("ls") List<String> groupIds, @Param("deptmentId") String deptmentId,@Param("status")String status);

    /**
     * 通过FormType集合查询basicInfoID
     * @param accountType
     * @param userId
     * @param formType
     * @return
     */
    @Select("<script>select distinct BASIC_FORM_ID from ADMINDA1.ADM_FORM_PERMISSION\n" +
            "   <where>\n" +
            "       BIZ_USER_TYPE=#{accountType} and\n" +
            "       BIZ_USER_TYPE_ID=#{userId} and\n" +
            "       FORM_TYPE IN\n" +
            "       <foreach collection=\"ls\" item=\"item\" open=\"(\" close=\")\" separator=\",\">\n" +
            "           #{item}\n" +
            "       </foreach>\n" +
            "       and IS_AVAILABLE='Y'\n" +
            "   </where></script>")
    List<String> selctPermissionByUserIdAndFormTypes(@Param("accountType")String accountType, @Param("userId") String userId, @Param("ls") List<String> formType);

    @Select("<script>select distinct BASIC_FORM_ID from ADMINDA1.ADM_FORM_PERMISSION\n" +
            "        <where>\n" +
            "         FORM_TYPE IN\n" +
            "        <foreach collection=\"formTypes\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n" +
            "          #{item}\n" +
            "        </foreach>\n" +
            "          and BIZ_USER_TYPE='3' and BIZ_USER_TYPE_ID IN\n" +
            "        <foreach collection=\"groupNames\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n" +
            "          #{item}\n" +
            "         </foreach>\n" +
            "          and IS_AVAILABLE='Y'\n" +
            "        </where></script>")
    List<String> selectPermissionByGroupNameAndFormTypes(@Param("groupNames") List<String> groupNames, @Param("formTypes") List<String> formType);

    @Select("<script>select distinct BASIC_FORM_ID from ADMINDA1.ADM_FORM_PERMISSION\n" +
            "     where FORM_TYPE IN\n" +
            "        <foreach collection=\"formTypes\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n" +
            "            #{item}\n" +
            "        </foreach>\n" +
            "        and BIZ_USER_TYPE='4'\n" +
            "        and BIZ_USER_TYPE_ID=#{deptmentId}\n" +
            "        and IS_AVAILABLE='Y'</script>")
    List<String> selectPermissionByDeptIdAndFormTypes(@Param("deptmentId") String deptmentId, @Param("formTypes")List<String> formType);
}
