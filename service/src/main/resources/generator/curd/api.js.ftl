[#assign businessNameFirstUpper=table.businessName?cap_first]
[#assign childModuleNameFirstUpper=table.childModuleName?cap_first]
[#assign moduleNameFirstUpper=table.moduleName?cap_first]
import request from '@/utils/request'

// 查询列表
export function list${childModuleNameFirstUpper}${businessNameFirstUpper}(query) {
	return request({
		url: '/${table.moduleName}/${table.childModuleName}/${table.businessName}/list/searchList',
		method: 'get',
		params: query
	})
}

// 查询详细
export function get${childModuleNameFirstUpper}${businessNameFirstUpper}(${pkColumn}) {
	return request({
		url: '/${table.moduleName}/${table.childModuleName}/${table.businessName}/info/detailById/' + ${pkColumn},
		method: 'get'
	})
}

// 新增
export function add${childModuleNameFirstUpper}${businessNameFirstUpper}(data) {
	return request({
		url: '/${table.moduleName}/${table.childModuleName}/${table.businessName}/add',
		method: 'post',
		data: data
	})
}

// 修改
export function update${childModuleNameFirstUpper}${businessNameFirstUpper}(data) {
	return request({
		url: '/${table.moduleName}/${table.childModuleName}/${table.businessName}/update',
		method: 'put',
		data: data
	})
}

// 删除
export function del${childModuleNameFirstUpper}${businessNameFirstUpper}(${pkColumn}) {
	return request({
		url: '/${table.moduleName}/${table.childModuleName}/${table.businessName}/delete/deleteById/' + ${pkColumn},
		method: 'delete'
	})
}
