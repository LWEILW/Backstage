package com.backstage.dao.admin;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.backstage.entity.admin.Menus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Mapper
public interface MenusMapper {

    /**菜单台账
     *
     * @return
     */
    List<Menus> getMenusList(Pagination page);

    /**菜单创建
     *
     * @param menus
     * @return
     */
    int createMenus(@Param("menus") Menus menus);

    /**
     * 菜单更新
     * @param menus
     * @return
     */
    int updateMenus(@Param("menus") Menus menus);

    /**
     * 菜单删除
     * @param menusId
     * @return
     */
    int deleteMenus(@Param("menusId") int menusId);

    /**
     * 菜单详情
     * @param menusId
     * @return
     */
    Menus detailsMenus(@Param("menusId") int menusId);
}
