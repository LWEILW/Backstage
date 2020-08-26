package com.backstage.entity.everyday;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 每日任务管理
 *
 * @author Liu wei
 * @date 2020-08-26 11:30
 **/
@Data
@TableName(value = "everyday")
public class Everyday {

    @TableField(value = "labelId", el = "每日任务ID")
    private int everydayId;

    @TableField(value = "dailyDate", el = "日期")
    private String dailyDate;

    @TableField(value = "dailyWeek", el = "星期几")
    private String dailyWeek;

    @TableField(value = "weather", el = "天气")
    private String weather;

    @TableField(value = "temperature", el = "温度")
    private String temperature;

    @TableField(value = "windDirection", el = "风向")
    private String windDirection;

    @TableField(value = "address", el = "地址")
    private String address;
}
