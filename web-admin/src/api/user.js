import request from '@/utils/request'
import requestSso from '@/utils/requestSso'

export function getInfo() {
  return request({
    url: '/system/user/info',
    method: 'get'
  })
}

export function logout() {
  return requestSso({
    url: '/system/user/logout',
    method: 'post'
  })
}

export function removeLoginUserToken() {
  return request({
    url: '/system/user/removeLoginUserToken',
    method: 'post'
  })
}

export function getMenu() {
  return request({
    url: '/system/user/menu',
    method: 'get'
  })
}
