package com.allinabc.cloud.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.demo.mapper.LeaveMapper;
import com.allinabc.cloud.demo.pojo.po.Leave;
import com.allinabc.cloud.demo.service.LeaveService;
import com.allinabc.cloud.starter.form.domain.BasicForm;
import com.allinabc.cloud.starter.form.mapper.BasicFormMapper;
import com.allinabc.cloud.starter.form.param.BasicFormParam;
import com.allinabc.common.mybatis.mapper.MybatisCommonBaseMapper;
import com.allinabc.common.mybatis.service.impl.MybatisCommonServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/18 21:54
 **/
@Service
@Data
@Slf4j
public class LeaveServiceImpl extends MybatisCommonServiceImpl<Leave> implements LeaveService {

    @Resource
    private LeaveMapper leaveMapper;

    @Resource
    private BasicFormMapper basicFormMapper;

    @Override
    protected MybatisCommonBaseMapper<Leave> getRepository() {
        return leaveMapper;
    }

    @Override
    public Result<Void> add(BasicFormParam basicFormParam) {
        BasicForm basicForm = BeanUtil.copyProperties(basicFormParam, BasicForm.class);
        Date date = new Date();


        basicForm.setCreateTm(date);
        basicForm.setUpdateTm(date);
        basicForm.setCreatedBy("9999");
        basicForm.setUpdatedBy("9999");

        int insert = basicFormMapper.insert(basicForm);
        return Result.success();
    }


//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Result<?> add(Request<LeaveDTO> request) {
//        LeaveDTO leaveDTO = request.getData();
//        Leave leaveEntity = new Leave();
//        BasicForm basicFormEntity = new BasicForm();
//        //描述：实体赋值
//        BeanUtils.copyBeanProp(leaveEntity,leaveDTO);
//        BeanUtils.copyBeanProp(basicFormEntity,leaveDTO);
//        basicFormEntity.setCreatedBy("9999");
//        basicFormEntity.setCreateTm(new Date());
//
//        //描述：新增两张表记录
//        if (leaveMapper.insert(leaveEntity) != 1) {
//            throw new RuntimeException("新增请假leave数据失败！");
//        }
//        basicFormEntity.setPid(leaveEntity.getId());
//        if (basicFormMapper.insert(basicFormEntity) != 1) {
//            throw new RuntimeException("新增BasicForm数据失败！");
//        }
//
//        log.info("新增成功");
//        return Result.success(basicFormEntity,"add success");
//    }
//
//    @Override
//    public Result<LeaveVO> findByID(Request<LeaveDTO> request) {
//        LeaveVO result = leaveMapper.selectLeaveById(request.getData().getPid());
//        return Result.success(result);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Result<Void> modify(Request<LeaveDTO> request) {
//        LeaveDTO leaveDTO = request.getData();
//        Leave leaveEntity = new Leave();
//        BasicForm basicFormEntity = new BasicForm();
//        //描述：实体赋值
//        BeanUtils.copyBeanProp(leaveEntity,leaveDTO);
//        BeanUtils.copyBeanProp(basicFormEntity,leaveDTO);
//        leaveEntity.setId(leaveDTO.getPid());
//        basicFormEntity.setUpdatedBy("9999");
//        basicFormEntity.setUpdateTm(new Date());
//
//        //描述：修改两张表记录
//        if (leaveMapper.updateById(leaveEntity) != 1) {
//            throw new RuntimeException("修改请假leave数据失败！");
//        }
//        if (basicFormMapper.updateById(basicFormEntity) != 1) {
//            throw new RuntimeException("修改BasicForm数据失败！");
//        }
//
//        return Result.success("修改成功");
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Result<Void> delete(Request<LeaveDTO> request) {
//        LeaveDTO data = request.getData();
//        if (leaveMapper.deleteById(data.getPid()) != 1) {
//            throw new RuntimeException("删除leave数据失败");
//        }
//        if(basicFormMapper.deleteById(data.getId())!=1){
//            throw new RuntimeException("删除basicform数据失败");
//        }
//        log.info("删除成功");
//        return Result.success("刪除成功");
//    }
}
