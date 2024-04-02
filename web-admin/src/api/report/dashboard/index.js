import request from '@/utils/request'

export function dashboardData(query) {
  return request({
    url: '/report/dashboard/data',
    method: 'get',
    params: query
  })
}
