package com.backstage.service.impl.admin;


import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.dao.admin.MenusMapper;
import com.backstage.entity.admin.Menus;
import com.backstage.service.admin.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Service("MenusService")
public class MenusServiceImpl implements MenusService {

    @Autowired
    private MenusMapper menusMapperEx;

    /**
     * 权限台账
     *
     * @return
     */
    @Override
    public Page<Menus> getMenusList(Page<Menus> page) {

        return page.setRecords(menusMapperEx.getMenusList(page));

    }

    /**
     * 权限保存
     *
     * @param menus
     * @return
     */
    @Override
    public boolean saveMenus(Menus menus) {
        int succ = 0;
        if (menus.getMenusId() != 0) {
            // ID不为空，更新操作
            succ = menusMapperEx.updateMenus(menus);
        } else {
            // ID为空，创建操作
            succ = menusMapperEx.createMenus(menus);
        }

        if (succ != 1) {
            return false;
        }
        return true;
    }


    /**
     * 权限删除
     *
     * @param menusId
     * @return
     */
    @Override
    public int deleteMenus(int menusId) {
        return menusMapperEx.deleteMenus(menusId);
    }


    /**
     * 权限详情
     *
     * @param menusId
     * @return
     */
    @Override
    public Menus detailsMenus(int menusId) {

        return menusMapperEx.detailsMenus(menusId);
    }
}
