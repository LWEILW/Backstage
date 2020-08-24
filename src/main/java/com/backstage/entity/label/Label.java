package com.backstage.entity.label;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 文章标签管理
 *
 * @author Liu wei
 * @date 2020-08-21 10:30
 **/
@Data
@TableName(value = "label")
public class Label {


    @TableField(value = "labelId", el = "标签ID")
    private int labelId;

    @TableField(value = "labelName", el = "标签名称")
    private String labelName;

    @TableField(value = "publisher", el = "发布人")
    private String publisher;

    @TableField(value = "publisherTime", el = "发布时间")
    private String publisherTime;

    @TableField(value = "updatePerson", el = "更新人")
    private String updatePerson;

    @TableField(value = "updateTime", el = "更新时间")
    private String updateTime;
}
