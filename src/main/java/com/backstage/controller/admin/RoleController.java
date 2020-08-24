package com.backstage.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.service.ShareService;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.Role;
import com.backstage.entity.admin.User;
import com.backstage.service.admin.PermissionService;
import com.backstage.service.admin.RoleService;
import com.backstage.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ShareService shareService;


    /**
     * 角色台账
     *
     * @return
     */
    @PostMapping("/roleList")
    @RequiresPermissions("roleList")
    public Result getRoleList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");
        Role role = JSONObject.parseObject(data, Role.class);

        Page<Role> page = new Page<>(currentPage, pageSize);
        page = roleService.roleList(page, role);
        return Result.success("角色台账")
                .data("roleList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 角色详情
     *
     * @param roleId
     * @return
     */

    @GetMapping("/detailsRole/{roleId}")
    @RequiresPermissions("detailsRole")
    public Result detailsRole(@PathVariable("roleId") int roleId) {

        try {
            // 角色详情
            Role role = roleService.detailsRole(roleId);
            // 角色所选权限列表
            List<Integer> permissionList = roleService.getPermissionChangeList(roleId);

            return Result.success("角色详情")
                    .data("role", role)
                    .data("permissionList", permissionList);
        } catch (Exception ex) {
            return Result.fail("角色详情获取失败");
        }
    }


    /**
     * 角色保存
     *
     * @param data
     * @return
     */
    @PostMapping("/createOrUpdateRole")
    @RequiresPermissions("createOrUpdateRole")
    public Result createOrUpdateRole(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        Role role = JSON.parseObject(data, Role.class);
        JSONArray permissionList = (JSONArray) obj.get("permissionList");
        try {
            Boolean result = roleService.createOrUpdateRole(role, permissionList);
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
     * 角色删除
     *
     * @param roleId
     * @return
     */
    @GetMapping("/deleteRole/{roleId}")
    @RequiresPermissions("deleteRole")
    public Result deleteRole(@PathVariable int roleId) {
        try {
            Boolean result = roleService.deleteRole(roleId);
            if (result) {
                return Result.success("删除成功");
            } else {
                return Result.fail("删除失败");
            }
        } catch (Exception ex) {
            return Result.fail("删除失败");
        }

    }


    /**
     * 角色列表
     *
     * @return
     */
    @GetMapping("/getRoleList")
    public Result getRoleList() {
        try {
            List<JSONObject> roleList = shareService.getAll("role");
            return Result.success("角色列表")
                    .data("roleList", roleList);
        } catch (Exception ex) {
            return Result.fail("角色列表获取失败");
        }
    }
}
