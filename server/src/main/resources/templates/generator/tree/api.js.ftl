[#assign businessNameFristUpper=table.businessName?cap_first]
import request from '@/utils/request'

// 查询列表
export function list${businessNameFristUpper}(query) {
    return request({
        url: '/${table.moduleName}/${table.businessName}/list/searchList',
        method: 'get',
        params: query
    })
}

// 查询下拉树结构
export function treeSelect() {
    return request({
        url: '/${table.moduleName}/${table.businessName}/list/treeSelect',
        method: 'get'
    })
}

// 查询详细
export function get${businessNameFristUpper}(${pkColumn}) {
    return request({
        url: '/${table.moduleName}/${table.businessName}/info/detailById/' + ${pkColumn},
        method: 'get'
    })
}

// 新增
export function add${businessNameFristUpper}(data) {
    return request({
        url: '/${table.moduleName}/${table.businessName}/add/add${table.className}',
        method: 'post',
        data: data
    })
}

// 修改
export function update${businessNameFristUpper}(data) {
    return request({
        url: '/${table.moduleName}/${table.businessName}/update/update${table.className}',
        method: 'put',
        data: data
    })
}

// 删除
export function del${businessNameFristUpper}(${pkColumn}) {
    return request({
        url: '/${table.moduleName}/${table.businessName}/delete/deleteById/' + ${pkColumn},
        method: 'delete'
    })
}

// 导出
export function export${businessNameFristUpper}(query) {
    return request({
        url: '/${table.moduleName}/${table.businessName}/export',
        method: 'get',
        params: query
    })
}
