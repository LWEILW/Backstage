package com.backstage.dao.label;

import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.label.Label;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章标签管理
 *
 * @author Liu wei
 * @date 2020-08-21 10:30
 **/
@Mapper
public interface LabelMapper extends BaseMapper<JSONObject> {

    /**
     * 标签台账
     *
     * @param page
     * @return
     */
    List<Label> labelList(Pagination page);


    /**
     * 标签详情
     *
     * @param labelId
     * @return
     */
    Label labelDetails(@Param("labelId") int labelId);


    /**
     * 标签创建
     *
     * @param label
     * @return
     */
    int labelCreate(@Param("total") Label label);

    /**
     * 标签编辑
     *
     * @param label
     * @return
     */
    int labelUpdate(@Param("total") Label label);


    /**
     * 标签批量删除
     *
     * @param list
     * @return
     */
    int labelDeleteAll(@Param("list") List<Integer> list);


}
