import request from '@/utils/request'

// 查询列表
export function listCpContactWay(query) {
  return request({
    url: '/wechat/cp/contactWay/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getCpContactWay(configId) {
  return request({
    url: '/wechat/cp/contactWay/info/detailById/' + configId,
    method: 'get'
  })
}

// 新增
export function addCpContactWay(data) {
  return request({
    url: '/wechat/cp/contactWay/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateCpContactWay(data) {
  return request({
    url: '/wechat/cp/contactWay/update',
    method: 'put',
    data: data
  })
}

// 删除
export function delCpContactWay(configId) {
  return request({
    url: '/wechat/cp/contactWay/delete/deleteById/' + configId,
    method: 'delete'
  })
}
