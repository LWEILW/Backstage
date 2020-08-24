package com.backstage.controller.label;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.label.Label;
import com.backstage.service.ShareService;
import com.backstage.service.label.LabelService;
import com.backstage.util.Result;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章标签管理
 *
 * @author Liu wei
 * @date 2020-08-21 10:30
 **/
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private ShareService shareService;

    /**
     * 标签台账
     *
     * @param data
     * @return
     */
    @PostMapping("/labelList")
    public Result labelList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");

        Page<Label> page = new Page<>(currentPage, pageSize);
        page = labelService.labelList(page);
        return Result.success("标签台账")
                .data("labelList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 标签详情
     *
     * @param labelId
     * @return
     */
    @GetMapping("/labelDetails/{labelId}")
    public Result labelDetails(@PathVariable("labelId") int labelId) {

        Label label = labelService.labelDetails(labelId);
        return Result.success("标签详情")
                .data("label", label);
    }


    /**
     * 标签新建/编辑
     *
     * @param data
     * @return
     */
    @PostMapping("/labelSave")
    public Result labelSave(@RequestBody String data) {
        Label label = JSONObject.parseObject(data, Label.class);

        Boolean result = labelService.labelSave(label);
        if (result) {
            return Result.success("保存成功");
        } else {
            return Result.fail("保存失败");
        }
    }


    /**
     * 标签批量删除
     *
     * @param data
     * @return
     */
    @PostMapping("/deleteLabelAll")
    public Result deleteLabelAll(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        JSONArray idList = (JSONArray) obj.get("idList");

        Boolean result = labelService.labelDeleteAll(idList);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 标签列表
     *
     * @return
     */
    @GetMapping("/getLabelList")
    public Result getLabelList() {
        try {

            List<JSONObject> labelList = shareService.getAll("article_label");
            return Result.success("标签列表")
                    .data("labelList", labelList);
        } catch (Exception ex) {
            return Result.fail("标签列表获取失败");
        }
    }
}
