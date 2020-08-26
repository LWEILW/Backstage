package com.backstage.dao.everyday;

import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.everyday.Everyday;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 每日任务管理
 *
 * @author Liu wei
 * @date 2020-08-26 11:30
 **/
@Mapper
public interface EverydayMapper extends BaseMapper<JSONObject> {

    /**
     * 每日任务台账
     *
     * @param page
     * @return
     */
    List<Everyday> everydayList(Pagination page);


    /**
     * 每日任务详情
     *
     * @param everydayId
     * @return
     */
    Everyday everydayDetails(@Param("everydayId") int everydayId);


    /**
     * 每日任务创建
     *
     * @param everyday
     * @return
     */
    int everydayCreate(@Param("total") Everyday everyday);

    /**
     * 每日任务编辑
     *
     * @param everyday
     * @return
     */
    int everydayUpdate(@Param("total") Everyday everyday);


    /**
     * 每日任务批量删除
     *
     * @param list
     * @return
     */
    int everydayDeleteAll(@Param("list") List<Integer> list);


}
