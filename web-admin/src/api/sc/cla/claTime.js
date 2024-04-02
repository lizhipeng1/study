import request from '@/utils/request'

// 按周获取课表
export function searchListForCalendar(query) {
  return request({
    url: '/sc/cla/time/list/searchListForCalendar',
    method: 'get',
    params: query
  })
}

// 获取最近几天的排课日程
export function searchRecentDayTimeList(query) {
  return request({
    url: '/sc/cla/time/list/searchRecentDayTimeList',
    method: 'get',
    params: query
  })
}

// 获取上课记录
export function selectListForAttend(query) {
  return request({
    url: '/sc/cla/time/list/selectListForAttend',
    method: 'get',
    params: query
  })
}

// 排课详细信息
export function claTimeInfo(courseTimeId) {
  return request({
    url: '/sc/cla/time/info/detailById/' + courseTimeId,
    method: 'get'
  })
}

// 新增
export function addTime(data) {
  return request({
    url: '/sc/cla/time/add/addScClaTime',
    method: 'post',
    data: data
  })
}

// 修改
export function updateTime(data) {
  return request({
    url: '/sc/cla/time/update/updateScClaTime',
    method: 'put',
    data: data
  })
}

// 删除
export function delTime(courseTimeId) {
  return request({
    url: '/sc/cla/time/delete/deleteById/' + courseTimeId,
    method: 'delete'
  })
}

// 变更 已记上课 信息
export function changeHadClaTimeAttend(data) {
  return request({
    url: '/sc/cla/time/update/changeHadClaTimeAttend',
    method: 'post',
    data: data
  })
}

// 删除已上课
export function deleteHadClaTimeAttend(courseTimeId) {
  return request({
    url: '/sc/cla/time/delete/deleteHadClaTimeAttend/' + courseTimeId,
    method: 'delete'
  })
}
