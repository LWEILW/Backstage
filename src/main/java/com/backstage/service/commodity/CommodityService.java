package com.backstage.service.commodity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.commodity.Commodity;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;


/**
 * 商品管理
 *
 * @author Liu wei
 * @date 2020-07-22 16:00
 **/
public interface CommodityService extends IService<JSONObject> {

    /**
     * 商品台账
     *
     * @param page
     * @return
     */
    Page<Commodity> commodityList(Page<Commodity> page);


    /**
     * 商品详情
     *
     * @param commodityId
     * @return
     */
    Commodity commodityDetails(int commodityId);


    /**
     * 商品新建/编辑
     *
     * @param commodity
     * @return
     */
    boolean commoditySave(Commodity commodity);


    /**
     * 商品批量删除
     *
     * @param idList
     * @return
     */
    boolean commodityDeleteAll(JSONArray idList);


}
