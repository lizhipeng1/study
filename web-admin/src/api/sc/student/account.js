import request from '@/utils/request'

// 查询列表
export function studentAccountBalance(studentId) {
  return request({
    url: '/sc/studentAccount/info/studentAccountBalance/' + studentId,
    method: 'get'
  })
}
