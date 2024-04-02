import request from '@/utils/request'

// 查询列表
export function listRoom(query) {
  return request({
    url: '/sc/room/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询列表
export function select(query) {
  return request({
    url: '/sc/room/list/select',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getRoom(roomId) {
  return request({
    url: '/sc/room/info/detailById/' + roomId,
    method: 'get'
  })
}

// 新增
export function addRoom(data) {
  return request({
    url: '/sc/room/add/addScRoom',
    method: 'post',
    data: data
  })
}

// 修改
export function updateRoom(data) {
  return request({
    url: '/sc/room/update/updateScRoom',
    method: 'put',
    data: data
  })
}

// 删除
export function delRoom(roomId) {
  return request({
    url: '/sc/room/delete/deleteById/' + roomId,
    method: 'delete'
  })
}

// 导出
export function exportRoom(query) {
  return request({
    url: '/sc/room/export',
    method: 'get',
    params: query
  })
}
