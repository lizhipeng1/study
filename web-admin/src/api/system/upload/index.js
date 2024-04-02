import request from '@/utils/request'

// 查询列表
export function uploadImg(query) {
  return request({
    url: '/api/system/file/updateImg',
    method: 'post',
    params: query
  })
}
