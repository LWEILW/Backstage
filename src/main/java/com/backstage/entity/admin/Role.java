package com.backstage.entity.admin;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 角色实体类
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@Data
@TableName(value = "role")
public class Role {

    @TableField(value = "roleId", el = "角色ID")
    private int roleId;


    @TableField(value = "roleName", el = "角色名称")
    private String roleName;


    @TableField(value = "roleDescribe", el = "角色描述")
    private String roleDescribe;


    @TableField(value = "createPerson", el = "创建者（用户ID）")
    private String createPerson;


    @TableField(value = "createDate", el = "创建时间")
    private String createDate;


    @TableField(value = "updatePerson", el = "更新者（用户ID）")
    private String updatePerson;


    @TableField(value = "updateDate", el = "更新时间")
    private String updateDate;

}
