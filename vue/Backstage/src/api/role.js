import request from '@/utils/request'

export default {
  // 角色台账
  roleList(data) {
    return request({
      url: '/api/v1/role/roleList',
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
  // 角色详情
  detailsRole(id) {
    return request({
      url: `/api/v1/role/detailsRole/${id} `,
      method: 'get',
    })
  },
  // 角色保存
  createOrUpdateRole(data) {
    return request({
      url: '/api/v1/role/createOrUpdateRole',
      method: 'post',
      data
    })
  },
  // 角色删除
  deleteRole(id) {
    return request({
      url: `/api/v1/role/deleteRole/${id} `,
      method: 'get'
    })
  }
}
