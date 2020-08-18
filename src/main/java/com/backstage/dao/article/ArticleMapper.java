package com.backstage.dao.article;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.backstage.entity.article.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@Mapper
public interface ArticleMapper extends BaseMapper<JSONObject> {


    /**
     * 文章台账
     *
     * @param page
     * @return
     */
    List<Article> articleList(Pagination page);


    /**
     * 文章详情
     *
     * @param articleId
     * @return
     */
    Article articleDetails(@Param("articleId") int articleId);


    /**
     * 文章创建
     *
     * @param article
     * @return
     */
    int articleCreate(@Param("total") Article article);

    /**
     * 文章编辑
     *
     * @param article
     * @return
     */
    int articleUpdate(@Param("total") Article article);

    /**
     * 文章删除
     *
     * @param articleId
     * @return
     */
    int articleDelete(@Param("articleId") int articleId);

    /**
     * 文章批量删除
     *
     * @param list
     * @return
     */
    int articleDeleteAll(@Param("list") List<Integer> list);


}
