package com.backstage.entity.commodity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 商品管理
 *
 * @author Liu wei
 * @date 2020-07-22 16:00
 **/
@Data
@TableName(value = "commodity")
public class Commodity {


    @TableField(value = "commodityId", el = "商品ID")
    private int commodityId;

    @TableField(value = "commodityName", el = "商品名称")
    private String commodityName;

    @TableField(value = "commodityDesc", el = "商品描述")
    private String commodityDesc;

    @TableField(value = "commodityPrice", el = "商品价格")
    private String commodityPrice;

    @TableField(value = "commodityStatus", el = "商品状态（0:下架 1:商家）")
    private int commodityStatus;

    @TableField(value = "browseCount", el = "浏览次数")
    private int browseCount;

    @TableField(value = "publisher", el = "发布人")
    private String publisher;

    @TableField(value = "publisherTime", el = "发布时间")
    private String publisherTime;

    @TableField(value = "updatePerson", el = "更新人")
    private String updatePerson;

    @TableField(value = "updateTime", el = "更新时间")
    private String updateTime;

}
