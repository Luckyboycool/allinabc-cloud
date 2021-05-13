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
        log.info("发送邮件请求参数=" + JSON.toJSONString(mailRequest));
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
            log.error("发送邮件失败，失败数据=" + JSON.toJSONString(nteNoticeInfo));
            throw new RuntimeException("发送邮件失败");
        }
        List<NoticeParticipant> ls = new ArrayList();
        List<NoticeParticipantDTO> noticeParticipant = mailRequest.getNoticeParticipant();
        User currentUser = CurrentUserUtil.getCurrentUser();
        //描述：赋值操作
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
            log.error("发送邮件失败，失败数据=" + JSON.toJSONString(ls));
            throw new RuntimeException("发送邮件失败");
        }
        if (mailRequest.getNoticeWay().equals(NoticeConstant.NOTICE_WAY_ZERO)) {
            log.info("需要及时发邮件通知");
            noticeByMail(noticeParticipant, mailRequest, noticeId);
        } else {
            //定时通知
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
//            log.error("文件操作失败", e);
//            throw new RuntimeException("文件操作失败");
//        }
//    }

    public void noticeByMail(List<NoticeParticipantDTO> noticeParticipant, MailRequest mailRequest, String noticeId) {
        List<String> noticeToMailList = new ArrayList();
        List<String> noticeCcMailList = new ArrayList();
        Map<String, List<NoticeParticipantDTO>> collect = noticeParticipant.stream().collect(Collectors.groupingBy(m -> m.getParticipantType()));
        for (Map.Entry<String, List<NoticeParticipantDTO>> entry : collect.entrySet()) {
            if (entry.getKey().equals(NoticeConstant.PARTICIPANT_TYPE_NOTICE_TO)) {
                //接收方,遍历查询出所有的主键id
                setModel(entry, noticeToMailList);
            }
            //抄送方
            if (entry.getKey().equals(NoticeConstant.PARTICIPANT_TYPE_NOTICE_CC)) {
                setModel(entry, noticeCcMailList);
            }
        }


        //描述：去除为空的邮箱数据
        noticeToMailList  = noticeToMailList.stream().filter(s -> s!=null).collect(Collectors.toList());
        noticeCcMailList  = noticeCcMailList.stream().filter(s -> s!=null).collect(Collectors.toList());
        //描述：判断接收人邮箱地址是否为空
        if (noticeToMailList.size() <= 0) {
            log.error("需要发送的收件人邮箱未找到");
            throw new RuntimeException("要发送的收件人邮箱未找到,请查询信息是否正确！");
        }
        //描述：发送邮件操作
        log.info("开始发送邮件");
        boolean isSuccess = sendMailByRetry(mailRequest, noticeId, noticeToMailList, noticeCcMailList);
        if (!isSuccess) {
            //如果失败，判断是否需要重试，目前只重试一次
            if (mailRequest.getNeedRetry()) {
                boolean success = sendMailByRetry(mailRequest, noticeId, noticeToMailList, noticeCcMailList);
                if (!success) {
                    log.info("邮件重试一次失败");
                    nteNoticeInfoMapper.updateRetryCountById(noticeId, 1);
                   // throw new RuntimeException("邮件发送失败");
                }
            }
        }
    }


    /**
     * 封装数据
     */
    public void setModel(Map.Entry<String, List<NoticeParticipantDTO>> entry, List<String> ls) {

        //接收方,遍历查询出所有的主键id
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
                // 如果类型是Email,直接添加到邮箱list中
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
     * 发送邮件，返回是否成功或者失败
     */
    public boolean sendMailByRetry(MailRequest mailRequest, String noticeId, List<String> noticeToMailList, List<String> noticeCcMailList) {
        try {
            List<Attachment> attachments = mailRequest.getAttachments();
            log.info("附件信息=" + JSON.toJSONString(attachments));
            if (noticeCcMailList.size() > 0) {
                //需要抄送
                if (attachments != null && attachments.size() > 0) {
                    //附件不为空,支持多附件
                    downTempFiles(attachments);
                    MailUtil.send(mailAccount,noticeToMailList, noticeCcMailList, null, mailRequest.getSubject(), mailRequest.getContent(), mailRequest.getIsHtml(), getAttachmentFiles(attachments));
                } else {
                    MailUtil.send(mailAccount,noticeToMailList, noticeCcMailList, null, mailRequest.getSubject(), mailRequest.getContent(), mailRequest.getIsHtml());
                }

            } else {
                //不需要抄送
                if (attachments != null && attachments.size() > 0) {
                    downTempFiles(attachments);
                    MailUtil.send(mailAccount,noticeToMailList, mailRequest.getSubject(), mailRequest.getContent(), mailRequest.getIsHtml(), getAttachmentFiles(attachments));
                } else {
                    MailUtil.send(mailAccount,noticeToMailList, mailRequest.getSubject(), mailRequest.getContent(), mailRequest.getIsHtml());
                }
            }
            //模拟邮件发送失败
            // int c= 2/0;
            int var1 = nteNoticeInfoMapper.updateStatusById(noticeId, NoticeConstant.NOTICE_SEND_STATUS_SUCCESS);
            if (var1 != 1) {
                throw new RuntimeException("更新邮件状态失败");
            }
            return true;
        } catch (Exception e) {
            log.error("邮件发送出现异常，异常信息=" + e.getMessage(), e);
            //更新数据库邮件发送状态
            int var = nteNoticeInfoMapper.updateStatusById(noticeId, NoticeConstant.NOTICE_SEND_STATUS_FAIL);
            if (var != 1) {
                throw new RuntimeException("更新邮件状态失败");
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
            log.info("文件下载完成");
        });
    }


}
