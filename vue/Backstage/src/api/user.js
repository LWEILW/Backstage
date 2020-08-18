import request from '@/utils/request'

export default {
  // 用户台账
  getUserList(data) {
    return request({
      url: '/api/v1/user/userList',
      method: 'post',
      data
    })
  },
  // 用户详情
  detailsUser(userId) {
    return request({
      url: `/api/v1/user/detailsUser/${userId} `,
      method: 'get'
    })
  },
  // 角色列表
  getRoleList() {
    return request({
      url: `/api/v1/role/roleList/`,
      method: 'get'
    })
  },
  // 用户保存
  saveUser(data) {
    return request({
      url: '/api/v1/user/createOrUpdateUser',
      method: 'post',
      data
    })
  },
  // 用户删除
  deleteUserById(userId) {
    return request({
      url: `/api/v1/user/deleteUser/${userId} `,
      method: 'get'
    })
  },
  // 用户批量删除
  deleteUserList(data) {
    return request({
      url: `/api/v1/user/deleteUser/list`,
      method: 'post',
      data
    })
  },
  // 重置密码
  resetPassword(userId) {
    return request({
      url: `/api/v1/user/resetPassword/${userId} `,
      method: 'get'
    })
  },
  // 更新用户状态
  changeUserStatus(data) {
    return request({
      url: `/api/v1/user/changeUserStatus`,
      method: 'post',
      data
    })
  }
}
