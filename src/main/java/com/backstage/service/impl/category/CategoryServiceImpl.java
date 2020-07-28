package com.backstage.service.impl.category;

import com.alibaba.fastjson.JSONArray;
import com.backstage.dao.category.CategoryMapper;
import com.backstage.entity.category.Category;
import com.backstage.service.category.CategoryService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章分类管理
 *
 * @author Liu wei
 * @date 2020-07-28 17:05
 **/
@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 分类台账
     *
     * @param page
     * @return
     */
    @Override
    public Page<Category> categoryList(Page<Category> page) {

        return page.setRecords(categoryMapper.categoryList(page));
    }


    /**
     * 分类详情
     *
     * @param categoryId
     * @return
     */
    @Override
    public Category categoryDetails(int categoryId) {
        return categoryMapper.categoryDetails(categoryId);
    }


    /**
     * 分类新建/编辑
     *
     * @param category
     * @return
     */
    @Override
    public boolean categorySave(Category category) {
        if (category.getCategoryId() != 0) {
            // 编辑
            if (categoryMapper.categoryUpdate(category) == 1) {
                return true;
            }
        } else {
            // 创建
            if (categoryMapper.categoryCreate(category) == 1) {
                return true;
            }
        }
        return false;
    }


    /**
     * 分类批量删除
     *
     * @param idList
     * @return
     */
    @Override
    public boolean categoryDeleteAll(JSONArray idList) {
        //定义装有需要删除的ID集合
        List<Integer> list = new ArrayList<Integer>();
        //遍历原有数据
        for (Object id : idList) {
            //封装到新集合里
            list.add((Integer) id);
        }
        int succ = categoryMapper.categoryDeleteAll(list);
        if (succ == 0) {
            return false;
        }
        return true;
    }


}