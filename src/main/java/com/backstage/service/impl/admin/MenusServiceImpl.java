package com.backstage.service.impl.admin;


import com.alibaba.fastjson.JSONObject;
import com.backstage.dao.admin.RoleMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.dao.admin.MenusMapper;
import com.backstage.entity.admin.Menus;
import com.backstage.service.admin.MenusService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Service("MenusService")
public class MenusServiceImpl extends ServiceImpl<MenusMapper, JSONObject> implements MenusService {



    /**
     * 权限台账
     *
     * @return
     */
    @Override
    public Page<Menus> getMenusList(Page<Menus> page) {

        return page.setRecords(baseMapper.getMenusList(page));

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
            succ = baseMapper.updateMenus(menus);
        } else {
            // ID为空，创建操作
            succ = baseMapper.createMenus(menus);
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
    public Boolean deleteMenus(int menusId) {

        int count = baseMapper.deleteMenus(menusId);
        if (count == 1) {
            return true;
        }
        return false;
    }


    /**
     * 权限详情
     *
     * @param menusId
     * @return
     */
    @Override
    public Menus detailsMenus(int menusId) {

        return baseMapper.detailsMenus(menusId);
    }
}
