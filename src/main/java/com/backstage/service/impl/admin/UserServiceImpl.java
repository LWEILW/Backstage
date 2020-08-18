package com.backstage.service.impl.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.dao.admin.UserMapper;
import com.backstage.entity.admin.User;
import com.backstage.service.admin.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, JSONObject> implements UserService {


    /**
     * 用户台账
     *
     * @param page
     * @param user
     * @return
     */
    @Override
    public Page<User> userList(Page<User> page, User user) {

        return page.setRecords(baseMapper.userList(page, user));
    }


    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    @Override
    public User detailsUser(int userId) {

        return baseMapper.detailsUser(userId);
    }


    /**
     * 用户所选角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public JSONArray getRolesByUserId(int userId) {
        JSONArray jsonArray = new JSONArray();
        List<JSONObject> roleList = baseMapper.getRolesByUserId(userId);
        for (JSONObject obj : roleList) {
            jsonArray.add(obj.getInteger("roleId"));
        }
        return jsonArray;
    }


    /**
     * 用户保存
     *
     * @param user
     * @return
     */
    @Override
    public Boolean createOrUpdateUser(User user, JSONArray roleList) {
        // 获取登录用户
        Session session = SecurityUtils.getSubject().getSession();
        User sessionGetUser = (User) session.getAttribute("user");

        if (user.getUserId() != 0) {
            // 修改人
            user.setUpdatePerson(sessionGetUser.getUserName());
            // ID不为空，更新操作
            if (baseMapper.updateUser(user) != 1) {
                return false;
            }
            // 删除用户所选角色
            baseMapper.deleteRoleByUserId(user.getUserId());
        } else {
            // 密码加密,初始化密码为123
            RandomNumberGenerator saltGen = new SecureRandomNumberGenerator();
            String salt = saltGen.nextBytes().toString();
            Md5Hash md5Hash = new Md5Hash("123", salt, 2);
            user.setUserPassword(md5Hash.toString());
            user.setSalt(salt);
            // 创建人
            user.setCreatePerson(sessionGetUser.getUserName());
            // ID为空，创建操作
            if (baseMapper.createUser(user) != 1) {
                return false;
            }
        }

        // 添加所选用户角色
        for (Object id : roleList) {
            int count = baseMapper.addRoleByUserId((Integer) id, user.getUserId());
            if (count != 1) {
                return false;
            }
        }

        return true;
    }


    /**
     * 用户删除
     *
     * @param userId
     * @return
     */
    @Override
    public Boolean deleteUserById(int userId) {
        // 用户删除
        if (baseMapper.deleteUserById(userId) == 1) {
            // 删除用户所选角色
            baseMapper.deleteRoleByUserId(userId);
            return true;
        }
        return false;
    }


    /**
     * 用户批量删除
     *
     * @param userList
     * @return
     */
    @Override
    public Boolean deleteUserList(List<User> userList) {
        //定义装有需要删除的ID集合
        List<Integer> list = new ArrayList<Integer>();
        //遍历原有数据
        for (User user : userList) {
            //封装到新集合里
            list.add(user.getUserId());
        }

        int count = baseMapper.deleteUserList(list);
        if (count != 0) {
            // 删除用户下所有角色
            for (Integer userId : list) {
                baseMapper.deleteRoleByUserId(userId);
            }
            return true;
        }
        return false;
    }


    /**
     * 用户重置密码
     *
     * @param userId
     * @return
     */
    @Override
    public Boolean resetPassword(int userId) {
        User user = new User();
        user.setUserId(userId);
        //密码加密
        RandomNumberGenerator saltGen = new SecureRandomNumberGenerator();
        String salt = saltGen.nextBytes().toString();
        Md5Hash md5Hash = new Md5Hash("123", salt, 2);
        user.setUserPassword(md5Hash.toString());
        user.setSalt(salt);

        // ID不为空，更新操作
        int count = baseMapper.updateUser(user);
        if (count == 1) {
            return true;
        }
        return false;
    }

}
