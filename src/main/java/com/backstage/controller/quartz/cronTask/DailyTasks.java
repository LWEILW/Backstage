package com.backstage.controller.quartz.cronTask;

import com.backstage.util.WeatherUtil;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 每日任务管理
 *
 * @author Liu wei
 * @date 2020-08-26 11:30
 **/
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class DailyTasks implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getTrigger().getJobDataMap();
        String invokeParam = (String) data.get("invokeParam");
        //在这里实现业务逻辑
        System.out.println(invokeParam);
        try {
            //测试获取实时天气1(包含风况，湿度)
            Map<String, Object> map1 = WeatherUtil.getTodayWeather1("盐城");
            Map<String, Object> map2 = WeatherUtil.getTodayWeather1("无锡");
            Map<String, Object> map3 = WeatherUtil.getTodayWeather1("襄阳");
            Map<String, Object> map4 = WeatherUtil.getTodayWeather1("南京");
            Map<String, Object> map5 = WeatherUtil.getTodayWeather1("永州");
            Map<String, Object> map6 = WeatherUtil.getTodayWeather1("上海");
            Map<String, Object> map7 = WeatherUtil.getTodayWeather1("杭州");

            System.out.println(map1);
            System.out.println(map2);
            System.out.println(map3);
            System.out.println(map4);
            System.out.println(map5);
            System.out.println(map6);
            System.out.println(map7);


            //盐城:101190701
            //无锡:101190201
            //襄樊:101200201
            //南京:101190101
            //永州:101251401
            //上海:101020100
            //西安:101110101
            //杭州:101210101
            //测试获取实时天气2(包含天气，温度范围)
            //这块填的是城市编码
            Map<String, Object> map = WeatherUtil.getTodayWeather2("101200201");
            System.out.println(map.get("city") + "\t" + map.get("temp1") + "\t" + map.get("temp2") + "\t" + map.get("weather") + "\t" + map.get("ptime"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
