package com.backstage.entity.article;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 博客文章
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@Data
@TableName(value = "article")
public class Article {

    @TableField(value = "articleId", el = "博客ID")
    private int articleId;

    @TableField(value = "articleName", el = "博客名称")
    private String articleName;

    @TableField(value = "articleTitle", el = "博客标题")
    private String articleTitle;

    @TableField(value = "articleContent", el = "博客内容")
    private String articleContent;

    @TableField(value = "articleStatus", el = "博客状态")
    private Integer articleStatus;

    @TableField(value = "readingAmount", el = "阅读数量")
    private Integer readingAmount;

    @TableField(value = "publisher", el = "发布人")
    private String publisher;

    @TableField(value = "publisherTime", el = "发布时间")
    private String publisherTime;

    @TableField(value = "updatePerson", el = "更新人")
    private String updatePerson;

    @TableField(value = "updateDate", el = "更新时间")
    private String updateDate;

}
