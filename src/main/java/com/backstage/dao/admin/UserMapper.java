package com.backstage.dao.admin;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.backstage.entity.admin.Permission;
import com.backstage.entity.admin.Role;
import com.backstage.entity.admin.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Mapper
public interface UserMapper {

    /**
     * 用户台账
     *
     * @return
     */
    List<User> getUserList(Pagination page);

    /**
     * 用户创建
     *
     * @param user
     * @return
     */
    int createUser(@Param("user") User user);

    /**
     * 获取用户创建ID
     *
     * @return
     */
    List<User> getUserId();

    /**
     * 用户更新
     *
     * @param user
     * @return
     */
    int updateUser(@Param("user") User user);

    /**
     * 用户删除
     *
     * @param id
     * @return
     */
    int deleteUser(@Param("userId") int id);

    /**
     * 用户详情
     *
     * @param Id
     * @return
     */
    User detailsUser(@Param("userId") int Id);


    User usersLogin(@Param("account") String account);

    /**
     * shiro验证
     * @param userName
     * @return
     */
    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    User selectAllByName(@Param("userName") String userName);

    /**
     * 根据用户ID获取角色信息
     *
     * @param userId
     * @return
     */
    List<Role> getRoleListByUserId(@Param("userId") int userId);

    /**
     * 根据角色ID获取权限信息
     *
     * @param roleId
     * @return
     */
    List<Permission> getPermissionListByRoleId(@Param("roleId") int roleId);


    /**
     * 删除该用户下所有角色
     *
     * @param userId
     * @return
     */
    int deleteRoleByUserId(@Param("userId") int userId);

    /**
     * 人员维护添加
     *
     * @param roleId
     * @param userId
     * @return
     */
    int addRoleByUserId(@Param("roleId") Integer roleId, @Param("userId") int userId);


    /**
     * 获取用户角色列表
     *
     * @param userId
     * @return
     */
    List<JSONObject> getRolesByUserId(@Param("userId") int userId);
}
