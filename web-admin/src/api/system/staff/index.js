import request from '@/utils/request'

// 查询列表
export function listStaff(query) {
  return request({
    url: '/sys/staff/list/searchList',
    method: 'get',
    params: query
  })
}

// select
export function select() {
  return request({
    url: '/sys/staff/list/teacherSelect',
    method: 'get'
  })
}

// 查询详细
export function getStaff(staffId) {
  return request({
    url: '/sys/staff/info/detailById/' + staffId,
    method: 'get'
  })
}

// 新增
export function addStaff(data) {
  return request({
    url: '/sys/staff/add/addSysStaff',
    method: 'post',
    data: data
  })
}

// 修改
export function updateStaff(data) {
  return request({
    url: '/sys/staff/update/updateSysStaff',
    method: 'put',
    data: data
  })
}

// 删除
export function delStaff(teacherId) {
  return request({
    url: '/sys/staff/delete/deleteById/' + teacherId,
    method: 'delete'
  })
}

// 导出
export function exportStaff(query) {
  return request({
    url: '/sys/staff/export',
    method: 'get',
    params: query
  })
}
