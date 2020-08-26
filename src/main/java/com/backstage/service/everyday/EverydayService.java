package com.backstage.service.everyday;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.everyday.Everyday;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * 每日任务管理
 *
 * @author Liu wei
 * @date 2020-08-26 11:30
 **/
public interface EverydayService extends IService<JSONObject> {

    /**
     * 每日任务台账
     *
     * @param page
     * @return
     */
    Page<Everyday> everydayList(Page<Everyday> page);


    /**
     * 每日任务详情
     *
     * @param everydayId
     * @return
     */
    Everyday everydayDetails(int everydayId);


    /**
     * 每日任务新建/编辑
     *
     * @param everyday
     * @return
     */
    Boolean everydaySave(Everyday everyday);


    /**
     * 每日任务批量删除
     *
     * @param idList
     * @return
     */
    Boolean everydayDeleteAll(JSONArray idList);

}
