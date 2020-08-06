package com.backstage.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @Description: 通用公共方法
 * @Author LW
 * @Date 2020-07-22 16:30
 **/
public interface ShareService extends IService<JSONObject> {

    /**
     * 根据表名全表查询
     *
     * @param tableName 表名
     * @return List<JSONObject>
     */
    List<JSONObject> getAll(String tableName);

    /**
     * 根据表名单条记录查询
     *
     * @param tableName 表名
     * @return JSONObject
     */
    JSONObject getOne(String tableName);

    /**
     * 根据表名，过滤条件全表查询
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @return List<JSONObject>
     */
    List<JSONObject> getAll(String tableName, String whereCase);

    /**
     * 根据表名，过滤条件单条记录查询
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @return JSONObject
     */
    JSONObject getOne(String tableName, String whereCase);

    /**
     * 根据表名，过滤条件全表查询，并排序
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @param sortField 排序字段
     * @param sortMode 排序方式
     * @return List<JSONObject>
     */
    List<JSONObject> getAll(String tableName, String whereCase, String sortField, String sortMode);

    /**
     * 根据表名，过滤条件全表查询，并排序分页
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @param sortField 排序字段
     * @param sortMode 排序方式
     * @param currentPage 当前页码
     * @param pageSize 页面大小
     * @return PageInfo<JSONObject>
     */
//    PageInfo<JSONObject> getAll(String tableName, String whereCase, String sortField, String sortMode, Integer currentPage, Integer pageSize);

    /**
     * 根据过滤条件删除数据
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @return int
     */
    int deleteOne(String tableName, String whereCase);

    /**
     * 根据修改字段，修改值，过滤条件修改数据
     *
     * @param tableName 表名
     * @param setParam 修改字段
     * @param valParam 修改值
     * @param field 过滤条件
     * @return int
     */
    int updateOne(String tableName, String setParam, String valParam, String field);

    /**
     * 根据修改语句，过滤条件修改数据
     *
     * @param tableName 表名
     * @param setParam 修改语句
     * @param field 过滤条件
     * @return int
     */
    int updateOneTable(String tableName, String setParam, String field);

    /**
     * 根据修改字段，修改时间值，过滤条件修改数据
     *
     * @param tableName 表名
     * @param setParam 修改字段
     * @param valParam 修改时间值
     * @param field 过滤条件
     * @return int
     */
    int updateDate(String tableName, String setParam, String valParam, String field);

    /**
     * 查询总记录条数
     * @param tableName 表名
     * @param setParam 修改字段
     * @param valParam 修改值
     * @return int
     */
    Integer selectCount(String tableName, String setParam, String valParam);

    /**
     * 是否启动过工作流方法
     * @param businessId 单据ID
     * @param businessKey 单据KEY
     * @return 是否启动过
     */
    Integer isActiviti(String businessId, String businessKey);

//    /**
//     * 数据限制
//     *
//     * @param whereCase         过滤条件
//     * @param structureColumn   组织过滤条件
//     * @param siteColumn        线路过滤条件
//     * @return String
//     */
//    String dataLimit(String whereCase, String structureColumn, String siteColumn);
}
