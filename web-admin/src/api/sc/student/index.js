import request from '@/utils/request'

// 查询列表
export function listStudent(query) {
  return request({
    url: '/sc/student/list/searchList',
    method: 'get',
    params: query
  })
}

// select
export function listSelect(query) {
  return request({
    url: '/sc/student/list/select',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getStudent(studentId) {
  return request({
    url: '/sc/student/info/detailById/' + studentId,
    method: 'get'
  })
}

// 新增
export function addStudent(data) {
  return request({
    url: '/sc/student/add/addScStudent',
    method: 'post',
    data: data
  })
}

// 修改
export function updateStudent(data) {
  return request({
    url: '/sc/student/update/updateScStudent',
    method: 'put',
    data: data
  })
}

// 删除
export function delStudent(studentId) {
  return request({
    url: '/sc/student/delete/deleteById/' + studentId,
    method: 'delete'
  })
}

// 导出
export function exportStudent(query) {
  return request({
    url: '/sc/student/export',
    method: 'get',
    params: query
  })
}
