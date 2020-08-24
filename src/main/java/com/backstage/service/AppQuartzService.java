package com.backstage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.AppQuartz;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;


/**
 * @author Zhu jiabing
 */
public interface AppQuartzService extends IService<JSONObject> {

    /**
     * 定时器台账
     *
     * @param page
     * @param appQuartz
     * @return
     */
    Page<AppQuartz> quartzList(Page<AppQuartz> page, AppQuartz appQuartz);


    /**
     * 定时器详情
     *
     * @param quartzId
     * @return
     */
    AppQuartz detailsQuartz(int quartzId);


    /**
     * 定时器保存
     *
     * @param quartz
     * @return
     */
    Boolean createOrUpdateQuartz(AppQuartz quartz) throws Exception;


    /**
     * 定时器批量删除
     *
     * @param idList
     * @return
     */
    Boolean deleteQuartzList(JSONArray idList) throws Exception ;

}
