import request from '@/utils/request'

// 查询字典类型列表
export function listRole(query) {
  return request({
    url: '/system/role/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询下拉树结构
export function treeSelect() {
  return request({
    url: '/system/role/list/treeSelect',
    method: 'get'
  })
}

// 查询下拉树结构 限定登录人的角色
export function treeSelectLimitUserHasRole() {
  return request({
    url: '/system/role/list/treeSelectLimitUserHasRole',
    method: 'get'
  })
}

// 查询字典类型详细
export function getRole(roleId) {
  return request({
    url: '/system/role/info/detailById/' + roleId,
    method: 'get'
  })
}

// 新增字典类型
export function addRole(data) {
  return request({
    url: '/system/role/add/addSysRole',
    method: 'post',
    data: data
  })
}

// 修改字典类型
export function updateRole(data) {
  return request({
    url: '/system/role/update/updateSysRole',
    method: 'put',
    data: data
  })
}

// 删除字典类型
export function delRole(roleId) {
  return request({
    url: '/system/role/delete/deleteById/' + roleId,
    method: 'delete'
  })
}

// 导出字典类型
export function exportRole(query) {
  return request({
    url: '/system/role/export',
    method: 'get',
    params: query
  })
}

// 用户对应的角色Id列表
export function userRoleIdList(userId, tenantId) {
  return request({
    url: '/system/role/list/userRoleIdList/' + userId + '/' + tenantId,
    method: 'get'
  })
}

// 用户对应的角色Id列表
export function userRoleIdListWithNowTenant(userId) {
  return request({
    url: '/system/role/list/userRoleIdList/' + userId,
    method: 'get'
  })
}
