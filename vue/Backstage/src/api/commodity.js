import request from '@/utils/request'

export default {
  // 商品台账
  commodityList(data) {
    return request({
      url: '/api/v1/commodity/commodityList',
      method: 'post',
      data
    })
  },
  // 商品详情
  commodityDetails(id) {
    return request({
      url: `/api/v1/commodity/commodityDetails/${id} `,
      method: 'get',
    })
  },
  // 商品保存
  commoditySave(data) {
    return request({
      url: '/api/v1/commodity/commoditySave',
      method: 'post',
      data
    })
  },
  // 商品删除
  commodityDelete(id) {
    return request({
      url: `/api/v1/commodity/commodityDeleteAll/${id} `,
      method: 'get',
    })
  }

}
