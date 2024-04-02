import request from '@/utils/request'

// 某课程 学生到期日
export function stuCourseDateAccountList(query) {
  return request({
    url: '/sc/account/list/stuCourseDateAccountList',
    method: 'get',
    params: query
  })
}
// 查询列表
export function listAccount(query) {
  return request({
    url: '/sc/account/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getAccount(accountId) {
  return request({
    url: '/sc/account/info/detailById/' + accountId,
    method: 'get'
  })
}

// 新增
export function addAccount(data) {
  return request({
    url: '/sc/account/add/addScStudentAccountMoney',
    method: 'post',
    data: data
  })
}

// 修改
export function updateAccount(data) {
  return request({
    url: '/sc/account/update/updateScStudentAccountMoney',
    method: 'put',
    data: data
  })
}

// 删除
export function delAccount(accountId) {
  return request({
    url: '/sc/account/delete/deleteById/' + accountId,
    method: 'delete'
  })
}

// 导出
export function exportAccount(query) {
  return request({
    url: '/sc/account/export',
    method: 'get',
    params: query
  })
}
