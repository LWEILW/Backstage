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
    @PostMapping("/getRoleList")
    @RequiresPermissions("getRoleList")
    public Result getRoleList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");
        Role role = JSONObject.parseObject(data, Role.class);

        Page<Role> page = new Page<>(currentPage, pageSize);
        page = roleService.getRoleList(page, role);
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
            // 权限维护所有数据
            List<JSONObject> permissionAllList = permissionService.getPermissionList(1, 100000);
            // 权限维护已选数据
            List<Integer> permissionList = roleService.getPermissionChangeList(roleId);

            return Result.success("角色详情")
                    .data("role", role)
                    .data("permissionAllList", permissionAllList)
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
    @PostMapping("/saveRole")
    @RequiresPermissions("saveRole")
    public Result saveRole(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        Role role = JSON.parseObject(data, Role.class);
        JSONArray permissionList = (JSONArray) obj.get("permissionList");
        try {
            Boolean result = roleService.saveRole(role, permissionList);
            if (result) {
                return Result.success("保存成功");
            } else {
                return Result.fail("保存失败");
            }
        } catch (Exception ex) {
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
     * 角色_权限添加
     *
     * @param data
     * @return
     */
    @PostMapping("/addPermissionByRoleId")
    @RequiresPermissions("saveRole")
    public Result addPermissionByRoleId(@RequestBody String data) {
        JSONObject obj = JSON.parseObject(data);

        boolean result = roleService.addPermissionByRoleId(obj);
        if (result) {
            return Result.success("添加成功");
        } else {
            return Result.fail("添加失败");
        }

    }


//    @GetMapping("/getPermissionChangeList/{roleId}")
//    public Result getPermissionChangeList(@PathVariable("roleId") int roleId) {
//        List<Integer> permissionList = permissionService.getPermissionChangeList(roleId);
//
//        return Result.success("权限维护已选数据")
//                .data("permissionList", permissionList);
//    }

}
