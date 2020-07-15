import request from '@/utils/request'

export default {
  // 用户台账
  getUserList(data) {
    return request({
      url: '/api/v1/user/getUserList',
      method: 'post',
      data
    })
  },
 // 用户保存
  saveUser(data) {
    return request({
      url: '/api/v1/user/saveUser',
      method: 'post',
      data
    })
  },
  // 用户删除
  deleteUser(userId) {
    return request({
      url: `/api/v1/user/deleteUser/${userId} `,
      method: 'get',
    })
  },
  // 用户详情
  detailsUser(userId) {
    return request({
      url: `/api/v1/user/detailsUser/${userId} `,
      method: 'get',
    })
  }

}
