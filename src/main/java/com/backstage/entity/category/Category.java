package com.backstage.entity.category;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 文章分类管理
 *
 * @author Liu wei
 * @date 2020-07-28 17:05
 **/
@Data
@TableName(value = "category")
public class Category {

    @TableField(value = "categoryId", el = "分类ID")
    private int categoryId;

    @TableField(value = "categoryName", el = "分类名称")
    private String categoryName;

    @TableField(value = "publisher", el = "发布人")
    private String publisher;

    @TableField(value = "publisherTime", el = "发布时间")
    private String publisherTime;

    @TableField(value = "updatePerson", el = "更新人")
    private String updatePerson;

    @TableField(value = "updateTime", el = "更新时间")
    private String updateTime;
}
