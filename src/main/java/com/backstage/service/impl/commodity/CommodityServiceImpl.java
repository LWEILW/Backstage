package com.backstage.service.impl.commodity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.dao.admin.RoleMapper;
import com.backstage.dao.commodity.CommodityMapper;
import com.backstage.entity.commodity.Commodity;
import com.backstage.service.ShareService;
import com.backstage.service.commodity.CommodityService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, JSONObject> implements CommodityService {

    @Autowired
    private ShareService shareService;

    /**
     * 商品台账
     *
     * @param page
     * @return
     */
    @Override
    public Page<Commodity> commodityList(Page<Commodity> page) {
        List<Commodity> commodityList = baseMapper.commodityList(page);
        for (Commodity com : commodityList) {
            com.setCommodityFileList(baseMapper.commodityFileListById(com.getCommodityId()));
        }
        return page.setRecords(commodityList);
    }


    /**
     * 商品详情
     *
     * @param commodityId
     * @return
     */
    @Override
    public Commodity commodityDetails(int commodityId) {
        Commodity commodity = baseMapper.commodityDetails(commodityId);
        // 商品图片列表
        commodity.setCommodityFileList(baseMapper.commodityFileListById(commodityId));
        return commodity;
    }


    /**
     * 商品新建/编辑
     *
     * @param commodity
     * @return
     */
    @Override
    public boolean commoditySave(Commodity commodity, List<JSONObject> productImages) {
        if (commodity.getCommodityId() != 0) {
            // 编辑
            if (baseMapper.commodityUpdate(commodity) == 1) {
                // 插入商品图片
                for (JSONObject obj : productImages) {
                    String url = obj.getString("url");
                    baseMapper.insertCommodityFile(commodity.getCommodityId(), url);
                }
                return true;
            }
        } else {
            // 创建
            if (baseMapper.commodityCreate(commodity) == 1) {
                int commodityId = baseMapper.getCommodityId().get(0).getCommodityId();
                // 插入商品图片
                for (JSONObject obj : productImages) {
                    String url = obj.getString("url");
                    baseMapper.insertCommodityFile(commodityId, url);
                }
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
        int succ = baseMapper.commodityDeleteAll(list);
        if (succ == 0) {
            return false;
        }
        return true;
    }


}
