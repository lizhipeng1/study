import request from '@/utils/request'

// 查询列表
export function listOrder(query) {
  return request({
    url: '/sc/order/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getOrder(orderId) {
  return request({
    url: '/sc/order/info/detailById/' + orderId,
    method: 'get'
  })
}

// 新办
export function signUp(data) {
  return request({
    url: '/sc/order/add/signUp',
    method: 'post',
    data: data
  })
}

// 作废
export function invalidById(orderIds) {
  return request({
    url: '/sc/order/delete/invalidById/' + orderIds,
    method: 'post'
  })
}

// 导出
export function exportOrder(query) {
  return request({
    url: '/sc/order/export',
    method: 'get',
    params: query
  })
}
