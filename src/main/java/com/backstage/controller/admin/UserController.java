package com.backstage.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.service.ShareService;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.User;
import com.backstage.service.admin.UserService;
import com.backstage.util.Result;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @Autowired
    private ShareService shareService;


    /**
     * 用户台账
     *
     * @param data
     * @return
     */
    @PostMapping("/userList")
    @RequiresPermissions("userList")
    public Result userList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");
        User user = JSONObject.parseObject(data, User.class);

        Page<User> page = new Page<>(currentPage, pageSize);
        page = userService.userList(page, user);
        return Result.success("用户台账")
                .data("userList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 用户角色列表
     *
     * @return
     */
    @GetMapping("/roleList")
    public Result roleList() {
        try {
            List<JSONObject> roleList = shareService.getAll("role");
            return Result.success("角色列表")
                    .data("roleList", roleList);
        } catch (Exception ex) {
            return Result.fail("角色列表获取失败");
        }
    }


    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/detailsUser/{userId}")
    @RequiresPermissions("detailsUser")
    public Result detailsUser(@PathVariable("userId") int userId) {
        // 用户详情
        User user = userService.detailsUser(userId);
        // 用户所选角色列表
        JSONArray roleList = userService.getRolesByUserId(userId);

        return Result.success("用户详情")
                .data("user", user)
                .data("roleList", roleList);
    }


    /**
     * 用户保存
     *
     * @param data
     * @return
     */
    @PostMapping("/createOrUpdateUser")
    @RequiresPermissions("createOrUpdateUser")
    public Result createOrUpdateUser(@RequestBody String data) {
        try {
            JSONObject obj = JSONObject.parseObject(data);
            User user = JSON.parseObject(data, User.class);
            JSONArray roleList = (JSONArray) obj.get("roleList");

            Boolean result = userService.createOrUpdateUser(user, roleList);
            if (result) {
                return Result.success("保存成功");
            } else {
                return Result.fail("保存失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.fail("保存失败");
        }
    }


    /**
     * 用户删除
     *
     * @param userId
     * @return
     */
    @GetMapping("/deleteUser/{userId}")
    @RequiresPermissions("deleteUser")
    public Result deleteUserById(@PathVariable int userId) {

        Boolean result = userService.deleteUserById(userId);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }


    /**
     * 用户批量删除
     *
     * @param data
     * @return
     */
    @PostMapping("/deleteUser/list")
    @RequiresPermissions("deleteUser")
    public Result deleteUserList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        List<User> userList = JSON.parseArray(obj.getString("userList"), User.class);


        Boolean result = userService.deleteUserList(userList);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }


    /**
     * 用户改变状态
     *
     * @param data
     * @return
     */
    @PostMapping("/changeUserStatus")
    public Result changeUserStatus(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        Integer userId = obj.getInteger("userId");
        Integer userStatus = obj.getInteger("userStatus");

        int count = shareService.updateOne("user", "userStatus", userStatus.toString(), "userId=" + userId);
        if (count == 1) {
            return Result.success("改变成功");
        } else {
            return Result.fail("改变成功");
        }
    }


    /**
     * 用户重置密码
     *
     * @param userId
     * @return
     */
    @GetMapping("/resetPassword/{userId}")
    public Result resetPassword(@PathVariable int userId) {

        Boolean result = userService.resetPassword(userId);
        if (result) {
            return Result.success("重置成功");
        } else {
            return Result.fail("重置失败");
        }
    }

}
