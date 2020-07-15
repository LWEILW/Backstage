import axios from 'axios'
import store from '@/utils/store'

//前端的axios添加withCredentials属性
axios.defaults.withCredentials = true;
// Vue.prototype.$axios = axios;

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 15000 // 请求超时时间
})

service.interceptors.response.use(
  function (response) {
    //请求正常则返回
    return Promise.resolve(response)
  },
  function (error) {
    // 请求错误则向store commit这个状态变化
    const httpError = {
      hasError: true,
      status: error.response.status,
      statusText: error.response.statusText
    }
    store.commit('ON_HTTP_ERROR', httpError)
    return Promise.reject(error)
  }
)

export default service
