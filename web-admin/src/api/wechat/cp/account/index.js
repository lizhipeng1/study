import request from '@/utils/request'

// 查询列表
export function listCpAccount(query) {
  return request({
    url: '/wechat/cp/account/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getCpAccount(cpAccountId) {
  return request({
    url: '/wechat/cp/account/info/detailById/' + cpAccountId,
    method: 'get'
  })
}

// 新增
export function addCpAccount(data) {
  return request({
    url: '/wechat/cp/account/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateCpAccount(data) {
  return request({
    url: '/wechat/cp/account/update',
    method: 'put',
    data: data
  })
}

// 删除
export function delCpAccount(cpAccountId) {
  return request({
    url: '/wechat/cp/account/delete/deleteById/' + cpAccountId,
    method: 'delete'
  })
}
