import request from '@/utils/request'

// 查询列表
export function listTenant(query) {
  return request({
    url: '/system/tenant/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询列表
export function treeSelect() {
  return request({
    url: '/system/tenant/list/treeSelect',
    method: 'get'
  })
}

// 查询详细
export function getTenant(dictTenantId) {
  return request({
    url: '/system/tenant/info/detailById/' + dictTenantId,
    method: 'get'
  })
}

// 新增
export function addTenant(data) {
  return request({
    url: '/system/tenant/add/addTenant',
    method: 'post',
    data: data
  })
}

// 修改
export function updateTenant(data) {
  return request({
    url: '/system/tenant/update/updateTenant',
    method: 'put',
    data: data
  })
}

// 删除
export function delTenant(dictTenantId) {
  return request({
    url: '/system/tenant/delete/deleteById/' + dictTenantId,
    method: 'delete'
  })
}

// 导出
export function exportTenant(query) {
  return request({
    url: '/system/tenant/export',
    method: 'get',
    params: query
  })
}

// 用户所属租户列表，限定当前登录人的租户
export function userTenantSelectLimitSelf(userId) {
  return request({
    url: '/system/tenant/list/userTenantSelectLimitSelf/' + userId,
    method: 'get'
  })
}

// 用户所属租户列表
export function userTenantSelect(userId) {
  return request({
    url: '/system/tenant/list/userTenantSelect/' + userId,
    method: 'get'
  })
}

// 当前登录用户 所属租户列表
export function loginUserTenantSelect() {
  return request({
    url: '/system/tenant/list/loginUserTenantSelect',
    method: 'get'
  })
}

// 当前租户信息
export function nowTenantInfo() {
  return request({
    url: '/system/tenant/info/nowTenantInfo',
    method: 'get'
  })
}
