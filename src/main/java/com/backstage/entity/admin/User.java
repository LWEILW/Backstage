package com.backstage.entity.admin;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.List;

/**
 * 用户实体类（继承角色字段）
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@Data
@TableName(value = "user")
public class User extends Role {


    @TableField(value = "userId", el = "用户ID")
    private int userId;


    @TableField(value = "userAccount", el = "用户工号")
    private String userAccount;


    @TableField(value = "userName", el = "用户姓名")
    private String userName;


    @TableField(value = "userSex", el = "性别")
    private String userSex;


    @TableField(value = "userPhone", el = "手机话")
    private String userPhone;

    @TableField(value = "telephone", el = "电话")
    private String telephone;


    @TableField(value = "userEmail", el = "邮箱")
    private String userEmail;


    @TableField(value = "userPassword", el = "密码")
    private String userPassword;


    @TableField(value = "salt", el = "mb5加密")
    private String salt;


    @TableField(value = "userStatus", el = "用户状态")
    private int userStatus;


    @TableField(value = "token", el = "登录验证")
    private String token;


    @TableField(value = "createPerson", el = "创建者（用户ID）")
    private String createPerson;


    @TableField(value = "createDate", el = "创建时间")
    private String createDate;


    @TableField(value = "updatePerson", el = "更新者（用户ID）")
    private String updatePerson;


    @TableField(value = "updateDate", el = "更新时间")
    private String updateDate;


    @TableField(exist = false, el = "角色列表")
    private List<String> roleList;


    @TableField(exist = false, el = "权限列表")
    private List<String> permissionList;


}
