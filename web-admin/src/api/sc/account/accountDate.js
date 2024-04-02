import request from '@/utils/request'

// 某课程 学生到期日
export function stuCourseDateAccountList(query) {
  return request({
    url: '/sc/accountDate/list/stuCourseDateAccountList',
    method: 'get',
    params: query
  })
}

// 首次续费
export function firstPay(data) {
  return request({
    url: '/sc/accountDate/pay/firstPay',
    method: 'post',
    data: data
  })
}

// 续费
export function renew(data) {
  return request({
    url: '/sc/accountDate/pay/renew',
    method: 'post',
    data: data
  })
}

// 获取各学生每周期费用
export function claStudentChargeList(query) {
  return request({
    url: '/sc/accountDate/list/claStudentChargeList',
    method: 'get',
    params: query
  })
}

// 批量续费
export function batchRenew(data) {
  return request({
    url: '/sc/accountDate/pay/batchRenew',
    method: 'post',
    data: data
  })
}
