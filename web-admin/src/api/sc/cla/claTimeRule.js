import request from '@/utils/request'

// 查询列表
export function listRule(query) {
  return request({
    url: '/sc/cla/time/rule/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getRule(ruleId) {
  return request({
    url: '/sc/cla/time/rule/info/detailById/' + ruleId,
    method: 'get'
  })
}

// 新增
export function addRule(data) {
  return request({
    url: '/sc/cla/time/rule/add/addScClaTimeRule',
    method: 'post',
    data: data
  })
}

// 修改
export function updateRule(data) {
  return request({
    url: '/sc/cla/time/rule/update/updateScClaTimeRule',
    method: 'put',
    data: data
  })
}

// 删除
export function delRule(ruleId) {
  return request({
    url: '/sc/cla/time/rule/delete/deleteById/' + ruleId,
    method: 'delete'
  })
}
