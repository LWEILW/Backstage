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
  suspendAll(data) {
    return request({
      url: `/api/v1/quartz/suspendAll`,
      method: 'post',
      data
    })
  },
  // 恢复所有
  recoveryAll(data) {
    return request({
      url: `/api/v1/quartz/recoveryAll`,
      method: 'post',
      data
    })
  },
  // 暂停job
  suspendJob(data) {
    return request({
      url: `/api/v1/quartz/suspendJob`,
      method: 'post',
      data
    })
  },
  // 恢复job
  recoveryJob(data) {
    return request({
      url: `/api/v1/quartz/recoveryJob`,
      method: 'post',
      data
    })
  }
}
