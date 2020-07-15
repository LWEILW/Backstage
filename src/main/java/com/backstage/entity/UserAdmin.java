package com.backstage.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

/**
 * 用户登录实体类
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Data
public class UserAdmin {

    @TableField(exist = false, el = "ID")
    private String id;


    @TableField(exist = false, el = "账号")
    private String account;


    @TableField(exist = false, el = "密码")
    private int password;


    @TableField(exist = false, el = "token")
    private String token;


    @TableField(exist = false, el = "用户名")
    private String username;


    @TableField(exist = false, el = "角色")
    private String role;


}
