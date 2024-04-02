import request from '@/utils/request'

// 上课记录
export function classBeginLogList(query) {
  return request({
    url: '/sc/account/log/list/classBeginLogList',
    method: 'get',
    params: query
  })
}

// 充值记录
export function payLogList(query) {
  return request({
    url: '/sc/account/log/list/payLogList',
    method: 'get',
    params: query
  })
}
