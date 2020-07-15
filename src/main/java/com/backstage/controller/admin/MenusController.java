package com.backstage.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.Menus;
import com.backstage.server.admin.MenusService;
import com.backstage.util.Result;
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
     * @return
     */
    @PostMapping("/getMenusList")
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
     * @param data
     * @return
     */
    @PostMapping("/saveMenus")
    public String saveMenus(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        Menus menus = JSON.parseObject(data, Menus.class);

        boolean succ = menusService.saveMenus(menus);
        if (succ) {
            return "保存成功";
        } else {
            return "保存失败";
        }
    }

    /**
     * 菜单删除
     * @param menusId
     * @return
     */
    @GetMapping("/deleteMenus/{menusId}")
    public int deleteMenus(@PathVariable int menusId) {

        return menusService.deleteMenus(menusId);
    }


    /**
     * 菜单详情
     * @param menusId
     * @return
     */
    @GetMapping("/detailsMenus/{menusId}")
    public Menus detailsMenus(@PathVariable("menusId") int menusId) {
        Menus menus = menusService.detailsMenus(menusId);
        return menus;
    }


}
