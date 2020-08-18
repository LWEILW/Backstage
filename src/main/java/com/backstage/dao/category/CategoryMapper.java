package com.backstage.dao.category;

import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.category.Category;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章分类管理
 *
 * @author Liu wei
 * @date 2020-07-28 17:05
 **/
@Mapper
public interface CategoryMapper extends BaseMapper<JSONObject> {

    /**
     * 分类台账
     *
     * @param page
     * @return
     */
    List<Category> categoryList(Pagination page);


    /**
     * 分类详情
     *
     * @param categoryId
     * @return
     */
    Category categoryDetails(@Param("categoryId") int categoryId);


    /**
     * 分类创建
     *
     * @param category
     * @return
     */
    int categoryCreate(@Param("total") Category category);

    /**
     * 分类编辑
     *
     * @param category
     * @return
     */
    int categoryUpdate(@Param("total") Category category);


    /**
     * 分类批量删除
     *
     * @param list
     * @return
     */
    int categoryDeleteAll(@Param("list") List<Integer> list);


}
