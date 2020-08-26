import request from '@/utils/request'

export default {
  // 定时器台账
  everydayList(data) {
    return request({
      url: '/api/v1/everyday/everydayList',
      method: 'post',
      data
    })
  },
  // 定时器详情
  detailsEveryday(id) {
    return request({
      url: `/api/v1/everyday/detailsEveryday/${id} `,
      method: 'get',
    })
  },
  // 定时器保存
  createOrUpdateEveryday(data) {
    return request({
      url: '/api/v1/everyday/createOrUpdateEveryday',
      method: 'post',
      data
    })
  },
  // 定时器删除
  deleteEverydayList(data) {
    return request({
      url: `/api/v1/everyday/deleteEverydayList`,
      method: 'post',
      data
    })
  }
}
