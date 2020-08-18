package com.backstage.service.category;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.category.Category;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * 文章分类管理
 *
 * @author Liu wei
 * @date 2020-07-28 17:05
 **/
public interface CategoryService extends IService<JSONObject> {

    /**
     * 分类台账
     *
     * @param page
     * @return
     */
    Page<Category> categoryList(Page<Category> page);


    /**
     * 分类详情
     *
     * @param categoryId
     * @return
     */
    Category categoryDetails(int categoryId);


    /**
     * 分类新建/编辑
     *
     * @param category
     * @return
     */
    boolean categorySave(Category category);


    /**
     * 分类批量删除
     *
     * @param idList
     * @return
     */
    boolean categoryDeleteAll(JSONArray idList);

}
