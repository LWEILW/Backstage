package com.backstage.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;


/**
 * 定时器管理
 *
 * @author Liu wei
 * @date 2020-08-22 13:30
 **/
@Data
@TableName(value = "app_quartz")
public class AppQuartz {

    @TableField(value = "quartzId", el = "定时器ID")
    private Integer quartzId;

    @TableField(value = "jobName", el = "任务名称")
    private String jobName;

    @TableField(value = "jobDesc", el = "任务描述")
    private String jobDesc;

    @TableField(value = "jobGroup", el = "任务分组")
    private String jobGroup;

    @TableField(value = "startTime", el = "任务开始时间")
    private String startTime;

    @TableField(exist = false, el = "任务结束时间")
    private String nextFireTime;

    @TableField(value = "cronExpression", el = "corn表达式")
    private String cronExpression;

    @TableField(value = "invokeParam", el = "需要传递的参数")
    private String invokeParam;

    @TableField(value = "classAddress", el = "类地址")
    private String classAddress;

    @TableField(value = "jobStatus", el = "状态")
    private String jobStatus;
}
