package com.backstage.entity.admin;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 权限角色关联实体类
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@Data
@TableName(value = "permission_role")
public class PermissionRole extends Permission {

}
