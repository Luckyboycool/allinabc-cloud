package com.allinabc.cloud.attach.util;

import com.allinabc.cloud.attach.pojo.dto.AttachmentInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Description 本地存储上传与下载
 * @Author wangtaifeng
 * @Date 2020/11/18 9:37
 **/
@Slf4j
@Component
public class LocalAttachmentUtil {

//    @Value("${attachment.localurl}")
//    private String localurl;

    /**
     * 根据配置生成文件路径
     */
    public  String buildPath(HttpServletRequest request,String systemCode, String moduleCode, String bizType, String fileUploadType, String fileName) {
        return systemCode+"/"+moduleCode+"/"+bizType+"/"+fileUploadType+"/"+fileName;
    }

    public void downLoad(AttachmentInfoDTO attachmentInfoDTO, HttpServletResponse response){
        //描述：通用文件下载
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(attachmentInfoDTO.getFileUrl()));
            response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(attachmentInfoDTO.getFileName()+"."+attachmentInfoDTO.getFileType(), "UTF-8"));
            outputStream = response.getOutputStream();
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }

        } catch (Exception e) {
            log.error("文件下载异常", e);
        } finally {
            try {
                if(inputStream!=null) {
                    inputStream.close();
                }
                if(outputStream!=null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}
