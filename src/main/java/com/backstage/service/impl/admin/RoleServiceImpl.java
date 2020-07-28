package com.backstage.service.impl.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.dao.admin.RoleMapper;
import com.backstage.dao.admin.UserMapper;
import com.backstage.entity.admin.Permission;
import com.backstage.entity.admin.Role;
import com.backstage.entity.admin.User;
import com.backstage.service.admin.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Service("RoleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapperEx;

    @Autowired
    private UserMapper userMapperEx;

    /**
     * 角色台账
     *
     * @return
     */
    @Override
    public Page<Role> getRoleList(Page<Role> page) {

        return page.setRecords(roleMapperEx.getRoleList(page));

    }

    /**
     * 角色保存
     *
     * @param role
     * @return
     */
    @Override
    public boolean saveRole(Role role, JSONArray permissionList) {
        int succ = 0;
        int roleId;
        if (role.getRoleId() != 0) {
            // ID不为空，更新操作
            succ = roleMapperEx.updateRole(role);
            roleId = role.getRoleId();
        } else {
            // ID为空，创建操作
            succ = roleMapperEx.createRole(role);
            roleId = roleMapperEx.getRoleId().get(0).getRoleId();
        }
        if (succ != 1) {
            return false;
        }

        // 删除该角色所有权限
        roleMapperEx.deletePermissionByRoleId(roleId);
        for (Object id : permissionList) {
            int count = roleMapperEx.addPermissionByRoleId(roleId, (Integer) id);
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
    public boolean deleteRole(int roleId) {

        // 删除角色
        int count = roleMapperEx.deleteRole(roleId);
        if (count != 1) {
            return false;
        }
        // 删除关联的用户
        roleMapperEx.deleteRoleByUser(roleId);

        return true;
    }


    /**
     * 角色详情
     *
     * @param roleId
     * @return
     */
    @Override
    public Role detailsRole(int roleId) {
        return roleMapperEx.detailsRole(roleId);
    }

    /**
     * 人员维护台账
     *
     * @param roleId
     * @return
     */
    @Override
    public List<User> getUserListByRoleId(int roleId) {
        return roleMapperEx.getUserListByRoleId(roleId);
    }

    /**
     * 人员维护待添加人员台账
     *
     * @param roleId
     * @return
     */
    @Override
    public List<User> getUserOthersByRoleId(int roleId) {
        List<User> userList = roleMapperEx.getUserListByRoleId(roleId);
        List<Integer> userIdList = new ArrayList<Integer>();
        for (User user : userList) {
            userIdList.add(user.getUserId());
        }


        return roleMapperEx.getUserOthersByRoleId(userIdList);
    }

    /**
     * 人员维护添加
     *
     * @param jsonObjectList
     * @param roleId
     * @return
     */
    @Override
    public boolean addUserByRoleId(List<JSONObject> jsonObjectList, int roleId) {
        for (JSONObject obj : jsonObjectList) {
            int userId = obj.getInteger("userId");
//            int count = roleMapperEx.addUserByRoleId(roleId, userId);
//            if (count != 1) {
//                return false;
//            }
        }
        return true;
    }


    /**
     * 权限维护已选数据
     *
     * @param roleId
     * @return
     */
    @Override
    public List<JSONObject> getPermissionListByRoleId(int roleId) {
        // 封装数据
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();

        // 第一层权限
        List<Permission> permissionFirstList = roleMapperEx.getPermissionListByRoleId(roleId, 1);
        for (Permission permission : permissionFirstList) {
            JSONObject firstObj = new JSONObject();
            firstObj.put("permissionId", permission.getPermissionId());
            firstObj.put("permissionName", permission.getPermissionName());
            firstObj.put("LevelNo", permission.getLevelNo());
            firstObj.put("ParentId", permission.getParentId());
            jsonObjects.add(firstObj);

        }

        // 其他层权限
        List<Permission> permissionOtherList = roleMapperEx.getPermissionListByRoleId(roleId, 0);
        for (JSONObject jsonObject : jsonObjects) {
            // 循环获取第一层ID，匹配封装第一层数据下的值
            int PermissionId = jsonObject.getInteger("permissionId");

            // children层
            List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
            for (Permission permission : permissionOtherList) {
                // 获取父类ID,匹配第一层ID
                int parentId = permission.getParentId();
                if (PermissionId == parentId) {
                    JSONObject OtherObject = new JSONObject();
                    OtherObject.put("permissionId", permission.getPermissionId());
                    OtherObject.put("permissionName", permission.getPermissionName());
                    jsonObjectList.add(OtherObject);

                }
            }
            jsonObject.put("children", jsonObjectList);
        }


        return jsonObjects;
    }

    @Override
    public List<Integer> getPermissionChangeList(int roleId) {
        return roleMapperEx.getPermissionChangeList(roleId);
    }


    /**
     * 权限维护添加
     *
     * @param obj
     * @return
     */
    @Override
    public boolean addPermissionByRoleId(JSONObject obj) {
        int roleId = obj.getInteger("roleId");
        JSONArray permissionList = (JSONArray) obj.get("permissionList");

        // 删除该角色所有权限
        roleMapperEx.deletePermissionByRoleId(roleId);

        for (Object id : permissionList) {
            int count = roleMapperEx.addPermissionByRoleId(roleId, (Integer) id);
            if (count != 1) {
                return false;
            }
        }
        return true;
    }
}
