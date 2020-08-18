package com.backstage.dao.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.backstage.entity.admin.Permission;
import com.backstage.entity.admin.Role;
import com.backstage.entity.admin.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Mapper
public interface RoleMapper extends BaseMapper<JSONObject> {

    /**
     * 角色台账
     *
     * @return
     */
    List<Role> getRoleList(Pagination page, @Param("role") Role role);


    /**
     * 角色详情
     *
     * @param roleId
     * @return
     */
    Role detailsRole(@Param("roleId") int roleId);


    /**
     * 角色创建
     *
     * @param role
     * @return
     */
    int createRole(@Param("role") Role role);


    /**
     * 角色更新
     *
     * @param role
     * @return
     */
    int updateRole(@Param("role") Role role);


    /**
     * 角色删除
     *
     * @param roleId
     * @return
     */
    int deleteRole(@Param("roleId") int roleId);


    /**
     * 删除角色关联用户
     *
     * @param roleId
     * @return
     */
    int deleteRoleByUser(@Param("roleId") int roleId);


    /**
     * 删除角色所有权限
     *
     * @param roleId
     * @return
     */
    int deletePermissionByRoleId(@Param("roleId") int roleId);


    /**
     * 角色_权限已选数据
     *
     * @param roleId
     * @return
     */
    List<Integer> getPermissionChangeList(@Param("roleId") int roleId);


    /**
     * 角色_权限添加
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    int addPermissionByRoleId(@Param("roleId") int roleId, @Param("permissionId") int permissionId);


    /**
     * 根据角色ID获取权限信息
     *
     * @param roleId
     * @return
     */
    List<Permission> getPermissionListByRoleId(@Param("roleId") int roleId);


    /**
     * 获取角色创建ID
     *
     * @return
     */
    List<Role> getRoleId();



    /**
     * 权限添加
     *
     * @param obj
     * @return
     */
    boolean addPermissionByRoleId(JSONObject obj);
}
