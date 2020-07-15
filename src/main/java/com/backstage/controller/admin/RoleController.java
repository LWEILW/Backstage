package com.backstage.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.Role;
import com.backstage.entity.admin.User;
import com.backstage.server.admin.PermissionService;
import com.backstage.server.admin.RoleService;
import com.backstage.util.Result;
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

    /**
     * 角色台账
     *
     * @return
     */
//    @RequiresPermissions("角色一览")
    @PostMapping("/getRoleList")
    public Result getRoleList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");
        Page<Role> page = new Page<>(currentPage, pageSize);
        page = roleService.getRoleList(page);
        return Result.success("角色台账")
                .data("roleList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }

    /**
     * 角色保存
     *
     * @param data
     * @return
     */
//    @RequiresPermissions("role:saveRole")
    @PostMapping("/saveRole")
    public String saveRole(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        Role role = JSON.parseObject(data, Role.class);
        JSONArray permissionList = (JSONArray) obj.get("permissionList");

        boolean succ = roleService.saveRole(role,permissionList);
        if (succ) {
            return "保存成功";
        } else {
            return "保存失败";
        }
    }

    /**
     * 角色删除
     *
     * @param roleId
     * @return
     */
    @GetMapping("/deleteRole/{roleId}")
//    @RequiresPermissions("role:deleteRole")
    public boolean deleteRole(@PathVariable int roleId) {

        return roleService.deleteRole(roleId);
    }

    /**
     * 角色详情
     *
     * @param roleId
     * @return
     */
//    @RequiresPermissions("role:detailsRole")
    @GetMapping("/detailsRole/{roleId}")
    public Result detailsRole(@PathVariable("roleId") int roleId) {
        Role role = roleService.detailsRole(roleId);
        //  权限维护所有数据
        List<JSONObject> permissionAllList = permissionService.getPermissionList(1, 100000);

        // 权限维护已选数据
        List<Integer> permissionList = roleService.getPermissionChangeList(roleId);

        return Result.success("角色详情")
                .data("role", role)
                .data("permissionAllList", permissionAllList)
                .data("permissionList", permissionList);
    }

    /**
     * 人员维护台账
     *
     * @param roleId
     * @return
     */
//    @RequiresPermissions("role:getUserListByRoleId")
    @GetMapping("/getUserListByRoleId/{roleId}")
    public Result getUserListByRoleId(@PathVariable("roleId") int roleId) {

        List<User> userList = roleService.getUserListByRoleId(roleId);
        return Result.success("人员维护台账").data("userList", userList);
    }

    /**
     * 人员维护待添加人员台账
     *
     * @param roleId
     * @return
     */
//    @RequiresPermissions("role:getUserOthersByRoleId")
    @GetMapping("/getUserOthersByRoleId/{roleId}")
    public Result getUserOthersByRoleId(@PathVariable("roleId") int roleId) {
        List<User> userList = roleService.getUserOthersByRoleId(roleId);
        return Result.success("人员维护待添加人员台账").data("userList", userList);
    }

    /**
     * 人员维护添加
     *
     * @param data
     * @return
     */
//    @RequiresPermissions("role:addUserToRole")
    @PostMapping("/addUserToRole")
    public Result addUserToRole(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int roleId = obj.getInteger("changesRoleId");
        List<JSONObject> jsonObjectList = JSON.parseArray(obj.getString("userList"), JSONObject.class);
        boolean succ = roleService.addUserByRoleId(jsonObjectList, roleId);
        if (succ) {
            return Result.success("添加成功");
        } else {
            return Result.fail("添加失败");
        }
    }

    /**
     * 权限维护所有数据
     *
     * @param roleId
     * @return
     */
//    @RequiresPermissions("role:getPermissionAllListByRoleId")
    @GetMapping("/getPermissionAllListByRoleId")
    public Result getPermissionAllListByRoleId() {
        List<JSONObject> permissionAllList = permissionService.getPermissionList(1, 100000);

        return Result.success("权限维护所有数据").data("permissionAllList", permissionAllList);
    }

    /**
     * 权限维护已选数据
     *
     * @param roleId
     * @return
     */
//    @RequiresPermissions("role:getPermissionChangeListByRoleId")
    @GetMapping("/getPermissionChangeListByRoleId/{roleId}")
    public Result getPermissionChangeListByRoleId(@PathVariable("roleId") int roleId) {
        List<JSONObject> permissionList = roleService.getPermissionListByRoleId(roleId);

        return Result.success("权限维护已选数据").data("permissionList", permissionList);
    }


    @GetMapping("/getPermissionChangeList/{roleId}")
    public Result getPermissionChangeList(@PathVariable("roleId") int roleId) {
        List<Integer> permissionList = roleService.getPermissionChangeList(roleId);

        return Result.success("权限维护已选数据").data("permissionList", permissionList);
    }

    /**
     * 权限维护添加
     *
     * @param data
     * @return
     */
//    @RequiresPermissions("role:addPermissionByRoleId")
    @PostMapping("/addPermissionByRoleId")
    public Result addPermissionByRoleId(@RequestBody String data) {
        JSONObject obj = JSON.parseObject(data);
        boolean succ = roleService.addPermissionByRoleId(obj);
        if (succ) {
            return Result.success("添加成功");
        } else {
            return Result.fail("添加失败");
        }

    }
}
