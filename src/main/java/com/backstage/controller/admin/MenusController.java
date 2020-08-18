package com.backstage.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.Menus;
import com.backstage.service.admin.MenusService;
import com.backstage.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@RestController
@RequestMapping("/menus")
public class MenusController {
    @Autowired
    private MenusService menusService;

    /**
     * 菜单台账
     *
     * @return
     */
    @PostMapping("/getMenusList")
    @RequiresPermissions("getMenusList")
    public Result getMenusList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");
        Page<Menus> page = new Page<>(currentPage, pageSize);
        page = menusService.getMenusList(page);
        return Result.success("菜单台账")
                .data("menusList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }

    /**
     * 菜单保存
     *
     * @param data
     * @return
     */
    @PostMapping("/saveMenus")
    @RequiresPermissions("saveMenus")
    public Result saveMenus(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        Menus menus = JSON.parseObject(data, Menus.class);

        boolean result = menusService.saveMenus(menus);
        if (result) {
            return Result.success("保存成功");
        } else {
            return Result.fail("保存失败");
        }
    }

    /**
     * 菜单删除
     *
     * @param menusId
     * @return
     */
    @GetMapping("/deleteMenus/{menusId}")
    @RequiresPermissions("deleteMenus")
    public Result deleteMenus(@PathVariable int menusId) {

        Boolean result = menusService.deleteMenus(menusId);
        return Result.success("菜单删除");
    }


    /**
     * 菜单详情
     *
     * @param menusId
     * @return
     */
    @GetMapping("/detailsMenus/{menusId}")
    @RequiresPermissions("detailsMenus")
    public Result detailsMenus(@PathVariable("menusId") int menusId) {

        Menus menus = menusService.detailsMenus(menusId);
        return Result.success("菜单详情")
                .data("menus",menus);
    }


}
