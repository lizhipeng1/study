import request from '@/utils/request'

// 查询列表
export function listUser(query) {
  return request({
    url: '/system/user/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getUser(userId) {
  return request({
    url: '/system/user/info/detailById/' + userId,
    method: 'get'
  })
}

// 新增
export function addUser(data) {
  return request({
    url: '/system/user/add/addSysUser',
    method: 'post',
    data: data
  })
}

// 修改
export function updateUser(data) {
  return request({
    url: '/system/user/update/updateSysUser',
    method: 'put',
    data: data
  })
}

// 删除
export function delUser(userId) {
  return request({
    url: '/system/user/delete/deleteById/' + userId,
    method: 'delete'
  })
}

// 导出
export function exportUser(query) {
  return request({
    url: '/system/user/export',
    method: 'get',
    params: query
  })
}

// 是否可用
export function changeUserEnable(userId, enable) {
  return request({
    url: '/system/user/update/changeUserEnable',
    method: 'put',
    data: {
      userId,
      enable
    }
  })
}

// 重置密码
export function resetUserPwd(userId, password) {
  return request({
    url: '/system/user/update/resetUserPwd',
    method: 'put',
    data: {
      userId,
      password
    }
  })
}

// 修改用户角色
export function changeUserRole(data) {
  return request({
    url: '/system/user/update/changeUserRole',
    method: 'put',
    data: data
  })
}

// 修改用户租户
export function changeUserTenant(data) {
  return request({
    url: '/system/user/update/changeUserTenant',
    method: 'put',
    data: data
  })
}

// 切换当前租户
export function switchNowTenant(tenantId) {
  return request({
    url: '/system/user/switchNowTenant/' + tenantId,
    method: 'get'
  })
}

// 登录用户信息
export function getUserProfile() {
  return request({
    url: '/system/user/info/getUserProfile',
    method: 'get'
  })
}

// 修改用户基本信息
export function updateUserProfile(data) {
  return request({
    url: '/system/user/update/updateUserProfile',
    method: 'put',
    data: data
  })
}

// 修改用户基本信息
export function updateUserPwd(data) {
  return request({
    url: '/system/user/update/updateUserPwd',
    method: 'put',
    data: data
  })
}

// 上传头像
export function uploadAvatar(data) {
  return request({
    url: '/system/user/update/uploadAvatar',
    method: 'post',
    data: data
  })
}

// 校验账号是否已注册
export function checkUsernameUnique(username) {
  return request({
    url: '/system/user/info/checkUsernameUnique/' + username,
    method: 'get'
  })
}
