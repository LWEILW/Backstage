package com.backstage.service.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.User;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * 用户管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
public interface UserService extends IService<JSONObject> {

    /**
     * 用户台账
     *
     * @param page
     * @param user
     * @return
     */
    Page<User> userList(Page<User> page, User user);


    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    User detailsUser(int userId);


    /**
     * 用户所选角色列表
     *
     * @param userId
     * @return
     */
    JSONArray getRolesByUserId(int userId);


    /**
     * 用户保存
     *
     * @param user
     * @param roleList
     * @return
     */
    Boolean createOrUpdateUser(User user, JSONArray roleList);


    /**
     * 用户删除
     *
     * @param userId
     * @return
     */
    Boolean deleteUserById(int userId);


    /**
     * 用户批量删除
     *
     * @param userList
     * @return
     */
    Boolean deleteUserList(List<User> userList);


    /**
     * 用户重置密码
     *
     * @param userId
     * @return
     */
    Boolean resetPassword(int userId);


}
