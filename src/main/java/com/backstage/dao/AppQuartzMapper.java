package com.backstage.dao;

import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.AppQuartz;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 定时器管理
 *
 * @author Liu wei
 * @date 2020-08-22 13:30
 **/
@Mapper
public interface AppQuartzMapper extends BaseMapper<JSONObject> {

    /**
     * 定时器台账
     *
     * @param page
     * @param appQuartz
     * @return
     */
    List<AppQuartz> quartzList(Pagination page, @Param("quartz") AppQuartz appQuartz);


    /**
     * 定时器详情
     *
     * @param quartzId
     * @return
     */
    AppQuartz detailsQuartz(@Param("quartzId") int quartzId);


    /**
     * 定时器创建
     *
     * @param appQuartz
     * @return
     */
    int createQuartz(@Param("total") AppQuartz appQuartz);


    /**
     * 获取定时器创建ID
     *
     * @return
     */
    List<AppQuartz> getQuartzId();


    /**
     * 定时器更新
     *
     * @param appQuartz
     * @return
     */
    int updateQuartz(@Param("total") AppQuartz appQuartz);


    /**
     * 定时器批量删除
     *
     * @param list
     * @return
     */
    int deleteQuartzList(@Param("list") List<Integer> list);

}
