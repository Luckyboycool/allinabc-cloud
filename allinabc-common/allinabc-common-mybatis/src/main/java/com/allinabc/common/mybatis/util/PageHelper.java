package com.allinabc.common.mybatis.util;

import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

public class PageHelper {

    public static Page convert(IPage iPage) {
        return null != iPage ? new Page(iPage.getCurrent(), iPage.getSize(), iPage.getPages(), iPage.getTotal(), iPage.getRecords()) : new Page();
    }

}
