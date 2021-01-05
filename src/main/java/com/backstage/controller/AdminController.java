package com.backstage.controller;

import com.alibaba.fastjson.JSONObject;
import com.backstage.dao.admin.PermissionMapper;
import com.backstage.dao.admin.RoleMapper;
import com.backstage.dao.admin.UserMapper;
import com.backstage.entity.admin.Permission;
import com.backstage.entity.admin.Role;
import com.backstage.entity.admin.User;
import com.backstage.service.admin.UserService;
import com.backstage.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);



    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 登录
     *
     * @param data
     * @return
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        String userName = obj.getString("userName");
        String password = obj.getString("password");

        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        try {
            // 执行认证登陆
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return Result.fail("未知账户");
        } catch (IncorrectCredentialsException ice) {
            return Result.fail("密码不正确");
        } catch (LockedAccountException lae) {
            return Result.fail("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return Result.fail("用户名或密码错误次数过多");
        } catch (DisabledAccountException dis) {
            return Result.fail("帐号已经禁止登录！");
        } catch (AuthenticationException ae) {
            return Result.fail("用户名或密码不正确");
        }
        if (subject.isAuthenticated()) {
            User user = (User) subject.getSession().getAttribute("user");
            return Result.success("登录成功")
                    .data("user", user);
        } else {
            token.clear();
            return Result.fail("登录失败");
        }
    }


    /**
     * 登出
     *
     * @return
     */
    @GetMapping(value = "/loginOut")
    public Result loginOut() {
        //注销
        SecurityUtils.getSubject().logout();
        return Result.success("成功注销！");
    }


    /**
     * 身份认证失败访问
     *
     * @return
     */
    @GetMapping(value = "/notLogin")
    public Result notLogin() {

        return Result.success("您尚未登陆！")
                .data("code", "notLogin");
    }

    /**
     * 未授权界面访问
     *
     * @return
     */
    @GetMapping(value = "/notRole")
    public Result notRole() {

        return Result.success("您没有权限！")
                .data("code", "notRole");
    }


    /**
     * 错误页面展示
     *
     * @return
     */
    @GetMapping("/403")
    public String error() {
        return "error ok!";
    }


    /**
     * 获取用户信息
     *
     * @return com.ste.util.Result
     * @author LW
     */
    @GetMapping("/info")
    public Result info() {
        System.out.println("===================INFO===================");
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.out.println("查询不到用户信息");
            return Result.fail("查询不到用户信息");
        }
        System.out.println("===================INFO===================");
        // 用户的角色集合
        List<String> roleStrlist = new ArrayList<String>();
        // 用户的权限集合
        List<String> perminsStrlist = new ArrayList<String>();
        // 获取用户角色
        List<Role> roleList = userMapper.getRoleListByUserId(user.getUserId());
        for (Role role : roleList) {
            roleStrlist.add(role.getRoleName());
            //获取用户权限
            List<Permission> permissionList = roleMapper.getPermissionListByRoleId(role.getRoleId());
            for (Permission uPermission : permissionList) {
                perminsStrlist.add(uPermission.getPermissionName());
            }
        }
        user.setRoleList(roleStrlist);
        user.setPermissionList(perminsStrlist);


        // 设置用户权限
        return Result.success("获取成功")
                .data("personId", user.getUserId())
                .data("displayName", user.getUserName())
                .data("personGroups", roleList)
                .data("permissions", perminsStrlist);
    }


}
