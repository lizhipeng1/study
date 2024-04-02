import request from '@/utils/request'

// 查询字典数据列表
export function listData(query) {
  return request({
    url: '/system/dict/data/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询字典数据详细
export function getData(dictDataId) {
  return request({
    url: '/system/dict/data/info/detailById/' + dictDataId,
    method: 'get'
  })
}

// 根据字典类型查询字典数据信息
export function getDictListByDictType(dictType) {
  return request({
    url: '/system/dict/data/list/dictType/' + dictType,
    method: 'get'
  })
}

// 根据字典类型查询字典数据信息
export function getDictPageListByDictType(dictType, data) {
  return request({
    url: '/system/dict/data/list/dictTypeByPage/' + dictType,
    method: 'get',
    params: data
  })
}

// 根据字典类型查询字典数据信息
export function dictTypeDataListByParentValue(dictType, parentValue) {
  return request({
    url: '/system/dict/data/list/dictType/' + dictType + '/' + parentValue,
    method: 'get'
  })
}

// 新增字典数据
export function addData(data) {
  return request({
    url: '/system/dict/data/add/addDictData',
    method: 'post',
    data: data
  })
}

// 修改字典数据
export function updateData(data) {
  return request({
    url: '/system/dict/data/update/updateDictData',
    method: 'put',
    data: data
  })
}

// 删除字典数据
export function delData(dictDataId) {
  return request({
    url: '/system/dict/data/delete/deleteById/' + dictDataId,
    method: 'delete'
  })
}

// 导出字典数据
export function exportData(query) {
  return request({
    url: '/system/dict/data/export',
    method: 'get',
    params: query
  })
}
