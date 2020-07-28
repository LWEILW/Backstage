package com.backstage.service.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.Permission;

import java.util.List;

/**
 * 权限管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
public interface PermissionService {

    /**
     * 所有权限数据
     *
     * @return
     */
    Page<Permission> getPermissionAllList(Page<Permission> page);

    /**
     * 权限台账
     *
     * @return
     */
    List<JSONObject> getPermissionList(int currentPage, int pageSize);

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
    int deletePermission(int permissionId);

    /**
     * 权限详情
     *
     * @param permissionId
     * @return
     */
    Permission detailsPermission(int permissionId);
}
