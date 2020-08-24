import request from '@/utils/request'

export default {
  /* 登录接口 */
  ajaxLogin(data) {
    return request({
      url: '/api/v1/admin/login',
      method: 'post',
      data
    })
  },
  /* 登出接口 */
  loginOut() {
    return request({
      url: '/api/v1/admin/loginOut',
      method: 'get'
    })
  }
}
