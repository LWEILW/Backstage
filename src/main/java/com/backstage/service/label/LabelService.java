package com.backstage.service.label;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.label.Label;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * 文章标签管理
 *
 * @author Liu wei
 * @date 2020-08-21 10:30
 **/
public interface LabelService extends IService<JSONObject> {

    /**
     * 标签台账
     *
     * @param page
     * @return
     */
    Page<Label> labelList(Page<Label> page);


    /**
     * 标签详情
     *
     * @param labelId
     * @return
     */
    Label labelDetails(int labelId);


    /**
     * 标签新建/编辑
     *
     * @param label
     * @return
     */
    Boolean labelSave(Label label);


    /**
     * 标签批量删除
     *
     * @param idList
     * @return
     */
    Boolean labelDeleteAll(JSONArray idList);

}
