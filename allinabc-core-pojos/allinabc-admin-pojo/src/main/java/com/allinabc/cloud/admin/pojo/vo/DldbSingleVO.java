package com.allinabc.cloud.admin.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Simon.Xue
 * @date 2021/4/27 2:51 下午
 **/
@Data
public class DldbSingleVO implements Serializable {

    private DldbInfoVO dldbInfoVO;
    private List<DldbDetailVO> dldbDetailVOList;
}
