import request from '@/utils/request'

// 查询列表
export function listCla(query) {
  return request({
    url: '/sc/course/cla/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getCla(claId) {
  return request({
    url: '/sc/course/cla/info/detailById/' + claId,
    method: 'get'
  })
}

// 查询详细
export function allDetailInfoById(claId) {
  return request({
    url: '/sc/course/cla/info/allDetailInfoById/' + claId,
    method: 'get'
  })
}

// 新增
export function addCla(data) {
  return request({
    url: '/sc/course/cla/add/addScCourseCla',
    method: 'post',
    data: data
  })
}

// 修改
export function updateCla(data) {
  return request({
    url: '/sc/course/cla/update/updateScCourseCla',
    method: 'put',
    data: data
  })
}

// 删除
export function delCla(claId) {
  return request({
    url: '/sc/course/cla/delete/deleteById/' + claId,
    method: 'delete'
  })
}

// 导出
export function exportCla(query) {
  return request({
    url: '/sc/course/cla/export',
    method: 'get',
    params: query
  })
}

// 班级学生信息
export function claStudentList(query) {
  return request({
    url: '/sc/course/cla/list/claStudentList',
    method: 'get',
    params: query
  })
}

// 学生班级信息
export function studentClaList(query) {
  return request({
    url: '/sc/course/cla/list/studentClaList',
    method: 'get',
    params: query
  })
}

// 班级账户类型
export function claAccountType(claId) {
  return request({
    url: '/sc/course/cla/info/claAccountType/' + claId,
    method: 'get'
  })
}
