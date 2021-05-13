package com.allinabc.cloud.activiti.mapper;

import com.allinabc.cloud.activiti.pojo.dto.TaskParam;
import com.allinabc.cloud.activiti.pojo.vo.TaskModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author wangtaifeng
 * @Date 2021/3/19 16:43
 **/
public interface TaskMapper{


    /**
     * 模糊查询
     *
     * @param page
     * @param processDefKeyList
     * @param taskParam
     * @return
     */
    Page<TaskModel> findTaskIngFuzzySearch(IPage<TaskModel> page, @Param("ls") List<String> processDefKeyList, @Param("taskParam") TaskParam taskParam);

    /**
     * 非模糊查询
     * @return
     */
    Page<TaskModel> findTaskIng(IPage<TaskModel> page, @Param("taskParam") TaskParam taskParam);

    /**
     * 模糊查询已办
     * @param page
     * @param taskParam
     * @return
     */
    Page<TaskModel> findTaskDoneFuzzySearch(IPage<TaskModel> page,@Param("ls") List<String> processDefKeyList, @Param("taskParam")TaskParam taskParam);

    /**
     * 非模糊查询已办
     * @param page
     * @param taskParam
     * @return
     */
    Page<TaskModel> findTaskDone(IPage<TaskModel> page, @Param("taskParam")TaskParam taskParam);
}
