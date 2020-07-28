package com.backstage.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.User;
import com.backstage.service.admin.UserService;
import com.backstage.util.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Api(value = "[APP-测试接口]-FrontExaminationReportController")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 用户台账
     *
     * @param data
     * @return
     */
    @PostMapping("/getUserList")
    public Result getUserList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");

        Page<User> page = new Page<>(currentPage, pageSize);
        page = userService.getUserList(page);
        return Result.success("用户台账")
                .data("userList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }

    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/detailsUser/{userId}")
    public Result detailsUser(@PathVariable("userId") int userId) {
        User user = userService.detailsUser(userId);
        JSONArray roleList = userService.getRolesByUserId(userId);
        return Result.success("")
                .data("user", user)
                .data("roleList", roleList);
    }


    /**
     * 用户保存
     *
     * @param data
     * @return
     */
    @PostMapping("/saveUser")
    public String saveUser(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        User user = JSON.parseObject(data, User.class);
        JSONArray roleList = (JSONArray) obj.get("roleList");

        boolean succ = userService.saveUser(user, roleList);
        if (succ) {
            return "保存成功";
        } else {
            return "保存失败";
        }
    }


    /**
     * 用户删除
     *
     * @param userId
     * @return
     */
    @GetMapping("/deleteUser/{userId}")
    public int deleteUser(@PathVariable int userId) {

        return userService.deleteUser(userId);
    }


//    /**
//     * 用户注册
//     *
//     * @param data
//     * @return
//     */
//    @PostMapping("/usersRegister")
//    public int usersRegister(@RequestBody String data) {
//        JSONObject obj = JSONObject.parseObject(data);
//        String account = obj.getString("account");
//        int password = obj.getInteger("password");
//
//        User users = new User();
//        users.setId("user" + Utils.getUUID());
//        users.setAccount(account);
//        users.setPassword(password);
////        user.setToken();
//        return userService.createUser(users);
//    }
//
//    /**
//     * 用户登录验证
//     *
//     * @param data
//     * @return
//     */
//    @PostMapping("/usersLogin")
//    public Result usersLogin(@RequestBody String data) {
//        JSONObject obj = JSONObject.parseObject(data);
//        String account = obj.getString("account");
//        int password = obj.getInteger("password");
//
//        Result result = new Result();
//        // 根据账户查询用户信息
//        User user = userService.usersLogin(account);
//        if (user != null) {
//            // 该账户存在于数据库
//            if (user.getPassword()==password) {
//                //"密码正确，登录成功"
//                result.setMsg("密码正确,登录成功");
//                result.setFlag(true);
//            } else {
//                //"密码错误，登录失败"
//                result.setMsg("密码错误，登录失败");
//                result.setFlag(false);
//            }
//        } else {
//            // 登录失败,该账户不存在
//            result.setMsg("登录失败,该账户不存在");
//            result.setFlag(false);
//        }
//        return result;
//    }
}
