package com.backstage.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.backstage.entity.admin.Permission;
import com.backstage.service.admin.PermissionService;
import com.backstage.util.Result;
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
    @PostMapping("/getPermissionList")
    public Result getPermissionList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");
        List<JSONObject> permissionList = permissionService.getPermissionList(currentPage,pageSize);
        Pagination page = PageHelper.getPagination();
        PageHelper.remove();
        return Result.success("权限台账")
                .data("permissionList", permissionList)
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }

    /**
     * 权限保存
     *
     * @param data
     * @return
     */
    @PostMapping("/savePermission")
    public String savePermission(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        Permission permission = JSON.parseObject(data, Permission.class);

        boolean succ = permissionService.savePermission(permission);
        if (succ) {
            return "保存成功";
        } else {
            return "保存失败";
        }
    }

    /**
     * 权限删除
     *
     * @param permissionId
     * @return
     */
    @GetMapping("/deletePermission/{permissionId}")
    public int deletePermission(@PathVariable int permissionId) {

        return permissionService.deletePermission(permissionId);
    }


    /**
     * 权限详情
     *
     * @param permissionId
     * @return
     */
    @GetMapping("/detailsPermission/{permissionId}")
    public Permission detailsPermission(@PathVariable("permissionId") int permissionId) {
        Permission permission = permissionService.detailsPermission(permissionId);
        return permission;
    }


}
