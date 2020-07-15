package com.backstage.util;

import com.alibaba.fastjson.JSONObject;

/**
 * HTTP接口通用返回值，以json字符串的形式
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
public class Result extends JSONObject {

    /*
     * 1    成功
     * -1   失败
     * 4401 登陆超时
     */

    /**
     * 全参构造
     *
     * @param status  string 需要返回的状态
     * @param message string 需要返回的消息
     * @author LW
     */
    private Result(int status, String message) {
        this.put("status", status);
        this.put("message", message);
    }


    /**
     * 调用方法
     * 成功不附加消息
     *
     * @return com.ste.util.Result
     * @author 张永清
     */
    public static Result success() {
        return new Result(1, "");
    }


    /**
     * 调用方法
     * 成功附加消息
     *
     * @param message 需要返回的消息
     * @return com.ste.util.Result
     * @author 张永清
     */
    public static Result success(String message) {
        return new Result(1, message);
    }

    /**
     * 调用方法
     * 失败不附加消息
     *
     * @return com.ste.util.Result
     * @author 张永清
     */
    public static Result fail() {
        return new Result(-1, "");
    }

    /**
     * 调用方法
     * 失败附加消息
     *
     * @param message 需要返回的消息
     * @return com.ste.util.Result
     * @author 张永清
     */
    public static Result fail(String message) {
        return new Result(-1, message);
    }

    /**
     * 调用方法
     * 登陆超时
     *
     * @return com.ste.util.Result
     * @author 张永清
     */
    public static Result logout() {
        return new Result(4401, "登陆超时");
    }

    /**
     * 调用方法
     * 设置message，若已存在设置过的message，则覆盖
     *
     * @param message 需要返回的消息
     * @return com.ste.util.Result
     * @author 张永清
     */
    public Result message(String message) {
        this.put("message", message);
        return this;
    }

    /**
     * 调用方法
     * 设置data，若已存在对应的键的数据，则覆盖
     *
     * @param key   需要返回的数据的键
     * @param value 需要返回的数据的值
     * @return com.ste.util.Result
     * @author 张永清
     */
    public Result data(String key, Object value) {
        this.put(key, value);
        return this;
    }

}
