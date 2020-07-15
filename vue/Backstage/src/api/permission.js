import request from '@/utils/request'

export default {
  // 权限台账
  getPermissionList(data) {
    return request({
      url: '/api/v1/permission/getPermissionList',
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
  // 保存权限
  savePermission(data) {
    return request({
      url: '/api/v1/permission/savePermission',
      method: 'post',
      data
    })
  },
  // 删除权限
  deletePermission(id) {
    return request({
      url: `/api/v1/permission/deletePermission/${id} `,
      method: 'get',
    })
  }
}
