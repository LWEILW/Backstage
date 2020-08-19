package com.backstage.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backstage.entity.admin.User;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.backstage.entity.admin.Permission;
import com.backstage.service.admin.PermissionService;
import com.backstage.util.Result;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    /**
     * 权限台账
     *
     * @return
     */
    @PostMapping("/permissionList")
    @RequiresPermissions("permissionList")
    public Result getPermissionList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");

        List<JSONObject> permissionList = permissionService.permissionList(currentPage, pageSize);
        Pagination page = PageHelper.getPagination();
        PageHelper.remove();
        return Result.success("权限台账")
                .data("permissionList", permissionList)
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 权限详情
     *
     * @param permissionId
     * @return
     */
    @GetMapping("/detailsPermission/{permissionId}")
    @RequiresPermissions("detailsPermission")
    public Result detailsPermission(@PathVariable("permissionId") int permissionId) {

        Permission permission = permissionService.detailsPermission(permissionId);
        return Result.success("权限详情")
                .data("permission", permission);
    }


    /**
     * 权限保存
     *
     * @param data
     * @return
     */
    @PostMapping("/createOrUpdatePermission")
    @RequiresPermissions("createOrUpdatePermission")
    public Result createOrUpdatePermission(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        Permission permission = JSON.parseObject(data, Permission.class);
        try {
            boolean result = permissionService.createOrUpdatePermission(permission);
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
     * 权限删除
     *
     * @param permissionId
     * @return
     */
    @GetMapping("/deletePermission/{permissionId}")
    @RequiresPermissions("deletePermission")
    public Result deletePermission(@PathVariable int permissionId) {
        try {
            Boolean result = permissionService.deletePermission(permissionId);
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
     * 权限父类列表
     *
     * @return
     */
    @GetMapping("/getPermissionParent")
    public Result getPermissionParent() {

        List<JSONObject> parentList = permissionService.getPermissionParent();
        return Result.success("权限父类列表")
                .data("parentList", parentList);
    }
}
