package com.backstage.service.impl.lable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.dao.label.LabelMapper;
import com.backstage.entity.label.Label;
import com.backstage.service.label.LabelService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章标签管理
 *
 * @author Liu wei
 * @date 2020-08-21 10:30
 **/
@Service("LabelService")
public class LabelServiceImpl extends ServiceImpl<LabelMapper, JSONObject> implements LabelService {



    /**
     * 标签台账
     *
     * @param page
     * @return
     */
    @Override
    public Page<Label> labelList(Page<Label> page) {

        return page.setRecords(baseMapper.labelList(page));
    }


    /**
     * 标签详情
     *
     * @param labelId
     * @return
     */
    @Override
    public Label labelDetails(int labelId) {

        return baseMapper.labelDetails(labelId);
    }


    /**
     * 标签新建/编辑
     *
     * @param label
     * @return
     */
    @Override
    public Boolean labelSave(Label label) {
        if (label.getLabelId() != 0) {
            // 编辑
            if (baseMapper.labelUpdate(label) == 1) {
                return true;
            }
        } else {
            // 创建
            if (baseMapper.labelCreate(label) == 1) {
                return true;
            }
        }
        return false;
    }


    /**
     * 标签批量删除
     *
     * @param idList
     * @return
     */
    @Override
    public Boolean labelDeleteAll(JSONArray idList) {
        //定义装有需要删除的ID集合
        List<Integer> list = new ArrayList<Integer>();
        //遍历原有数据
        for (Object id : idList) {
            //封装到新集合里
            list.add((Integer) id);
        }
        int succ = baseMapper.labelDeleteAll(list);
        if (succ == 0) {
            return false;
        }
        return true;
    }


}
