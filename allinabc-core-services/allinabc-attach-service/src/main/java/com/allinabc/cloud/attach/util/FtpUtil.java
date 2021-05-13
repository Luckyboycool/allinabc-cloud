package com.allinabc.cloud.attach.util;

import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/5/11 19:50
 **/
@Component
@Slf4j
public class FtpUtil {

    private static String host;

    private static int port;

    private static String userName;

    private static String password;



//    /**
//     *  读ftp上的文件，并将其转换成base64
//     * @param remoteFileName ftp服务器上的文件名
//     * @return
//     */
//    public void downLoadFile(String remoteDir,String remoteFileName, OutputStream outputStream){
////        FTPClient ftpClient = connectFtpServer();
//        Ftp ftpBean = getFtpBean();
//        FTPClient ftpClient = ftpBean.getClient();
//        if (ftpClient == null){
//            return ;
//        }
//
//        InputStream inputStream = null;
//
//        try {
//            ftpClient.changeWorkingDirectory(remoteDir);
//            FTPFile[] ftpFiles = ftpClient.listFiles(remoteDir);
//            Boolean flag = false;
//            //遍历当前目录下的文件，判断要读取的文件是否在当前目录下
//            for (FTPFile ftpFile:ftpFiles){
//                if (ftpFile.getName().equals(remoteFileName)){
//                    flag = true;
//                    break;
//                }
//            }
//            if (!flag){
//                log.error("directory：{}下没有 {}",remoteDir,remoteFileName);
//
//            }
//
//            inputStream =  ftpClient.retrieveFileStream(remoteDir + "/"+new String(remoteFileName.getBytes("UTF-8"), "iso-8859-1"));
//
//            //获取待读文件输入流
//           // inputStream = ftpClient.retrieveFileStream(remoteDir+remoteFileName);
//
//            //inputStream.available() 获取返回在不阻塞的情况下能读取的字节数，正常情况是文件的大小
//            //response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(remoteFileName, "UTF-8"));
//
//            byte[] bytes = new byte[2048];
//            int len = 0;
//            while ((len = inputStream.read(bytes)) > 0) {
//                outputStream.write(bytes, 0, len);
//            }
//
//            log.info("read file {} success",remoteFileName);
//            ftpClient.logout();
//        } catch (IOException e) {
//            log.error("read file fail ----->>>{}",e.getCause());
//
//        }finally {
//            if (ftpClient.isConnected()){
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException e) {
//                    log.error("disconnect fail ------->>>{}",e.getCause());
//                }
//            }
//
//            if (inputStream != null){
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    log.error("inputStream close fail -------- {}",e.getCause());
//                }
//            }
//
//        }
//
//
//    }



    public static Ftp getFtpBean(){
       return new Ftp(host,port,userName,password, Charset.defaultCharset(), FtpMode.Passive);
    }



    @Value("${ftp.host}")
    public void setHost(String ftpHost) {
        host = ftpHost;
    }

    @Value("${ftp.port}")
    public void setPort(int ftpPort) {
        port = ftpPort;
    }

    @Value("${ftp.user}")
    public void setUserName(String user) {
        userName = user;
    }

    @Value("${ftp.password}")
    public void setPassword(String ftpPassword) {
        password = ftpPassword;
    }
}
