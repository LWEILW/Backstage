package com.backstage.entity.admin;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 用户实体类（继承角色字段）
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@Data
public class UserPoi extends User {
    @TableField(value = "roleId", el = "角色ID")
    private int roleId;

    @TableField(exist = false)
    private int ErrorCode;

    @TableField(exist = false)
    private boolean success;
}
