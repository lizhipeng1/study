import request from '@/utils/request'

// 查询字典类型列表
export function listType(query) {
  return request({
    url: '/system/dict/type/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询字典类型详细
export function getType(dictTypeId) {
  return request({
    url: '/system/dict/type/info/detailById/' + dictTypeId,
    method: 'get'
  })
}

// 新增字典类型
export function addType(data) {
  return request({
    url: '/system/dict/type/add/addDictType',
    method: 'post',
    data: data
  })
}

// 修改字典类型
export function updateType(data) {
  return request({
    url: '/system/dict/type/update/updateDictType',
    method: 'put',
    data: data
  })
}

// 删除字典类型
export function delType(dictTypeId) {
  return request({
    url: '/system/dict/type/delete/deleteById/' + dictTypeId,
    method: 'delete'
  })
}

// 导出字典类型
export function exportType(query) {
  return request({
    url: '/system/dict/type/export',
    method: 'get',
    params: query
  })
}

// 导出字典类型
export function optionSelect() {
  return request({
    url: '/system/dict/type/list/optionSelect',
    method: 'get'
  })
}
