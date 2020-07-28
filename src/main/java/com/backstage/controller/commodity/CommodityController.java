package com.backstage.controller.commodity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.backstage.entity.commodity.Commodity;
import com.backstage.service.commodity.CommodityService;
import com.backstage.util.Result;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品管理
 *
 * @author Liu wei
 * @date 2020-07-22 16:00
 **/
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    /**
     * 商品台账
     *
     * @param data
     * @return
     */
    @PostMapping("/commodityList")
    public Result commodityList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");

        Page<Commodity> page = new Page<>(currentPage, pageSize);
        page = commodityService.commodityList(page);
        return Result.success("商品台账")
                .data("commodityList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 商品详情
     *
     * @param commodityId
     * @return
     */
    @GetMapping("/commodityDetails/{commodityId}")
    public Result commodityDetails(@PathVariable("commodityId") int commodityId) {
        Commodity commodity = commodityService.commodityDetails(commodityId);
        return Result.success("商品详情")
                .data("commodity", commodity);
    }


    /**
     * 商品新建/编辑
     *
     * @param data
     * @return
     */
    @PostMapping("/commoditySave")
    public String commoditySave(@RequestBody String data) {
        Commodity commodity = JSONObject.parseObject(data, Commodity.class);

        boolean succ = commodityService.commoditySave(commodity);
        if (succ) {
            return "保存成功";
        } else {
            return "保存失败";
        }
    }


    /**
     * 商品批量删除
     *
     * @param data
     * @return
     */
    @PostMapping("/deleteCommodityAll")
    public String deleteCommodityAll(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        JSONArray idList = (JSONArray) obj.get("idList");

        boolean succ = commodityService.commodityDeleteAll(idList);
        if (succ) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }


}
