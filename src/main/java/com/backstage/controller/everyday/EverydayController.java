package com.backstage.controller.everyday;

import com.alibaba.fastjson.JSONArray;
import com.backstage.entity.everyday.Everyday;
import com.backstage.service.ShareService;
import com.backstage.service.everyday.EverydayService;
import com.backstage.util.Result;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 每日任务管理
 *
 * @author Liu wei
 * @date 2020-08-26 11:30
 **/
@RestController
@RequestMapping("/everyday")
public class EverydayController {


    @Autowired
    private EverydayService everydayService;

    @Autowired
    private ShareService shareService;

    /**
     * 每日任务台账
     *
     * @param data
     * @return
     */
    @PostMapping("/everydayList")
    public Result everydayList(@RequestBody String data) {
        com.alibaba.fastjson.JSONObject obj = com.alibaba.fastjson.JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");

        Page<Everyday> page = new Page<>(currentPage, pageSize);
        page = everydayService.everydayList(page);
        return Result.success("每日任务台账")
                .data("everydayList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 每日任务详情
     *
     * @param everydayId
     * @return
     */
    @GetMapping("/everydayDetails/{everydayId}")
    public Result everydayDetails(@PathVariable("everydayId") int everydayId) {

        Everyday everyday = everydayService.everydayDetails(everydayId);
        return Result.success("每日任务详情")
                .data("everyday", everyday);
    }


    /**
     * 每日任务新建/编辑
     *
     * @param data
     * @return
     */
    @PostMapping("/everydaySave")
    public Result everydaySave(@RequestBody String data) {
        Everyday everyday = com.alibaba.fastjson.JSONObject.parseObject(data, Everyday.class);

        Boolean result = everydayService.everydaySave(everyday);
        if (result) {
            return Result.success("保存成功");
        } else {
            return Result.fail("保存失败");
        }
    }


    /**
     * 每日任务批量删除
     *
     * @param data
     * @return
     */
    @PostMapping("/deleteEverydayAll")
    public Result deleteEverydayAll(@RequestBody String data) {
        com.alibaba.fastjson.JSONObject obj = com.alibaba.fastjson.JSONObject.parseObject(data);
        JSONArray idList = (JSONArray) obj.get("idList");

        Boolean result = everydayService.everydayDeleteAll(idList);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 每日任务列表
     *
     * @return
     */
    @GetMapping("/getEverydayList")
    public Result getEverydayList() {
        try {
            List<com.alibaba.fastjson.JSONObject> everydayList = shareService.getAll("article_everyday");
            return Result.success("每日任务列表")
                    .data("everydayList", everydayList);
        } catch (Exception ex) {
            return Result.fail("每日任务列表获取失败");
        }
    }


}
