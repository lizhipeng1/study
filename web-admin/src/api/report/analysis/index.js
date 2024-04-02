import request from '@/utils/request'

export function monthCourseOrderFeeReport(query) {
  return request({
    url: '/report/analysis/monthCourseOrderFeeReport',
    method: 'get',
    params: query
  })
}
