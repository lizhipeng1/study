import request from '@/utils/request'

// 某课程 学生到期日
export function stuCourseHourAccountList(query) {
  return request({
    url: '/sc/accountHour/list/stuCourseHourAccountList',
    method: 'get',
    params: query
  })
}

// 课时充值
export function hourPay(data) {
  return request({
    url: '/sc/accountHour/pay/hourPay',
    method: 'post',
    data: data
  })
}

// 获取各学生费用，剩余课时
export function claStudentChargeList(query) {
  return request({
    url: '/sc/accountHour/list/claStudentChargeList',
    method: 'get',
    params: query
  })
}

// 批量充值
export function batchHourPay(data) {
  return request({
    url: '/sc/accountHour/pay/batchHourPay',
    method: 'post',
    data: data
  })
}
