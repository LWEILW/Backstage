package com.backstage.server.article;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.article.Article;

/**
 * 文章管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
public interface ArticleService {

    /**
     * 文章台账
     *
     * @param page
     * @return
     */
    Page<Article> articleList(Page<Article> page);


    /**
     * 文章详情
     *
     * @param articleId
     * @return
     */
    Article articleDetails(int articleId);


    /**
     * 文章新建/编辑
     *
     * @param article
     * @return
     */
    boolean articleSave(Article article);


    /**
     * 文章删除
     *
     * @param articleId
     * @return
     */
    int articleDelete(int articleId);

    /**
     * 文章批量删除
     *
     * @param idList
     * @return
     */
    boolean articleDeleteAll(JSONArray idList);


}
