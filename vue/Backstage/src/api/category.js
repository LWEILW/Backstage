import request from '@/utils/request'

export default {
  // 文章分类台账
  categoryList(data) {
    return request({
      url: '/api/v1/category/categoryList',
      method: 'post',
      data
    })
  },
  // 文章分类详情
  categoryDetails(id) {
    return request({
      url: `/api/v1/category/categoryDetails/${id} `,
      method: 'get',
    })
  },
  // 文章分类保存
  categorySave(data) {
    return request({
      url: '/api/v1/category/categorySave',
      method: 'post',
      data
    })
  },
  // 文章分类删除
  categoryDelete(id) {
    return request({
      url: `/api/v1/category/categoryDelete/${id} `,
      method: 'get',
    })
  }
}
