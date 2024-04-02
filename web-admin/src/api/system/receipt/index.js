import request from '@/utils/request'

// 查询列表
export function listReceipt(query) {
  return request({
    url: '/sys/receipt/list/searchList',
    method: 'get',
    params: query
  })
}

// select
export function select() {
  return request({
    url: '/sys/receipt/list/select',
    method: 'get'
  })
}

// 查询详细
export function getReceipt(accountId) {
  return request({
    url: '/sys/receipt/info/detailById/' + accountId,
    method: 'get'
  })
}

// 新增
export function addReceipt(data) {
  return request({
    url: '/sys/receipt/add/addSysReceiptAccount',
    method: 'post',
    data: data
  })
}

// 修改
export function updateReceipt(data) {
  return request({
    url: '/sys/receipt/update/updateSysReceiptAccount',
    method: 'put',
    data: data
  })
}

// 删除
export function delReceipt(accountId) {
  return request({
    url: '/sys/receipt/delete/deleteById/' + accountId,
    method: 'delete'
  })
}

// 导出
export function exportReceipt(query) {
  return request({
    url: '/sys/receipt/export',
    method: 'get',
    params: query
  })
}
