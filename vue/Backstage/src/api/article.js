import request from '@/utils/request'

export default {
  // 文章台账
  articleList(data) {
    return request({
      url: '/api/v1/article/articleList',
      method: 'post',
      data
    })
  },
  // 文章详情
  articleDetails(id) {
    return request({
      url: `/api/v1/article/articleDetails/${id} `,
      method: 'get',
    })
  },
  // 保存文章
  articleSave(data) {
    return request({
      url: '/api/v1/article/articleSave',
      method: 'post',
      data
    })
  },
  // 删除文章
  articleDelete(id) {
    return request({
      url: `/api/v1/article/articleDelete/${id} `,
      method: 'get',
    })
  }
}
