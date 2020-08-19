package com.backstage.service.impl.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.dao.admin.PermissionMapper;
import com.backstage.entity.admin.User;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.dao.admin.RoleMapper;
import com.backstage.entity.admin.Role;
import com.backstage.service.admin.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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
    public Page<Role> roleList(Page<Role> page, Role role) {

        return page.setRecords(baseMapper.roleList(page, role));

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
     * 角色所选权限列表
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
    public Boolean createOrUpdateRole(Role role, JSONArray permissionList) {
        // 获取登录用户
        Session session = SecurityUtils.getSubject().getSession();
        User sessionGetUser = (User) session.getAttribute("user");

        if (role.getRoleId() != 0) {
            // 修改人
            role.setUpdatePerson(sessionGetUser.getUserName());
            // ID不为空，更新操作
            if (baseMapper.updateRole(role) != 1) {
                return false;
            }

            // 删除该角色所有权限
            baseMapper.deletePermissionByRoleId(role.getRoleId());
            // 添加所选角色权限
            for (Object id : permissionList) {
                int count = baseMapper.addPermissionByRoleId(role.getRoleId(), (Integer) id);
                if (count != 1) {
                    return false;
                }
            }

        } else {
            // 创建人
            role.setCreatePerson(sessionGetUser.getUserName());
            // ID为空，创建操作
            if (baseMapper.createRole(role) != 1) {
                return false;
            }
            // 新创建ID
            int roleId = baseMapper.getRoleId().get(0).getRoleId();
            // 添加所选角色权限
            for (Object id : permissionList) {
                int count = baseMapper.addPermissionByRoleId(roleId, (Integer) id);
                if (count != 1) {
                    return false;
                }
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
        // 删除该角色所有权限
        baseMapper.deletePermissionByRoleId(roleId);

        return true;
    }


}
