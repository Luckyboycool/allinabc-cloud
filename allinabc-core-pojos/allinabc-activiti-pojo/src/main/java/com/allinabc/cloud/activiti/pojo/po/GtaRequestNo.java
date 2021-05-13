package com.allinabc.cloud.activiti.pojo.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * GTA_REQUEST_NO
 * @author 
 */
@Data
public class GtaRequestNo implements Serializable {
    private String id;

    private String key;

    private String value;

    private GtaRequestNo() {

    }
    private static volatile GtaRequestNo instance;

    public static GtaRequestNo getInstance(){
        if (instance==null){
            synchronized (GtaRequestNo.class){
                if (instance==null){
                    instance=new GtaRequestNo();
                }
            }
        }
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private static final long serialVersionUID = 1L;
}