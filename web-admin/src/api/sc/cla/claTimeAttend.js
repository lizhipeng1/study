import request from '@/utils/request'

// 查询列表
export function listClaTimeAttend(query) {
  return request({
    url: '/sc/cla/time/attend/list/searchList',
    method: 'get',
    params: query
  })
}

// 上课 出席详情
export function hadClaTimeAttendDetail(courseTimeId) {
  return request({
    url: '/sc/cla/time/attend/info/hadClaTimeAttendDetail/' + courseTimeId,
    method: 'get'
  })
}

// 查询详细
export function getClaTimeAttend(attendId) {
  return request({
    url: '/sc/cla/time/attend/info/detailById/' + attendId,
    method: 'get'
  })
}

// 新增
export function addClaTimeAttend(data) {
  return request({
    url: '/sc/cla/time/attend/add/addScClaTimeAttend',
    method: 'post',
    data: data
  })
}

// 修改
export function updateClaTimeAttend(data) {
  return request({
    url: '/sc/cla/time/attend/update/updateScClaTimeAttend',
    method: 'put',
    data: data
  })
}

// 删除
export function delClaTimeAttend(attendId) {
  return request({
    url: '/sc/cla/time/attend/delete/deleteById/' + attendId,
    method: 'delete'
  })
}
