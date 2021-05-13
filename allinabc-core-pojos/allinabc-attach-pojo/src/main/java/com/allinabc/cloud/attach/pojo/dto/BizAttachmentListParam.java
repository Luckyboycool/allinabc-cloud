package com.allinabc.cloud.attach.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/2/26 9:31
 **/
@Data
public class BizAttachmentListParam implements Serializable {

    private static final long serialVersionUID = -7691646453641430203L;

    private List<AttachmentParam> attachmentParams;
}
