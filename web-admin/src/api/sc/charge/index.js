import request from '@/utils/request'

// 查询列表
export function listCharge(query) {
  return request({
    url: '/sc/charge/list/searchList',
    method: 'get',
    params: query
  })
}

// select
export function select(query) {
  return request({
    url: '/sc/charge/list/select',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getCharge(chargeId) {
  return request({
    url: '/sc/charge/info/detailById/' + chargeId,
    method: 'get'
  })
}

// 新增
export function addCharge(data) {
  return request({
    url: '/sc/charge/add/addScChargeConfig',
    method: 'post',
    data: data
  })
}

// 修改
export function updateCharge(data) {
  return request({
    url: '/sc/charge/update/updateScChargeConfig',
    method: 'put',
    data: data
  })
}

// 导出
export function exportCharge(query) {
  return request({
    url: '/sc/charge/export',
    method: 'get',
    params: query
  })
}
