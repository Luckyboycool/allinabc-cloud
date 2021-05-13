package com.allinabc.cloud.notice.util;

import org.springframework.stereotype.Component;

import java.io.*;


@Component
public class FilesUtil {

    public void toFile (String filename,InputStream inputStream,String filePath) throws Exception{
        byte[] data = readInputStream(inputStream);
        if(data != null){
            String filepath =filePath + filename;
            File file = new File(filepath);
            if(file.exists()){
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data,0,data.length);
            fos.flush();
            fos.close();
        }
    }

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
}
