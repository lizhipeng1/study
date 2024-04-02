import request from '@/utils/request'

// 下载导入模板
export function downImportTemplate(query) {
  return request({
    url: '/tool/import/downImportTemplate',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
// 导入数据
export function uploadForImportDataByFileId(data) {
  return request({
    url: '/tool/import/uploadForImportDataByFileId',
    method: 'post',
    data: data
  })
}
