package com.backstage.dao.admin;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.backstage.entity.admin.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Mapper
public interface PermissionMapper {

    /**
     * 权限台账
     * @return
     */
    List<Permission> getPermissionList(Pagination page);

    /**
     * 获取初始层权限结构
     * @param type
     * @return
     */
    List<Permission> getPermissionListByFirst(@Param("type") int type);

    /**
     * 权限创建
     * @param permission
     * @return
     */
    int createPermission(@Param("permission") Permission permission);

    /**
     * 权限更新
     * @param permission
     * @return
     */
    int updatePermission(@Param("permission") Permission permission);

    /**
     * 权限删除
     * @param permissionId
     * @return
     */
    int deletePermission(@Param("permissionId") int permissionId);

    /**
     * 权限详情
     * @param permissionId
     * @return
     */
    Permission detailsPermission(@Param("permissionId") int permissionId);
}
