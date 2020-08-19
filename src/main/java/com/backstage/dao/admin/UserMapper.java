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
 * 用户管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Mapper
public interface UserMapper extends BaseMapper<JSONObject> {

    /**
     * 用户台账
     *
     * @param page
     * @param user
     * @return
     */
    List<User> userList(Pagination page, @Param("user") User user);


    /**
     * 用户详情
     *
     * @param Id
     * @return
     */
    User detailsUser(@Param("userId") int Id);


    /**
     * 用户所选角色列表
     *
     * @param userId
     * @return
     */
    List<JSONObject> getRolesByUserId(@Param("userId") int userId);


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
     * 添加用户所选角色
     *
     * @param roleId
     * @param userId
     * @return
     */
    int addRoleByUserId(@Param("roleId") Integer roleId, @Param("userId") int userId);


    /**
     * 用户删除
     *
     * @param id
     * @return
     */
    int deleteUserById(@Param("userId") int id);


    /**
     * 用户批量删除
     *
     * @param list
     * @return
     */
    int deleteUserList(@Param("list") List<Integer> list);


    /**
     * 删除用户所选角色
     *
     * @param userId
     * @return
     */
    int deleteRoleByUserId(@Param("userId") int userId);


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
}
