package com.allinabc.cloud.attach.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 存储方式
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum StorageTypeEnum {
    STORAGE_TYPE_S3("S3","S3"),
    STORAGE_TYPE_LOCAL("Local","Local"),
    STORAGE_TYPE_FTP("Ftp","Ftp");


    private String code;
    private String name;

}
