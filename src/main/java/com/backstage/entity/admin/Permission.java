package com.backstage.entity.admin;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 权限实体类（继承角色字段）
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@Data
@TableName(value = "permission")
public class Permission extends Role {


    @TableField(value = "permissionId", el = "权限ID")
    private int permissionId;


    @TableField(value = "permissionName", el = "权限名称")
    private String permissionName;


    @TableField(value = "permissionPath", el = "权限路径")
    private String permissionPath;


    @TableField(value = "parentId", el = "父类ID")
    private int parentId;

    @TableField(value = "parentName", el = "父类名称")
    private String parentName;


    @TableField(value = "levelNo", el = "权限等级")
    private int levelNo;


    @TableField(value = "createPerson", el = "创建者（用户ID）")
    private String createPerson;

    @TableField(value = "createDate", el = "创建时间")
    private String createDate;


    @TableField(value = "updatePerson", el = "更新者（用户ID）")
    private String updatePerson;


    @TableField(value = "updateDate", el = "更新时间")
    private String updateDate;


}
