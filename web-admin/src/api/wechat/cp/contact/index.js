import request from '@/utils/request'

// 查询列表
export function listCpContact(query) {
  return request({
    url: '/wechat/cp/contact/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getCpContact(externalUserId) {
  return request({
    url: '/wechat/cp/contact/info/detailById/' + externalUserId,
    method: 'get'
  })
}

// 新增
export function addCpContact(data) {
  return request({
    url: '/wechat/cp/contact/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateCpContact(data) {
  return request({
    url: '/wechat/cp/contact/update',
    method: 'put',
    data: data
  })
}

// 删除
export function delCpContact(externalUserId) {
  return request({
    url: '/wechat/cp/contact/delete/deleteById/' + externalUserId,
    method: 'delete'
  })
}
