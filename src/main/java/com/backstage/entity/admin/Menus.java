package com.backstage.entity.admin;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

/**
 * 菜单实体类
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@Data
public class Menus {

    @TableField(value = "menusId", el = "菜单ID")
    private int menusId;
    // 菜单名称
    // 排序
    // 请求地址
    // 类型
    // 可见
    // 权限标识

}
