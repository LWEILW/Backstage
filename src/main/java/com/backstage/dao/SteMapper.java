package com.backstage.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: SteMapper
 * @Author Zhu Jiabing
 * @Date 2019-12-19 17:16
**/
@Mapper
public interface SteMapper extends BaseMapper<JSONObject> {

    /**
     * select * from ${tableName}
     *
     * @param tableName 表名
     * @return List<JSONObject>
     */
    List<JSONObject> getAll(@Param("tableName") String tableName);

    /**
     * select * from ${tableName}
     *
     * @param tableName 表名
     * @return JSONObject
     */
    JSONObject getOne(@Param("tableName") String tableName);

    /**
     * select * from ${tableName} where ${whereCase}
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @return List<JSONObject>
     */
    List<JSONObject> getAllWithWhereCase(@Param("tableName") String tableName, @Param("whereCase") String whereCase);

    /**
     * select * from ${tableName} where ${whereCase}
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @return JSONObject
     */
    JSONObject getOneWithWhereCase(@Param("tableName") String tableName, @Param("whereCase") String whereCase);

    /**
     * select * from ${tableName} where ${whereCase} ORDER BY ${sortField} ${sortMode}
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @param sortField 排序字段
     * @param sortMode 排序方式
     * @return List<JSONObject>
     */
    List<JSONObject> getAllWithWhereCaseOrder(@Param("tableName") String tableName, @Param("whereCase") String whereCase, @Param("sortField") String sortField, @Param("sortMode") String sortMode);

    /**
     * delete from ${tableName} where ${whereCase}
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @return int
     */
    int deleteAll(@Param("tableName") String tableName, @Param("whereCase") String whereCase);

    /**
     * update ${tableName} set ${setParam} = '${valParam}' where ${field}
     *
     * @param tableName 表名
     * @param setParam 修改字段
     * @param valParam 修改值
     * @param field 过滤条件
     * @return int
     */
    int updateAll(@Param("tableName") String tableName, @Param("setParam") String setParam, @Param("valParam") String valParam, @Param("field") String field);

    /**
     * update ${tableName} set ${setParam} where ${field}
     *
     * @param tableName 表名
     * @param setParam 修改语句
     * @param field 过滤条件
     * @return int
     */
    int updateOneTable(@Param("tableName") String tableName, @Param("setParam") String setParam, @Param("field") String field);

    /**
     * update ${tableName} set ${setParam} = to_date('${valParam}','yyyy-mm-dd hh24:mi:ss') where ${field}
     *
     * @param tableName 表名
     * @param setParam 修改字段
     * @param valParam 修改值
     * @param field 过滤条件
     * @return int
     */
    int updateDate(@Param("tableName") String tableName, @Param("setParam") String setParam, @Param("valParam") String valParam, @Param("field") String field);

    /**
     * 查询总记录条数
     * @param tableName 表名
     * @param setParam 修改字段
     * @param valParam 修改值
     * @return int
     */
    Integer selectCount(@Param("tableName") String tableName, @Param("setParam") String setParam, @Param("valParam") String valParam);

    /**
     * 是否启动过工作流
     * @param businessId 单据ID
     * @param businessKey 单据KEY
     * @return 是否启动过
     */
    Integer isActiviti(@Param("businessId") String businessId, @Param("businessKey") String businessKey);;
}
