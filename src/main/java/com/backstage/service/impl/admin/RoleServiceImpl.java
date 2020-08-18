package com.backstage.service.impl.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.dao.admin.PermissionMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.dao.admin.RoleMapper;
import com.backstage.entity.admin.Role;
import com.backstage.service.admin.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 角色管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Service("RoleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, JSONObject> implements RoleService {

    @Autowired
    private PermissionMapper permissionMapper;


    /**
     * 角色台账
     *
     * @return
     */
    @Override
    public Page<Role> getRoleList(Page<Role> page, Role role) {

        return page.setRecords(baseMapper.getRoleList(page, role));

    }


    /**
     * 角色详情
     *
     * @param roleId
     * @return
     */
    @Override
    public Role detailsRole(int roleId) {
        return baseMapper.detailsRole(roleId);
    }


    /**
     * 角色_权限已选数据
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> getPermissionChangeList(int roleId) {
        return baseMapper.getPermissionChangeList(roleId);
    }


    /**
     * 角色保存
     *
     * @param role
     * @return
     */
    @Override
    public Boolean saveRole(Role role, JSONArray permissionList) {
        int succ = 0;
        int roleId;
        if (role.getRoleId() != 0) {
            // ID不为空，更新操作
            succ = baseMapper.updateRole(role);
            roleId = role.getRoleId();
        } else {
            // ID为空，创建操作
            succ = baseMapper.createRole(role);
            roleId = baseMapper.getRoleId().get(0).getRoleId();
        }
        if (succ != 1) {
            return false;
        }

        // 删除该角色所有权限
        baseMapper.deletePermissionByRoleId(roleId);
        for (Object id : permissionList) {
            int count = baseMapper.addPermissionByRoleId(roleId, (Integer) id);
            if (count != 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * 角色_权限添加
     *
     * @param obj
     * @return
     */
    @Override
    public boolean addPermissionByRoleId(JSONObject obj) {
        int roleId = obj.getInteger("roleId");
        JSONArray permissionList = (JSONArray) obj.get("permissionList");

        // 角色所有权限删除
        baseMapper.deletePermissionByRoleId(roleId);

        for (Object id : permissionList) {
            int count = baseMapper.addPermissionByRoleId(roleId, (Integer) id);
            if (count != 1) {
                return false;
            }
        }
        return true;
    }


    /**
     * 角色删除
     *
     * @param roleId
     * @return
     */
    @Override
    public Boolean deleteRole(int roleId) {

        // 删除角色
        int count = baseMapper.deleteRole(roleId);
        if (count != 1) {
            return false;
        }
        // 删除关联的用户
        baseMapper.deleteRoleByUser(roleId);

        return true;
    }


}
