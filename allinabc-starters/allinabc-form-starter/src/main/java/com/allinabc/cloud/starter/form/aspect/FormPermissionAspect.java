package com.allinabc.cloud.starter.form.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.activiti.pojo.consts.ProcessNodeConstant;
import com.allinabc.cloud.common.core.exception.user.ForbiddenException;
import com.allinabc.cloud.common.core.exception.user.UnauthorizedException;
import com.allinabc.cloud.common.core.utils.ServletUtils;
import com.allinabc.cloud.common.core.utils.StringUtils;
import com.allinabc.cloud.common.core.utils.constant.Constants;
import com.allinabc.cloud.common.core.utils.spring.SpringUtils;
import com.allinabc.cloud.common.web.enums.ApiResultCode;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.starter.form.annotation.HasFormPermission;
import com.allinabc.cloud.starter.form.domain.BasicForm;
import com.allinabc.cloud.starter.form.enums.BasicFormStatus;
import com.allinabc.cloud.starter.form.mapper.BasicFormMapper;
import com.allinabc.cloud.starter.redis.util.RedisHelper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponseWrapper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Slf4j
@Component
public class FormPermissionAspect {

    @Resource
    private BasicFormMapper basicFormMapper;


    @Around("@annotation(com.allinabc.cloud.starter.form.annotation.HasFormPermission)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        HasFormPermission annotation = method.getAnnotation(HasFormPermission.class);
        if (annotation == null) {
            return point.proceed();
        }
        Object[] objs = point.getArgs();
        String[] argNames = ((MethodSignature) point.getSignature()).getParameterNames(); // ?????????
        Map<String, Object> paramMap = Maps.newHashMap();
        for (int i = 0; i < objs.length; i++) {
            if (!(objs[i] instanceof ExtendedServletRequestDataBinder) && !(objs[i] instanceof HttpServletResponseWrapper)) {
                paramMap.put(argNames[i], objs[i]);
            }
        }
        log.info(JSON.toJSONString(paramMap));

//        if(has(paramMap)) {
//            Result result = (Result) point.proceed();
//            if(result.getCode()==20000){
//                //???????????????????????????
//                basicFormMapper.selectFormPermissionBy
//            }
//            return result;
//        }else {
//            throw new ForbiddenException();
//        }
        return has(paramMap, point);
    }

    private Object has(Map<String, Object> maps, ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ServletUtils.getRequest();

        // ??????Token?????????????????????
        RedisHelper redis = SpringUtils.getBean(RedisHelper.class);
        String token = request.getHeader(Constants.TOKEN);
        Map<String, Object> userMap = redis.getBean(Constants.ACCESS_TOKEN + token, Map.class);

        if (null == userMap) {
            throw new UnauthorizedException();
        }

        String userId = MapUtil.get(userMap, "id", String.class);
        String basicInfoId = (String) maps.get("id");
        String accountType = MapUtil.get(userMap, "accountType", String.class);
        Result result = (Result) point.proceed();

        BasicForm basicForm = basicFormMapper.selectById(basicInfoId);
        if (basicForm == null) {
            throw new RuntimeException("??????????????????");
        }

        if (result.getCode() == ApiResultCode.SUCCESS.getCode()) {
            // ?????? ??? ?????? ????????????
            if (BasicFormStatus.DRAFT.getCode().equals(basicForm.getStatus())
                    || BasicFormStatus.WASTE_VERSION.getCode().equals(basicForm.getStatus())) {
                Object data = result.getData();
                if (data != null) {
                    Map<String, Object> objectMap = BeanUtil.beanToMap(data);
                    objectMap.put("revision", false);
                    result.setData(objectMap);
                    return result;
                }
            }
        } else {
            throw new ForbiddenException();
        }

        //????????????userId?????????????????????????????????
        String deptmentId = basicFormMapper.selectDeptmentIdByUserId(userId);
        List<String> groupIds = basicFormMapper.selectGroupNameByUserId(userId);
        // basicFormMapper.selectBasicFormPermissionByUserIdAndFormType(basicForm.getFormType(),userId,);
        List<String> groupBasicInfoList = new ArrayList<>();
        List<String> userBasicInfoList = basicFormMapper.selctPermissionByUserId(accountType, userId, basicForm.getFormType());
        if (CollUtil.isNotEmpty(groupIds)) {
            groupBasicInfoList = basicFormMapper.selectPermissionByGroupName(groupIds, basicForm.getFormType());
        }
        List<String> deptBasicInfoList = new ArrayList<>();
        if (StringUtils.isNotEmpty(deptmentId)) {
            deptBasicInfoList = basicFormMapper.selectPermissionByDeptId(deptmentId, basicForm.getFormType());
        }
        List<String> basicInfnIds = Lists.newArrayList();
        if (userBasicInfoList != null && userBasicInfoList.size() > 0) {
            basicInfnIds.addAll(userBasicInfoList);
        }
        if (groupBasicInfoList != null && groupBasicInfoList.size() > 0) {
            basicInfnIds.addAll(groupBasicInfoList);
        }
        if (deptBasicInfoList != null && deptBasicInfoList.size() > 0) {
            basicInfnIds.addAll(deptBasicInfoList);
        }
        if (basicInfnIds == null || basicInfnIds.size() == 0) {
            throw new ForbiddenException();
        }
        basicInfnIds = basicInfnIds.stream().distinct().collect(Collectors.toList());
        boolean hasPerm = basicInfnIds.stream().anyMatch(basicInfoId::equals);
        if (hasPerm) {
            log.info("???????????????????????????????????????????????????");
            if (result.getCode() == 20000) {
                //???????????????????????????
                List<String> revesionBasicInfoId = basicFormMapper.selectFormRevisionPermissionByRevision(accountType, basicInfoId, basicForm.getFormType(), userId, groupIds, deptmentId, "2");
                if (!CollUtil.isEmpty(revesionBasicInfoId)) {
                    //???????????????????????????
                    //2021-5-10 ??????(??????Device/MTR/TopTier???????????????????????????)
                    String basicFormStatus = basicForm.getStatus();
                    String basicFormFormType = basicForm.getFormType();
                    log.info("??????????????????=" + basicFormFormType + ",???????????????=" + basicFormStatus);
                    /**
                     * technology?????? ?????????????????????????????????
                     */
                    if (basicFormFormType.equals(ProcessNodeConstant.FORM_TYPE_DEVICE)
                            || basicFormFormType.equals(ProcessNodeConstant.FORM_TYPE_MTR)
                            || basicFormFormType.equals(ProcessNodeConstant.FORM_TYPE_TOPTIER)
                            || basicFormFormType.equals(ProcessNodeConstant.FORM_TYPE_MCI)
                            || ProcessNodeConstant.FORM_TYPE_TECHNOLOGY.equals(basicFormFormType)) {
                        //????????????????????????????????????????????????????????????
                        if (basicFormStatus.equals(BasicFormStatus.COMPLETED.getCode())) {
                            //??????????????????
                            Map<String, Object> objectMap = BeanUtil.beanToMap(result.getData());
                            objectMap.put("revision", true);
                            result.setData(objectMap);
                        } else {
                            //????????????,?????????????????????Device??????????????????
                            if (basicFormFormType.equals(ProcessNodeConstant.FORM_TYPE_DEVICE) && basicFormStatus.equals(BasicFormStatus.PROCESSING.getCode())) {
                                Map<String, Object> objectMap = BeanUtil.beanToMap(result.getData());
                                objectMap.put("revision", true);
                                result.setData(objectMap);
                            } else {
                                Map<String, Object> objectMap = BeanUtil.beanToMap(result.getData());
                                objectMap.put("revision", false);
                                result.setData(objectMap);
                            }

                        }

                    } else {
                        Object data = result.getData();
                        if (data != null) {
                            Map<String, Object> objectMap = BeanUtil.beanToMap(data);
                            objectMap.put("revision", false);
                            result.setData(objectMap);
                        }
                    }

                } else {
                    Object data = result.getData();
                    if (data != null) {
                        Map<String, Object> objectMap = BeanUtil.beanToMap(data);
                        objectMap.put("revision", false);
                        result.setData(objectMap);
                    }
                }
            }
            return result;
        } else {
            throw new ForbiddenException("????????????");
        }

    }
}
