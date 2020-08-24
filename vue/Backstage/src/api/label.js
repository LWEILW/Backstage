import request from '@/utils/request'

export default {
  // 文章标签台账
  labelList(data) {
    return request({
      url: '/api/v1/label/labelList',
      method: 'post',
      data
    })
  },
  // 文章标签详情
  labelDetails(id) {
    return request({
      url: `/api/v1/label/labelDetails/${id} `,
      method: 'get',
    })
  },
  // 文章标签保存
  labelSave(data) {
    return request({
      url: '/api/v1/label/labelSave',
      method: 'post',
      data
    })
  },
  // 文章标签删除
  deleteLabelAll(data) {
    return request({
      url: `/api/v1/label/deleteLabelAll`,
      method: 'post',
      data
    })
  }
}
