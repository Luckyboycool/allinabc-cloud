package com.allinabc.cloud.notice.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSON;
import com.allinabc.cloud.common.core.domain.User;
import com.allinabc.cloud.common.core.utils.bean.BeanUtils;
import com.allinabc.cloud.common.web.pojo.resp.Result;
import com.allinabc.cloud.notice.mapper.NoticeInfoMapper;
import com.allinabc.cloud.notice.mapper.NoticeParticipantMapper;
import com.allinabc.cloud.notice.pojo.constant.NoticeConstant;
import com.allinabc.cloud.notice.pojo.dto.Attachment;
import com.allinabc.cloud.notice.pojo.dto.MailRequest;
import com.allinabc.cloud.notice.pojo.dto.NoticeParticipantDTO;
import com.allinabc.cloud.notice.pojo.po.NoticeInfo;
import com.allinabc.cloud.notice.pojo.po.NoticeParticipant;
import com.allinabc.cloud.notice.service.NoticeService;
import com.allinabc.cloud.notice.util.FilesUtil;
import com.allinabc.cloud.starter.s3.util.S3Util;
import com.allinabc.cloud.starter.web.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/8 10:27
 **/
@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    @Value("${mail.from}")
    private String from;

    @Value("${attachment.bucketName}")
    private String bucketName;

    @Resource
    private NoticeInfoMapper nteNoticeInfoMapper;

    @Resource
    private NoticeParticipantMapper nteNoticeParticipantMapper;

    @Autowired
    private FilesUtil fileUtil;

    @Autowired
    private MailAccount mailAccount;

    @Autowired
    private S3Util s3Util;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> sendMail(MailRequest mailRequest) {
        log.info("????????????????????????=" + JSON.toJSONString(mailRequest));
        NoticeInfo nteNoticeInfo = new NoticeInfo();
        BeanUtils.copyBeanProp(nteNoticeInfo, mailRequest);
        Date now = new Date();
        String noticeId = IdUtil.getSnowflake(0, 0).nextIdStr();
        nteNoticeInfo.setId(noticeId);
        nteNoticeInfo.setSendType(NoticeConstant.SEND_TYPE_MAIL);
        nteNoticeInfo.setNoticeFrom(from);
        if (mailRequest.getNoticeWay().equals(NoticeConstant.NOTICE_WAY_ZERO)) {
            nteNoticeInfo.setSendTime(now);
        }
        int result = nteNoticeInfoMapper.insert(nteNoticeInfo);
        if (result != 1) {
            log.error("?????????????????????????????????=" + JSON.toJSONString(nteNoticeInfo));
            throw new RuntimeException("??????????????????");
        }
        List<NoticeParticipant> ls = new ArrayList();
        List<NoticeParticipantDTO> noticeParticipant = mailRequest.getNoticeParticipant();
        User currentUser = CurrentUserUtil.getCurrentUser();
        //?????????????????????
        noticeParticipant.forEach(s -> {
            NoticeParticipant nteNoticeParticipant;
            if (currentUser == null) {
                nteNoticeParticipant = new NoticeParticipant("9999", now, "9999", now);
            } else {
                nteNoticeParticipant = new NoticeParticipant(currentUser.getId(), now, currentUser.getId(), now);
            }
            BeanUtils.copyBeanProp(nteNoticeParticipant, s);
            nteNoticeParticipant.setId(IdUtil.getSnowflake(0, 0).nextIdStr());
            nteNoticeParticipant.setNoticeId(noticeId);
            ls.add(nteNoticeParticipant);
        });
        int var = nteNoticeParticipantMapper.insertList(ls);
        if (var != ls.size()) {
            log.error("?????????????????????????????????=" + JSON.toJSONString(ls));
            throw new RuntimeException("??????????????????");
        }
        if (mailRequest.getNoticeWay().equals(NoticeConstant.NOTICE_WAY_ZERO)) {
            log.info("???????????????????????????");
            noticeByMail(noticeParticipant, mailRequest, noticeId);
        } else {
            //????????????
        }
        return Result.success();
    }


//    @Override
//    public void setMailContentByTemplateUrl(MailRequest mailRequest) {
//        try {
//            String buckName = mailRequest.getTemplateUrl().substring(0, mailRequest.getTemplateUrl().indexOf("/"));
//            String url = mailRequest.getTemplateUrl().substring(mailRequest.getTemplateUrl().indexOf("/") + 1);
//            InputStream inputStream = S3Util.getInputStream(buckName, url);
//            WordExtractor ex = new WordExtractor(inputStream);
//            String content = ex.getText();
//            mailRequest.setContent(content);
//        } catch (IOException e) {
//            log.error("??????????????????", e);
//            throw new RuntimeException("??????????????????");
//        }
//    }

    public void noticeByMail(List<NoticeParticipantDTO> noticeParticipant, MailRequest mailRequest, String noticeId) {
        List<String> noticeToMailList = new ArrayList();
        List<String> noticeCcMailList = new ArrayList();
        Map<String, List<NoticeParticipantDTO>> collect = noticeParticipant.stream().collect(Collectors.groupingBy(m -> m.getParticipantType()));
        for (Map.Entry<String, List<NoticeParticipantDTO>> entry : collect.entrySet()) {
            if (entry.getKey().equals(NoticeConstant.PARTICIPANT_TYPE_NOTICE_TO)) {
                //?????????,??????????????????????????????id
                setModel(entry, noticeToMailList);
            }
            //?????????
            if (entry.getKey().equals(NoticeConstant.PARTICIPANT_TYPE_NOTICE_CC)) {
                setModel(entry, noticeCcMailList);
            }
        }


        //????????????????????????????????????
        noticeToMailList  = noticeToMailList.stream().filter(s -> s!=null).collect(Collectors.toList());
        noticeCcMailList  = noticeCcMailList.stream().filter(s -> s!=null).collect(Collectors.toList());
        //????????????????????????????????????????????????
        if (noticeToMailList.size() <= 0) {
            log.error("???????????????????????????????????????");
            throw new RuntimeException("????????????????????????????????????,??????????????????????????????");
        }
        //???????????????????????????
        log.info("??????????????????");
        boolean isSuccess = sendMailByRetry(mailRequest, noticeId, noticeToMailList, noticeCcMailList);
        if (!isSuccess) {
            //???????????????????????????????????????????????????????????????
            if (mailRequest.getNeedRetry()) {
                boolean success = sendMailByRetry(mailRequest, noticeId, noticeToMailList, noticeCcMailList);
                if (!success) {
                    log.info("????????????????????????");
                    nteNoticeInfoMapper.updateRetryCountById(noticeId, 1);
                   // throw new RuntimeException("??????????????????");
                }
            }
        }
    }


    /**
     * ????????????
     */
    public void setModel(Map.Entry<String, List<NoticeParticipantDTO>> entry, List<String> ls) {

        //?????????,??????????????????????????????id
        List<String> userIdList = new ArrayList();
        List<String> groupIdList = new ArrayList();
        for (int i = 0; i < entry.getValue().size(); i++) {
            switch (entry.getValue().get(i).getObjectType()) {
                case NoticeConstant.OBJECT_TYPE_USER:
                    userIdList.add(entry.getValue().get(i).getObjectId());
                    break;
                case NoticeConstant.OBJECT_TYPE_GROUP:
                    groupIdList.add(entry.getValue().get(i).getObjectId());
                    break;
                // ???????????????Email,?????????????????????list???
                case NoticeConstant.OBJECT_TYPE_EMAIL:
                    ls.add(entry.getValue().get(i).getObjectId());
                    break;
            }
        }

        List<String> groupMailList = new ArrayList();

        List<String> userMailList = new ArrayList();

        if (userIdList.size() > 0) {
            userMailList = nteNoticeParticipantMapper.selectMailByUserIds(userIdList);
        }

        if (groupIdList.size() > 0) {
            groupMailList = nteNoticeParticipantMapper.selectMailByGroupIds(groupIdList);
        }

        if (groupMailList.size() > 0) {
            ls.addAll(groupMailList);
        }

        if (userMailList.size() > 0) {
            ls.addAll(userMailList);
        }



    }

    /**
     * ?????????????????????????????????????????????
     */
    public boolean sendMailByRetry(MailRequest mailRequest, String noticeId, List<String> noticeToMailList, List<String> noticeCcMailList) {
        try {
            List<Attachment> attachments = mailRequest.getAttachments();
            log.info("????????????=" + JSON.toJSONString(attachments));
            if (noticeCcMailList.size() > 0) {
                //????????????
                if (attachments != null && attachments.size() > 0) {
                    //???????????????,???????????????
                    downTempFiles(attachments);
                    MailUtil.send(mailAccount,noticeToMailList, noticeCcMailList, null, mailRequest.getSubject(), mailRequest.getContent(), mailRequest.getIsHtml(), getAttachmentFiles(attachments));
                } else {
                    MailUtil.send(mailAccount,noticeToMailList, noticeCcMailList, null, mailRequest.getSubject(), mailRequest.getContent(), mailRequest.getIsHtml());
                }

            } else {
                //???????????????
                if (attachments != null && attachments.size() > 0) {
                    downTempFiles(attachments);
                    MailUtil.send(mailAccount,noticeToMailList, mailRequest.getSubject(), mailRequest.getContent(), mailRequest.getIsHtml(), getAttachmentFiles(attachments));
                } else {
                    MailUtil.send(mailAccount,noticeToMailList, mailRequest.getSubject(), mailRequest.getContent(), mailRequest.getIsHtml());
                }
            }
            //????????????????????????
            // int c= 2/0;
            int var1 = nteNoticeInfoMapper.updateStatusById(noticeId, NoticeConstant.NOTICE_SEND_STATUS_SUCCESS);
            if (var1 != 1) {
                throw new RuntimeException("????????????????????????");
            }
            return true;
        } catch (Exception e) {
            log.error("???????????????????????????????????????=" + e.getMessage(), e);
            //?????????????????????????????????
            int var = nteNoticeInfoMapper.updateStatusById(noticeId, NoticeConstant.NOTICE_SEND_STATUS_FAIL);
            if (var != 1) {
                throw new RuntimeException("????????????????????????");
            }
            return false;

        }
    }


    public File[] getAttachmentFiles(List<Attachment> attachments) {
        File[] fileList = new File[attachments.size()];
        for (int i = 0; i < attachments.size(); i++) {
            fileList[i] = FileUtil.file("tempfile/" + attachments.get(i).getFileName());
        }
        return fileList;
    }

    private void downTempFiles(List<Attachment> attachments) {
        attachments.forEach(file -> {
            String url = file.getUrl().substring(file.getUrl().indexOf("/") + 1);
            s3Util.downloadS3File(bucketName,url,FileUtil.getAbsolutePath("tempfile/")+file.getFileName());
            log.info("??????????????????");
        });
    }


}
