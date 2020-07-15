package com.backstage.dao.admin;

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
public interface RoleMapper {

    /**
     * 角色台账
     *
     * @return
     */
    List<Role> getRoleList(Pagination page);

    /**
     * 角色创建
     *
     * @param role
     * @return
     */
    int createRole(@Param("role") Role role);

    /**
     * 获取角色创建ID
     * @return
     */
    List<Role> getRoleId();

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
     * 角色详情
     *
     * @param roleId
     * @return
     */
    Role detailsRole(@Param("roleId") int roleId);

    /**
     * 人员维护台账
     *
     * @param roleId
     * @return
     */
    List<User> getUserListByRoleId(@Param("roleId") int roleId);

    /**
     * 人员维护待添加人员台账
     *
     * @param userIdList
     * @return
     */
    List<User> getUserOthersByRoleId(@Param("List") List<Integer> userIdList);




    /**
     * 权限维护已选数据
     *
     * @param roleId
     * @param type
     * @return
     */
    List<Permission> getPermissionListByRoleId(@Param("roleId") int roleId, @Param("type") int type);

    /**
     * 权限维护添加
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    int addPermissionByRoleId(@Param("roleId") int roleId, @Param("permissionId") int permissionId);


    /**
     * @param roleId
     * @return
     */
    List<Integer> getPermissionChangeList(@Param("roleId") int roleId);

    /**
     * 删除该角色所有权限
     *
     * @param roleId
     * @return
     */
    int deletePermissionByRoleId(@Param("roleId") int roleId);

}
