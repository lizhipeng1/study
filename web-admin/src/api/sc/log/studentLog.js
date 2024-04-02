import request from '@/utils/request'

// 查询列表
export function listStudentCourseLog(query) {
  return request({
    url: '/sc/student/log/list/searchList',
    method: 'get',
    params: query
  })
}
