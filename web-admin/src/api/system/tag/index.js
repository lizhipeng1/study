import request from '@/utils/request'

// 查询列表
export function listTag(query) {
  return request({
    url: '/sys/tag/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getTag(tagId) {
  return request({
    url: '/sys/tag/info/detailById/' + tagId,
    method: 'get'
  })
}

// 新增
export function addTag(data) {
  return request({
    url: '/sys/tag/add/addSysTag',
    method: 'post',
    data: data
  })
}

// 修改
export function updateTag(data) {
  return request({
    url: '/sys/tag/update/updateSysTag',
    method: 'put',
    data: data
  })
}

// 删除
export function delTag(tagId) {
  return request({
    url: '/sys/tag/delete/deleteById/' + tagId,
    method: 'delete'
  })
}

// 导出
export function exportTag(query) {
  return request({
    url: '/sys/tag/export',
    method: 'get',
    params: query
  })
}
