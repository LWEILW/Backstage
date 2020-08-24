import request from '@/utils/request'

export default {
  // 定时器台账
  quartzList(data) {
    return request({
      url: '/api/v1/quartz/quartzList',
      method: 'post',
      data
    })
  },
  // 定时器详情
  detailsQuartz(id) {
    return request({
      url: `/api/v1/quartz/detailsQuartz/${id} `,
      method: 'get',
    })
  },
  // 定时器保存
  createOrUpdateQuartz(data) {
    return request({
      url: '/api/v1/quartz/createOrUpdateQuartz',
      method: 'post',
      data
    })
  },
  // 定时器删除
  deleteQuartzList(data) {
    return request({
      url: `/api/v1/quartz/deleteQuartzList`,
      method: 'post',
      data
    })
  },
  // 暂停所有
  suspendAll() {
    return request({
      url: `/api/v1/quartz/suspendAll`,
      method: 'get'
    })
  },
  // 恢复所有
  recoveryAll() {
    return request({
      url: `/api/v1/quartz/recoveryAll`,
      method: 'get'
    })
  },
  // 暂停job
  suspendJob(id) {
    return request({
      url: `/api/v1/quartz/suspendJob/${id} `,
      method: 'get'
    })
  },
  // 恢复job
  recoveryJob(id) {
    return request({
      url: `/api/v1/quartz/recoveryJob/${id} `,
      method: 'get'
    })
  }
}
