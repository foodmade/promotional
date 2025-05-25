import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API || '/',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json;charset=utf-8',
  }
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 从本地存储获取token
    const token = localStorage.getItem('token')
    const tokenHeader = localStorage.getItem('tokenHeader') || 'Authorization'
    const tokenPrefix = localStorage.getItem('tokenPrefix') || 'Bearer '
    
    // 如果token存在，添加到请求头
    if (token) {
      config.headers[tokenHeader] = `${tokenPrefix}${token}`
    }
    
    return config
  },
  error => {
    console.error('请求拦截器错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    
    // 请求成功但业务状态码不是200的情况
    if (res.code !== 200) {
      ElMessage({
        message: res.message || '操作失败',
        type: 'error',
        duration: 5 * 1000
      })
      
      // 处理特定错误码
      if (res.code === 401) {
        // token过期或未登录
        ElMessage({
          message: '登录已过期，请重新登录',
          type: 'error',
          duration: 5 * 1000
        })
        
        // 清除本地token信息
        localStorage.removeItem('token')
        localStorage.removeItem('tokenHeader')
        localStorage.removeItem('tokenPrefix')
        
        // 当前路径不是登录页时才跳转到登录页
        if (!window.location.pathname.includes('/login')) {
          setTimeout(() => {
            window.location.href = '/login'
          }, 1500)
        }
      }
      
      return Promise.reject(new Error(res.message || '操作失败'))
    }
    
    return res
  },
  error => {
    console.error('响应拦截器错误:', error)
    
    // 网络错误或服务器错误
    ElMessage({
      message: error.message || '网络异常，请稍后重试',
      type: 'error',
      duration: 5 * 1000
    })
    
    return Promise.reject(error)
  }
)

export default service 