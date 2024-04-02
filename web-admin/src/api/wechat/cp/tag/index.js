import request from '@/utils/request'

// 查询列表
export function listCpTag(query) {
  return request({
    url: '/wechat/cp/tag/list/searchList',
    method: 'get',
    params: query
  })
}

// select
export function groupSelect(query) {
  return request({
    url: '/wechat/cp/tag/list/groupSelect',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getCpTag(tagId) {
  return request({
    url: '/wechat/cp/tag/info/detailById/' + tagId,
    method: 'get'
  })
}

// 新增
export function addCpTag(data) {
  return request({
    url: '/wechat/cp/tag/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateCpTag(data) {
  return request({
    url: '/wechat/cp/tag/update',
    method: 'put',
    data: data
  })
}

// 删除
export function delCpTag(tagId) {
  return request({
    url: '/wechat/cp/tag/delete/deleteById/' + tagId,
    method: 'delete'
  })
}
