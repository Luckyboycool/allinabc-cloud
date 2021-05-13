package com.allinabc.cloud.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import com.allinabc.cloud.admin.mapper.DldbDetailMapper;
import com.allinabc.cloud.admin.mapper.DldbInfoMapper;
import com.allinabc.cloud.admin.pojo.dto.DldbSendDTO;
import com.allinabc.cloud.admin.pojo.dto.DldbUpdateDTO;
import com.allinabc.cloud.admin.pojo.enums.DldbEnums;
import com.allinabc.cloud.admin.pojo.po.DldbDetail;
import com.allinabc.cloud.admin.pojo.po.DldbInfo;
import com.allinabc.cloud.admin.pojo.vo.DldbDetailVO;
import com.allinabc.cloud.admin.pojo.vo.DldbInfoVO;
import com.allinabc.cloud.admin.pojo.vo.DldbSingleVO;
import com.allinabc.cloud.admin.pojo.vo.DldbVO;
import com.allinabc.cloud.admin.service.DldbInfoService;
import com.allinabc.cloud.admin.util.AsyncSendMail;
import com.allinabc.cloud.common.core.utils.bean.BeanCompareUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.notice.pojo.constant.NoticeConstant;
import com.allinabc.cloud.notice.pojo.dto.MailRequest;
import com.allinabc.cloud.notice.pojo.dto.NoticeParticipantDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Simon.Xue
 * @date 2021/4/6 2:34 下午
 **/
@Slf4j
@Service
public class DldbInfoServiceImpl implements DldbInfoService {

    @Resource
    private DldbInfoMapper dldbInfoMapper;
    @Resource
    private DldbDetailMapper dldbDetailMapper;
    @Autowired
    private AsyncSendMail asyncSendMail;



    @Override
    public IPage findList(Integer pageNum, Integer pageSize, String deviceName, String productId) {
        Page page = new Page(pageNum, pageSize);
        IPage<DldbVO> dldbVOIPage = dldbInfoMapper.findList(page, deviceName, productId);
        // TODO 后期根据表中数据做对应处理

        return dldbVOIPage;
    }

    @Override
    public Result update(DldbUpdateDTO dldbUpdateDTO) {

        QueryWrapper<DldbDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("DLDB_INFO_ID", dldbUpdateDTO.getId());
        List<DldbDetail> dldbDetails = dldbDetailMapper.selectList(wrapper);
        Assert.notEmpty(dldbDetails, "数据有误，请检查后重试");
        List<DldbUpdateDTO.DldbDetailParam> dldbDetailParamList = dldbUpdateDTO.getDldbDetailList();
        dldbDetails.forEach(dldbDetail-> {
            String productIdStatus = dldbDetail.getProductIdStatus();
            dldbDetailParamList.forEach(dldbDetailParam -> {
                if (dldbDetail.getId().equals(dldbDetailParam.getId())) {
                    // TODO 判断状态的更新
                    String productIdStatusParam = dldbDetailParam.getProductIdStatus();
                    if (StringUtils.isNotEmpty(productIdStatusParam)) {
                        // 手动修改ENG状态
                        if (DldbEnums.PRODUCT_ID_STATUS.ENG.name().equals(productIdStatusParam)) {
                            Assert.isTrue((DldbEnums.PRODUCT_ID_STATUS.RELEASE.name().equals(productIdStatus)
                                            || DldbEnums.PRODUCT_ID_STATUS.CLOSE.name().equals(productIdStatus)),
                                    "该状态下，不能修改成ENG状态！");
                        }

                        // 修改CLOSE状态
                        if (DldbEnums.PRODUCT_ID_STATUS.CLOSE.name().equals(productIdStatusParam)) {
                            Assert.isTrue(!DldbEnums.PRODUCT_ID_STATUS.CLOSE.name().equals(productIdStatus),
                                    "该状态下，不能修改成CLOSE状态！");
                        }

                    }

                    BeanUtils.copyProperties(dldbDetailParam, dldbDetail, BeanCompareUtils.getEmptyPropertyNames(dldbDetailParam));
                    dldbDetailMapper.updateById(dldbDetail);

                }
            });

        });

        return Result.success("更新成功");
    }

    @Override
    public Result sendNoticeMail(DldbSendDTO dldbSendDTO) {
        List<DldbSendDTO.DldbUserDTO> userList = dldbSendDTO.getUserList();
        Assert.notEmpty(userList, "请选择要发送邮件的人员");


        List<NoticeParticipantDTO> toList = userList.stream().map(a -> {
            NoticeParticipantDTO to = new NoticeParticipantDTO();
            to.setParticipantType(NoticeConstant.PARTICIPANT_TYPE_NOTICE_TO);
            to.setObjectType(NoticeConstant.OBJECT_TYPE_GROUP.equalsIgnoreCase(a.getType())
                    ? NoticeConstant.OBJECT_TYPE_GROUP
                    : NoticeConstant.OBJECT_TYPE_USER);
            to.setObjectId(a.getId());
            return to;
        }).collect(Collectors.toList());

        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("dldb测试主题");
        mailRequest.setContent("dldb测试内容");
        mailRequest.setNoticeParticipant(toList);
        mailRequest.setIsHtml(false);
        mailRequest.setNeedRetry(false);
        mailRequest.setNoticeWay(NoticeConstant.NOTICE_WAY_ZERO);
        //TODO 数据库中需要添加模板
        mailRequest.setNoticeTypeCode("dldb测试通知模板");
        log.info("开始发送DLDB通知邮件，mailRequest = {}", JSONObject.toJSONString(mailRequest));
        asyncSendMail.handleSendMail(mailRequest);

        return Result.success("发送邮件成功");
    }

    @Override
    public Result<DldbSingleVO> getById(String id) {
        DldbInfo dldbInfo = dldbInfoMapper.selectById(id);
        Assert.notNull(dldbInfo, "产品信息不存在");
        List<DldbDetail> dldbDetails = dldbDetailMapper.selectList(new QueryWrapper<DldbDetail>().eq("DLDB_INFO_ID", id));
        DldbInfoVO dldbInfoVO = BeanUtil.copyProperties(dldbInfo, DldbInfoVO.class);
        DldbSingleVO dldbSingleVO = new DldbSingleVO();
        dldbSingleVO.setDldbInfoVO(dldbInfoVO);
        List<DldbDetailVO> dldbDetailVOList = dldbDetails.stream().map(a -> {
            DldbDetailVO dldbDetailVO = new DldbDetailVO();
            BeanUtil.copyProperties(a, dldbDetailVO);
            return dldbDetailVO;
        }).collect(Collectors.toList());
        dldbSingleVO.setDldbDetailVOList(dldbDetailVOList);
        return Result.success(dldbSingleVO);
    }


}
