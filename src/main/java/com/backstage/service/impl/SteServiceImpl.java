package com.backstage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.backstage.dao.SteMapper;
import com.backstage.entity.admin.User;
import com.backstage.service.ShareService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: STEServiceImpl
 * @Author Zhu Jiabing
 * @Date 2019-12-19 17:16
 **/
@Service
public class SteServiceImpl extends ServiceImpl<SteMapper, JSONObject> implements ShareService {

    /**
     * select * from ${tableName} where ${whereCase}
     *
     * @param tableName 表名
     * @return List<JSONObject>
     */
    @Override
    public List<JSONObject> getAll(String tableName) {
        List<JSONObject> getAll = baseMapper.getAll(tableName);
        return getAll;
    }

    /**
     * select * from ${tableName} where ${whereCase}
     *
     * @param tableName 表名
     * @return JSONObject
     */
    @Override
    public JSONObject getOne(String tableName) {
        JSONObject getOne = baseMapper.getOne(tableName);
        return getOne;
    }

    /**
     * select * from ${tableName} where ${whereCase}
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @return List<JSONObject>
     */
    @Override
    public List<JSONObject> getAll(String tableName, String whereCase) {
        List<JSONObject> getAllWithWhereCase = baseMapper.getAllWithWhereCase(tableName, whereCase);
        return getAllWithWhereCase;
    }

    /**
     * select * from ${tableName} where ${whereCase}
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @return JSONObject
     */
    @Override
    public JSONObject getOne(String tableName, String whereCase) {
        JSONObject getOneWithWhereCase = baseMapper.getOneWithWhereCase(tableName, whereCase);
        return getOneWithWhereCase;
    }

    /**
     * select * from ${tableName} where ${whereCase} ORDER BY ${sortField} ${sortMode}
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @param sortField 排序字段
     * @param sortMode 排序方式
     * @return List<JSONObject>
     */
    @Override
    public List<JSONObject> getAll(String tableName, String whereCase, String sortField, String sortMode) {
        List<JSONObject> getAllWithWhereCaseOrder = baseMapper.getAllWithWhereCaseOrder(tableName,whereCase,sortField,sortMode);
        return getAllWithWhereCaseOrder;
    }

    /**
     * select * from ${tableName} where ${whereCase} ORDER BY ${sortField} ${sortMode}
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @param sortField 排序字段
     * @param sortMode 排序方式
     * @param currentPage 当前页码
     * @param pageSize 页面大小
     * @return PageInfo<JSONObject>
     */
//    @Override
//    public PageInfo<JSONObject> getAll(String tableName, String whereCase, String sortField, String sortMode, Integer currentPage, Integer pageSize ) {
//
//        PageHelper.startPage(currentPage, pageSize);
//        String asc = "ascending";
//        if(sortMode.equals(asc)){
//            sortMode = "asc";
//        }else{
//            sortMode = "desc";
//        }
//        List<JSONObject> getAll = baseMapper.getAllWithWhereCaseOrder(tableName,whereCase,sortField,sortMode);
//        PageInfo<JSONObject> pageInfo = new PageInfo<>(getAll);
//        return pageInfo;
//    }

    /**
     * delete from ${tableName} where ${whereCase}
     *
     * @param tableName 表名
     * @param whereCase 过滤条件
     * @return int
     */
    @Override
    public int deleteOne(String tableName, String whereCase) {
        int status = baseMapper.deleteAll(tableName,whereCase);
        return status;
    }

    /**
     * update ${tableName} set ${setParam} = to_date('${valParam}','yyyy-mm-dd hh24:mi:ss') where ${field}
     *
     * @param tableName 表名
     * @param setParam 修改字段
     * @param valParam 修改值
     * @param field 过滤条件
     * @return int
     */
    @Override
    public int updateOne(String tableName, String setParam, String valParam,String field) {
        int status = baseMapper.updateAll(tableName,setParam,valParam,field);
        return status;
    }

    /**
     * update ${tableName} set ${setParam} where ${field}
     *
     * @param tableName 表名
     * @param setParam 修改语句
     * @param field 过滤条件
     * @return int
     */
    @Override
    public int updateOneTable(String tableName, String setParam,String field) {
        int status = baseMapper.updateOneTable(tableName,setParam,field);
        return status;
    }

    /**
     * update ${tableName} set ${setParam} = to_date('${valParam}','yyyy-mm-dd hh24:mi:ss') where ${field}
     *
     * @param tableName 表名
     * @param setParam 修改字段
     * @param valParam 修改值
     * @param field 过滤条件
     * @return int
     */
    @Override
    public int updateDate(String tableName, String setParam, String valParam,String field) {
        int status = baseMapper.updateDate(tableName,setParam,valParam,field);
        return status;
    }

    @Override
    public Integer selectCount(String tableName, String setParam, String valParam){
        Integer count = baseMapper.selectCount(tableName, setParam, valParam);
        return count;
    }

    @Override
    public Integer isActiviti(String businessId, String businessKey) {
        return baseMapper.isActiviti(businessId, businessKey);
    }

//    /**
//     * 数据限制
//     *
//     * @param whereCase       过滤条件
//     * @param structureColumn 组织过滤条件
//     * @param siteColumn      线路过滤条件
//     * @return String
//     */
//    @Override
//    public String dataLimit(String whereCase, String structureColumn, String siteColumn) {
//        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
//        int structureId = currentUser.getStructureId();
//        int siteId = currentUser.getSiteId();
//        int accessLevel = currentUser.getAccessLevel();
//
//        StringBuilder structureString = new StringBuilder();
//        StringBuilder sb = new StringBuilder();
//        sb.append(whereCase);
//
//        int adminAccessLevel = 6;
//        if(accessLevel <= adminAccessLevel){
//            // 管理员用户可以查看所有数据
//        }else{
//            // 非管理员用户只能查看本组织，本线路下数据
//            List<JSONObject> structureList = getAll("STRUCTURE_CLASS_VIEW","DEPARTMENT_ID = " + structureId +" or WORKSHOP_ID = " + structureId + " or TEAM_ID = " + structureId);
//            for(JSONObject structure : structureList){
//                if(structureString.length() > 0){
//                    structureString.append(",").append(structure.getString("STRUCTURE_ID"));
//                }else{
//                    structureString.append(structure.getString("STRUCTURE_ID"));
//                }
//            }
//            sb.append(" and " + structureColumn + " in (" + structureString + ")");
//            sb.append(" and " + siteColumn + " = '"+siteId+"'");
//        }
//        return sb.toString();
//    }
}
