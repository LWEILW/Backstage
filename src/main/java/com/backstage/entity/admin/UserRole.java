package com.backstage.entity.admin;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 用户角色关联实体类
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@Data
@TableName(value = "user_role")
public class UserRole extends User {

    @TableField(value = "roleId", el = "角色ID")
    private int roleId;
}
