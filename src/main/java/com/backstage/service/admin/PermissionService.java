package com.backstage.service.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.Permission;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * 权限管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
public interface PermissionService extends IService<JSONObject> {


    /**
     * 权限台账
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<JSONObject> getPermissionList(int currentPage, int pageSize);


    /**
     * 权限详情
     *
     * @param permissionId
     * @return
     */
    Permission detailsPermission(int permissionId);


    /**
     * 权限保存
     *
     * @param permission
     * @return
     */
    boolean savePermission(Permission permission);


    /**
     * 权限删除
     *
     * @param permissionId
     * @return
     */
    Boolean deletePermission(int permissionId);




    /**
     * 权限已选数据
     *
     * @param roleId
     * @return
     */
    List<JSONObject> getPermissionListByRoleId(int roleId);



}
