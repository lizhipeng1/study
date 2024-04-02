import request from '@/utils/request'

// 查询列表
export function listSchool(query) {
  return request({
    url: '/sc/school/list/searchList',
    method: 'get',
    params: query
  })
}

// select
export function listSelect(query) {
  return request({
    url: '/sc/school/list/select',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getSchool(schoolId) {
  return request({
    url: '/sc/school/info/detailById/' + schoolId,
    method: 'get'
  })
}

// 新增
export function addSchool(data) {
  return request({
    url: '/sc/school/add/addScSchool',
    method: 'post',
    data: data
  })
}

// 修改
export function updateSchool(data) {
  return request({
    url: '/sc/school/update/updateScSchool',
    method: 'put',
    data: data
  })
}

// 删除
export function delSchool(schoolId) {
  return request({
    url: '/sc/school/delete/deleteById/' + schoolId,
    method: 'delete'
  })
}

// 导出
export function exportSchool(query) {
  return request({
    url: '/sc/school/export',
    method: 'get',
    params: query
  })
}
