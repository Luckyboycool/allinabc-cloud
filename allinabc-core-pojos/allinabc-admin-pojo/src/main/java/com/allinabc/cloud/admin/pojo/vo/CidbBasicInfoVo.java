package com.allinabc.cloud.admin.pojo.vo;

import com.allinabc.cloud.admin.pojo.po.CidbBasicInfo;
import com.allinabc.cloud.admin.pojo.po.CidbDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * CIDB_BASIC_INFO
 * @author 
 */
@Data
public class CidbBasicInfoVo extends CidbBasicInfo implements Serializable {
    @JsonIgnore
    private List<CidbDetail> cidbDetailList;
    @JsonIgnore
    private Map<String,List<CidbDetail>> map;
    private Map<String,Map<String, List<EmpVO>>> maps;
    private static final long serialVersionUID = 1L;

    @Data
    public static class EmpVO {
        private String id;
        private String name;
    }


}