package com.allinabc.common.mybatis.service;

import com.allinabc.cloud.common.core.service.CommonService;
import com.allinabc.cloud.common.web.pojo.param.PropertyFilter;
import com.allinabc.cloud.common.web.pojo.param.QueryParam;
import com.allinabc.cloud.common.web.pojo.resp.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2020/12/18 15:02
 **/
public interface MybatisCommonService<T> extends CommonService<T>, IService<T> {


}
