import request from '@/utils/request'

export default {
  // 角色台账
  getRoleList(data) {
    return request({
      url: '/api/v1/role/getRoleList',
      method: 'post',
      data
    })
  },
  // 保存角色
  saveRole(data) {
    return request({
      url: '/api/v1/role/saveRole',
      method: 'post',
      data
    })
  },
  // 角色详情
  detailsRole(id) {
    return request({
      url: `/api/v1/role/detailsRole/${id} `,
      method: 'get',
    })
  },
  // 删除角色
  deleteRole(id) {
    return request({
      url: `/api/v1/role/deleteRole/${id} `,
      method: 'get'
    })
  },

  // 人员维护台账
  getUserListByRoleId(id) {
    return request({
      url: `/api/v1/role/getUserListByRoleId/${id}`,
      method: 'get'
    })
  },
  // 人员维护可添加列表
  getUserOthersByRoleId(id) {
    return request({
      url: `/api/v1/role/getUserOthersByRoleId/${id}`,
      method: 'get'
    })
  },
  // 人员维护添加
  addUserToRole(data) {
    return request({
      url: `/api/v1/role/addUserToRole`,
      method: 'post',
      data
    })
  },
  // 权限维护所有数据
  getPermissionAllListByRoleId() {
    return request({
      url: `/api/v1/role/getPermissionAllListByRoleId`,
      method: 'get'
    })
  },
  getPermissionChangeList(id) {
    return request({
      url: `/api/v1/role/getPermissionChangeList/${id}`,
      method: 'get'
    })
  },
  // 权限维护已选数据
  getPermissionChangeListByRoleId(id) {
    return request({
      url: `/api/v1/role/getPermissionChangeListByRoleId/${id}`,
      method: 'get'
    })
  },
  // 人员维护台账
  addPermissionByRoleId(data) {
    return request({
      url: `/api/v1/role/addPermissionByRoleId`,
      method: 'post',
      data
    })
  }
}
