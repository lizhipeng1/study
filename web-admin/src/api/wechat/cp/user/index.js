import request from '@/utils/request'

// select
export function select() {
  return request({
    url: '/wechat/cp/user/list/userSelect',
    method: 'get'
  })
}
