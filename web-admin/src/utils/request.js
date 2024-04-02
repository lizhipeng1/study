import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken, setToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['Authorization'] = 'Bearer ' + getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    // 下载
    if (res instanceof Blob) {
      return response
    }
    if (res.respCode !== '0000') {
      Message({
        message: res.respMsg || 'Error',
        type: 'error',
        duration: 5 * 1000
      })

      if (res.respCode === '0511') {
        // 强制踢出,请重新登录
        MessageBox.confirm(
          res.respMsg,
          '系统提示',
          {
            confirmButtonText: '重新登录',
            type: 'warning',
            showCancelButton: false
          }
        ).then(() => {
          store.dispatch('user/logout').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          }).catch(() => {

          })
        })
      } else if (res.respCode === '0513') {
        // 登录信息失效,请重新登录
        MessageBox.confirm(
          res.respMsg,
          '系统提示',
          {
            confirmButtonText: '重新登录',
            type: 'warning',
            showCancelButton: false
          }
        ).then(() => {
          store.dispatch('user/logout').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          }).catch(() => {

          })
        })
      } else {
        return Promise.reject(new Error(res.respMsg || 'Error'))
      }
    } else {
      return res
    }
  },
  error => {
    var config = error.config
    const res = error.response.data
    if (res && res.respCode === '0511') {
      MessageBox.confirm(
        res.respMsg,
        '系统提示',
        {
          confirmButtonText: '重新登录',
          type: 'warning',
          showCancelButton: false
        }
      ).then(() => {
        store.dispatch('user/logout').then(() => {
          location.reload() // 为了重新实例化vue-router对象 避免bug
        }).catch(() => {

        })
      })
    } else if (res.respCode === '0513') {
      // 登录信息失效,请重新登录
      MessageBox.confirm(
        res.respMsg,
        '系统提示',
        {
          confirmButtonText: '重新登录',
          type: 'warning',
          showCancelButton: false
        }
      ).then(() => {
        store.dispatch('user/logout').then(() => {
          location.reload() // 为了重新实例化vue-router对象 避免bug
        }).catch(() => {
        })
      })
    } else if (res && res.respCode === '0512') {
      const accessToken = error.response.headers.access_token
      setToken(accessToken)
      config.headers['Authorization'] = 'Bearer ' + getToken()
      config.baseURL = ''
      if (!config) {
        Message({
          message: error.message,
          type: 'error',
          duration: 5 * 1000
        })
        return Promise.reject(error)
      }

      var retry = new Promise(function(resolve) {
        resolve()
      })
      return retry.then(function() {
        return service(config)
      })
    } else {
      console.log('err' + error) // for debug
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(error)
    }
  }
)

export default service
