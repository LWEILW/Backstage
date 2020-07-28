package com.backstage.server.impl.article;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.dao.article.ArticleMapper;
import com.backstage.entity.article.Article;
import com.backstage.server.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@Service("ArticleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 文章台账
     *
     * @param page
     * @return
     */
    @Override
    public Page<Article> articleList(Page<Article> page) {

        return page.setRecords(articleMapper.articleList(page));
    }


    /**
     * 文章详情
     *
     * @param articleId
     * @return
     */
    @Override
    public Article articleDetails(int articleId) {
        return articleMapper.articleDetails(articleId);
    }


    /**
     * 文章新建/编辑
     *
     * @param article
     * @return
     */
    @Override
    public boolean articleSave(Article article) {
        if (article.getArticleId() != 0) {
            // 编辑
            if (articleMapper.articleUpdate(article) == 1) {
                return true;
            }
        } else {
            // 创建
            if (articleMapper.articleCreate(article) == 1) {
                return true;
            }
        }
        return false;
    }


    /**
     * 文章删除
     *
     * @param articleId
     * @return
     */
    @Override
    public int articleDelete(int articleId) {

        return articleMapper.articleDelete(articleId);
    }


    /**
     * 文章批量删除
     *
     * @param idList
     * @return
     */
    @Override
    public boolean articleDeleteAll(JSONArray idList) {
        //定义装有需要删除的ID集合
        List<Integer> list = new ArrayList<Integer>();
        //遍历原有数据
        for (Object id : idList) {
            //封装到新集合里
            list.add((Integer) id);
        }
        int succ = articleMapper.articleDeleteAll(list);
        if (succ == 0) {
            return false;
        }
        return true;
    }


}
