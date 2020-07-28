package com.backstage.service.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.Role;
import com.backstage.entity.admin.User;

import java.util.List;

/**
 * 角色管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
public interface RoleService {

    /**
     * 角色台账
     *
     * @return
     */
    Page<Role> getRoleList(Page<Role> page);

    /**
     * 角色保存
     *
     * @param role
     * @return
     */
    boolean saveRole(Role role, JSONArray permissionList);


    /**
     * 角色删除
     *
     * @param roleId
     * @return
     */
    boolean deleteRole(int roleId);


    /**
     * 角色详情
     *
     * @param roleId
     * @return
     */
    Role detailsRole(int roleId);


    /**
     * 人员维护台账
     *
     * @param roleId
     * @return
     */
    List<User> getUserListByRoleId(int roleId);

    /**
     * 人员维护待添加人员台账
     *
     * @param roleId
     * @return
     */
    List<User> getUserOthersByRoleId(int roleId);

    /**
     * 人员维护添加
     *
     * @param jsonObjectList
     * @param roleId
     * @return
     */
    boolean addUserByRoleId(List<JSONObject> jsonObjectList, int roleId);

    /**
     * 权限维护已选数据
     *
     * @param roleId
     * @return
     */
    List<JSONObject> getPermissionListByRoleId(int roleId);


    List<Integer> getPermissionChangeList(int roleId);

    /**
     * 权限维护添加
     *
     * @param obj
     * @return
     */
    boolean addPermissionByRoleId(JSONObject obj);
}
