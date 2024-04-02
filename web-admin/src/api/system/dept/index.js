import request from '@/utils/request'

// 查询部门列表
export function listDept(query) {
  return request({
    url: '/system/dept/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询部门详细
export function getDept(deptId) {
  return request({
    url: '/system/dept/info/detailById/' + deptId,
    method: 'get'
  })
}

// 查询部门下拉树结构
export function treeSelect() {
  return request({
    url: '/system/dept/list/treeSelect',
    method: 'get'
  })
}

// 新增部门
export function addDept(data) {
  return request({
    url: '/system/dept/add/addDept',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateDept(data) {
  return request({
    url: '/system/dept/update/updateDept',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delDept(deptId) {
  return request({
    url: '/system/dept/delete/deleteById/' + deptId,
    method: 'delete'
  })
}

// 校区列表
export function campusList() {
  return request({
    url: '/system/dept/list/campusList',
    method: 'get'
  })
}

// 当前用户可选校区列表
export function campusListLimitByUser() {
  return request({
    url: '/system/dept/list/campusListLimitByUser',
    method: 'get'
  })
}

// 可选校区类型 全部校区/部分校区
export function campusSelect() {
  return request({
    url: '/system/dept/list/campusSelect',
    method: 'get'
  })
}

// 当前用户 可选校区类型 全部校区/部分校区
export function campusSelectLimitByUser() {
  return request({
    url: '/system/dept/list/campusSelectLimitByUser',
    method: 'get'
  })
}
