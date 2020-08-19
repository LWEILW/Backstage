package com.backstage.service.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.Role;
import com.backstage.entity.admin.User;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * 角色管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
public interface RoleService extends IService<JSONObject> {

    /**
     * 角色台账
     *
     * @param page
     * @param role
     * @return
     */
    Page<Role> roleList(Page<Role> page, Role role);


    /**
     * 角色详情
     *
     * @param roleId
     * @return
     */
    Role detailsRole(int roleId);


    /**
     * 角色所选权限列表
     *
     * @param roleId
     * @return
     */
    List<Integer> getPermissionChangeList(int roleId);


    /**
     * 角色保存
     *
     * @param role
     * @param permissionList
     * @return
     */
    Boolean createOrUpdateRole(Role role, JSONArray permissionList);


    /**
     * 角色删除
     *
     * @param roleId
     * @return
     */
    Boolean deleteRole(int roleId);


}
