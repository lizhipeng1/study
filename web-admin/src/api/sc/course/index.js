import request from '@/utils/request'

// 查询列表
export function listCourse(query) {
  return request({
    url: '/sc/course/list/searchList',
    method: 'get',
    params: query
  })
}

// 查询列表 （含有学生报读状态）
export function selectCourseListWithStudentCourse(query) {
  return request({
    url: '/sc/course/list/selectCourseListWithStudentCourse',
    method: 'get',
    params: query
  })
}

// select
export function select() {
  return request({
    url: '/sc/course/list/select',
    method: 'get'
  })
}

// 查询详细
export function getCourse(courseId) {
  return request({
    url: '/sc/course/info/detailById/' + courseId,
    method: 'get'
  })
}

// 新增
export function addCourse(data) {
  return request({
    url: '/sc/course/add/addScCourse',
    method: 'post',
    data: data
  })
}

// 修改
export function updateCourse(data) {
  return request({
    url: '/sc/course/update/updateScCourse',
    method: 'put',
    data: data
  })
}

// 删除
export function delCourse(courseId) {
  return request({
    url: '/sc/course/delete/deleteById/' + courseId,
    method: 'delete'
  })
}

// 导出
export function exportCourse(query) {
  return request({
    url: '/sc/course/export/exportCourse',
    method: 'get',
    params: query
  })
}

// 是否开售
export function changeCourseSale(courseId, sale) {
  return request({
    url: '/sc/course/update/changeCourseSale',
    method: 'put',
    data: {
      courseId,
      sale
    }
  })
}

// 报名 已选择课程详情
export function orderCourseDetail(query) {
  return request({
    url: '/sc/course/info/orderCourseDetail',
    method: 'get',
    params: query
  })
}

// 学生是否可报读课程
export function studentCanSignUpCourse(query) {
  return request({
    url: '/sc/course/info/studentCanSignUpCourse',
    method: 'get',
    params: query
  })
}
