import request from '@/utils/request'

// 查询部门列表
export function listOnlineUser(query) {
  return request({
    url: '/monitor/online/list/searchList',
    method: 'get',
    params: query
  })
}

// 强制下线
export function forceOffline(userId, jti) {
  return request({
    url: '/monitor/online/force/offline/' + userId + '/' + jti,
    method: 'delete'
  })
}
