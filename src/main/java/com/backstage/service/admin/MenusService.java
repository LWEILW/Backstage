package com.backstage.service.admin;

import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.Menus;

/**
 * 菜单管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
public interface MenusService {

    /**
     * 菜单台账
     * @return
     */
    Page<Menus> getMenusList(Page<Menus> page);

    /**
     * 菜单保存
     * @param menus
     * @return
     */
    boolean saveMenus(Menus menus);


    /**
     * 菜单删除
     * @param menusId
     * @return
     */
    int deleteMenus(int menusId);

    /**
     * 菜单详情
     * @param menusId
     * @return
     */
    Menus detailsMenus(int menusId);
}
