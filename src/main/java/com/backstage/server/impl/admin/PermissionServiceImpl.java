package com.backstage.server.impl.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.backstage.dao.admin.PermissionMapper;
import com.backstage.entity.admin.Permission;
import com.backstage.server.admin.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Service("PermissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapperEx;

    /**
     * 所有权限数据
     * @return
     */
    @Override
    public Page<Permission> getPermissionAllList(Page<Permission> page) {

        return page.setRecords(permissionMapperEx.getPermissionList(page));
    }


    /**
     * 权限台账
     * @return
     */
    @Override
    public List<JSONObject> getPermissionList(int currentPage,int pageSize) {

        // 封装数据
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();

        // 第一层权限
        List<Permission> permissionFirstList = permissionMapperEx.getPermissionListByFirst(1);
        for (Permission permission : permissionFirstList) {
            JSONObject firstObj = new JSONObject();
            firstObj.put("permissionId", permission.getPermissionId());
            firstObj.put("permissionName", permission.getPermissionName());
            firstObj.put("permissionPath", permission.getPermissionPath());
            firstObj.put("parentId", permission.getParentId());
            firstObj.put("parentName", permission.getParentName());
            firstObj.put("levelNo", permission.getLevelNo());
            firstObj.put("createPerson", permission.getCreatePerson());
            firstObj.put("createDate", permission.getCreateDate());
            firstObj.put("updatePerson", permission.getUpdatePerson());
            firstObj.put("updateDate", permission.getUpdateDate());
            jsonObjects.add(firstObj);
        }

        // 其他层权限
        List<Permission> permissionOtherList = permissionMapperEx.getPermissionListByFirst(0);
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
                    OtherObject.put("permissionPath", permission.getPermissionPath());
                    OtherObject.put("parentId", permission.getParentId());
                    OtherObject.put("parentName", permission.getParentName());
                    OtherObject.put("levelNo", permission.getLevelNo());
                    OtherObject.put("createPerson", permission.getCreatePerson());
                    OtherObject.put("createDate", permission.getCreateDate());
                    OtherObject.put("updatePerson", permission.getUpdatePerson());
                    OtherObject.put("updateDate", permission.getUpdateDate());
                    jsonObjectList.add(OtherObject);
                }
            }
            jsonObject.put("children", jsonObjectList);
        }
        PageHelper.startPage(currentPage, pageSize);
        return jsonObjects;
    }

    /**
     * 权限保存
     * @param permission
     * @return
     */
    @Override
    public boolean savePermission(Permission permission) {
        int succ = 0;
        if (permission.getPermissionId() != 0) {
            // ID不为空，更新操作
            succ = permissionMapperEx.updatePermission(permission);
        } else {
            // ID为空，创建操作
            succ = permissionMapperEx.createPermission(permission);
        }

        if (succ != 1) {
            return false;
        }
        return true;
    }


    /**
     * 权限删除
     * @param permissionId
     * @return
     */
    @Override
    public int deletePermission(int permissionId) {
        return permissionMapperEx.deletePermission(permissionId);
    }


    /**
     * 权限详情
     * @param permissionId
     * @return
     */
    @Override
    public Permission detailsPermission(int permissionId) {

        return permissionMapperEx.detailsPermission(permissionId);
    }
}
