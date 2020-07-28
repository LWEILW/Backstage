package com.backstage.service.impl.commodity;

import com.alibaba.fastjson.JSONArray;
import com.backstage.dao.commodity.CommodityMapper;
import com.backstage.entity.commodity.Commodity;
import com.backstage.service.commodity.CommodityService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理
 *
 * @author Liu wei
 * @date 2020-07-22 16:00
 **/
@Service("CommodityService")
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    /**
     * 商品台账
     *
     * @param page
     * @return
     */
    @Override
    public Page<Commodity> commodityList(Page<Commodity> page) {

        return page.setRecords(commodityMapper.commodityList(page));
    }


    /**
     * 商品详情
     *
     * @param commodityId
     * @return
     */
    @Override
    public Commodity commodityDetails(int commodityId) {
        return commodityMapper.commodityDetails(commodityId);
    }


    /**
     * 商品新建/编辑
     *
     * @param commodity
     * @return
     */
    @Override
    public boolean commoditySave(Commodity commodity) {
        if (commodity.getCommodityId() != 0) {
            // 编辑
            if (commodityMapper.commodityUpdate(commodity) == 1) {
                return true;
            }
        } else {
            // 创建
            if (commodityMapper.commodityCreate(commodity) == 1) {
                return true;
            }
        }
        return false;
    }


    /**
     * 商品批量删除
     *
     * @param idList
     * @return
     */
    @Override
    public boolean commodityDeleteAll(JSONArray idList) {
        //定义装有需要删除的ID集合
        List<Integer> list = new ArrayList<Integer>();
        //遍历原有数据
        for (Object id : idList) {
            //封装到新集合里
            list.add((Integer) id);
        }
        int succ = commodityMapper.commodityDeleteAll(list);
        if (succ == 0) {
            return false;
        }
        return true;
    }


}