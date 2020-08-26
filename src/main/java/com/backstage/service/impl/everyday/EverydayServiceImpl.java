package com.backstage.service.impl.everyday;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.dao.everyday.EverydayMapper;
import com.backstage.entity.everyday.Everyday;
import com.backstage.service.everyday.EverydayService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 每日任务管理
 *
 * @author Liu wei
 * @date 2020-08-26 11:30
 **/
@Service("EverydayService")
public class EverydayServiceImpl extends ServiceImpl<EverydayMapper, JSONObject> implements EverydayService {



    /**
     * 每日任务台账
     *
     * @param page
     * @return
     */
    @Override
    public Page<Everyday> everydayList(Page<Everyday> page) {

        return page.setRecords(baseMapper.everydayList(page));
    }


    /**
     * 每日任务详情
     *
     * @param everydayId
     * @return
     */
    @Override
    public Everyday everydayDetails(int everydayId) {

        return baseMapper.everydayDetails(everydayId);
    }


    /**
     * 每日任务新建/编辑
     *
     * @param everyday
     * @return
     */
    @Override
    public Boolean everydaySave(Everyday everyday) {
        if (everyday.getEverydayId() != 0) {
            // 编辑
            if (baseMapper.everydayUpdate(everyday) == 1) {
                return true;
            }
        } else {
            // 创建
            if (baseMapper.everydayCreate(everyday) == 1) {
                return true;
            }
        }
        return false;
    }


    /**
     * 每日任务批量删除
     *
     * @param idList
     * @return
     */
    @Override
    public Boolean everydayDeleteAll(JSONArray idList) {
        //定义装有需要删除的ID集合
        List<Integer> list = new ArrayList<Integer>();
        //遍历原有数据
        for (Object id : idList) {
            //封装到新集合里
            list.add((Integer) id);
        }
        int succ = baseMapper.everydayDeleteAll(list);
        if (succ == 0) {
            return false;
        }
        return true;
    }


}
