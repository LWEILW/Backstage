package com.backstage.controller.category;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.category.Category;
import com.backstage.service.ShareService;
import com.backstage.service.category.CategoryService;
import com.backstage.util.Result;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章分类管理
 *
 * @author Liu wei
 * @date 2020-07-28 17:05
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ShareService shareService;

    /**
     * 分类台账
     *
     * @param data
     * @return
     */
    @PostMapping("/categoryList")
    public Result categoryList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");

        Page<Category> page = new Page<>(currentPage, pageSize);
        page = categoryService.categoryList(page);
        return Result.success("分类台账")
                .data("categoryList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 分类详情
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/categoryDetails/{categoryId}")
    public Result categoryDetails(@PathVariable("categoryId") int categoryId) {

        Category category = categoryService.categoryDetails(categoryId);
        return Result.success("分类详情")
                .data("category", category);
    }


    /**
     * 分类新建/编辑
     *
     * @param data
     * @return
     */
    @PostMapping("/categorySave")
    public Result categorySave(@RequestBody String data) {
        Category category = JSONObject.parseObject(data, Category.class);

        Boolean result = categoryService.categorySave(category);
        if (result) {
            return Result.success("保存成功");
        } else {
            return Result.fail("保存失败");
        }
    }


    /**
     * 分类批量删除
     *
     * @param data
     * @return
     */
    @PostMapping("/deleteCategoryAll")
    public Result deleteCategoryAll(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        JSONArray idList = (JSONArray) obj.get("idList");

        Boolean result = categoryService.categoryDeleteAll(idList);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 分页列表
     *
     * @return
     */
    @GetMapping("/getCategoryList")
    public Result getCategoryList() {
        try {

            List<JSONObject> categoryList = shareService.getAll("article_category");
            return Result.success("分页列表")
                    .data("categoryList", categoryList);
        } catch (Exception ex) {
            return Result.fail("分页列表获取失败");
        }
    }
}
