import request from '@/utils/request'

// 查询列表
export function listType(query) {
  return request({
    url: '/sc/course/type/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询列表
export function select(query) {
  return request({
    url: '/sc/course/type/list/select',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getType(courseTypeId) {
  return request({
    url: '/sc/course/type/info/detailById/' + courseTypeId,
    method: 'get'
  })
}

// 新增
export function addType(data) {
  return request({
    url: '/sc/course/type/add/addScCourseType',
    method: 'post',
    data: data
  })
}

// 修改
export function updateType(data) {
  return request({
    url: '/sc/course/type/update/updateScCourseType',
    method: 'put',
    data: data
  })
}

// 删除
export function delType(courseTypeId) {
  return request({
    url: '/sc/course/type/delete/deleteById/' + courseTypeId,
    method: 'delete'
  })
}

// 导出
export function exportType(query) {
  return request({
    url: '/sc/course/type/export',
    method: 'get',
    params: query
  })
}
