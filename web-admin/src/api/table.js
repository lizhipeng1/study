import request from '@/utils/requestMock'

export function getList(params) {
  return request({
    url: '/table/list',
    method: 'get',
    params
  })
}
