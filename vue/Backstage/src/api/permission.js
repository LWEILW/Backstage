import request from '@/utils/request'

export default {
  // 权限台账
  permissionList(data) {
    return request({
      url: '/api/v1/permission/permissionList',
      method: 'post',
      data
    })
  },
  // 权限详情
  detailsPermission(id) {
    return request({
      url: `/api/v1/permission/detailsPermission/${id} `,
      method: 'get',
    })
  },
  // 权限保存
  createOrUpdatePermission(data) {
    return request({
      url: '/api/v1/permission/createOrUpdatePermission',
      method: 'post',
      data
    })
  },
  // 权限删除
  deletePermission(id) {
    return request({
      url: `/api/v1/permission/deletePermission/${id} `,
      method: 'get',
    })
  },
  // 权限父类列表
  getPermissionParent() {
    return request({
      url: `/api/v1/permission/getPermissionParent`,
      method: 'get',
    })
  }
}
