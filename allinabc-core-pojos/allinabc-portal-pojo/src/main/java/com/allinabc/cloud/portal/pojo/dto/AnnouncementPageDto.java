package com.allinabc.cloud.portal.pojo.dto;

import com.allinabc.cloud.common.web.pojo.param.BaseQueryParam;
import lombok.Data;

/**
 * @author Simon.Xue
 * @date 2/25/21 6:16 PM
 **/
@Data
public class AnnouncementPageDto extends BaseQueryParam {

    private String title;
    private String type;
    private String timeType;
}
