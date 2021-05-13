package com.allinabc.cloud.starter.s3.util;

import com.allinabc.commons.s3.S3Operation;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class S3Util {


    private static String accessKeyId ;

    private static String secretAccessKey ;

    private static String endpointUrl;

    private static String serviceType;



    /**
     * 上传文件
     */
    public void upload(String bucketName, String s3Path, InputStream is,int fileSize)throws Exception {
        int threadNum = 2;
        log.info("accessKeyId="+accessKeyId+" secretAccessKey="+secretAccessKey+" endpointUrl="+endpointUrl+" serviceType="+serviceType);
        AmazonS3 s3Client = S3Operation.getS3Client(accessKeyId, secretAccessKey, endpointUrl, serviceType);
        //用完要关
        TransferManager transferManager = S3Operation.getTransferManger(s3Client,threadNum);
        S3Operation.putS3Object(transferManager, bucketName, s3Path, is,fileSize);
        S3Operation.closeTransferManger(transferManager);
    }

    /**
     * 下载S3文件
     */
    public void downloadS3File(String bucketName,String s3Path,String localPath){
        int threadNum = 3;
        AmazonS3 s3Client = S3Operation.getS3Client(accessKeyId, secretAccessKey, endpointUrl, serviceType);
        //用完要关
        TransferManager transferManager = S3Operation.getTransferManger(s3Client,threadNum);

        S3Operation.downloadS3File(transferManager, bucketName, s3Path, localPath);
        S3Operation.closeTransferManger(transferManager);
    }

    /**
     * 根据配置生成文件路径
     */
    public String buildPath(String systemCode,String moduleCode,String bizType,String fileUploadType,String fileName){
        return (systemCode+"/"+moduleCode+"/"+bizType+"/"+fileUploadType+"/"+fileName).toLowerCase();
    }

//    public String buildFtpPath(String systemCode,String moduleCode,String bizType,String fileUploadType,String fileName){
//        return ("/"+systemCode+"/"+moduleCode+"/"+bizType+"/"+fileUploadType+"/"+fileName).toLowerCase();
//    }

    public String buildFtpPathNoName(String systemCode,String moduleCode,String bizType,String fileUploadType){
        return ("/"+systemCode+"/"+moduleCode+"/"+bizType+"/"+fileUploadType).toLowerCase();
    }
    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


    @Value("${aws.access_key_id:'accessKeyId'}")
    public void setKey(String accesskey) {
        accessKeyId = accesskey;
    }

    @Value("${aws.secret_access_key:'secretAccessKey'}")
    public void setSecret(String secretkey) {
        secretAccessKey = secretkey;
    }

    @Value("${aws.s3.endpoint}")
    public void setEndPoint(String endpoint) {
        endpointUrl = endpoint;
    }

    @Value("${aws.s3.serviceType}")
    public void setServiceType(String type) {
        serviceType = type;
    }




}
