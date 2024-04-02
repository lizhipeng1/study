import request from '@/utils/request'

// 查询列表
export function studentCourseInfo(studentId) {
  return request({
    url: '/sc/studentCourse/info/studentCourseInfo/' + studentId,
    method: 'get'
  })
}

// 班级课程 人员列表
export function searchCourseClaStudent(query) {
  return request({
    url: '/sc/studentCourse/list/searchCourseClaStudent',
    method: 'get',
    params: query
  })
}

// 未选班 选班
export function studentCourseChooseCla(data) {
  return request({
    url: '/sc/studentCourse/update/studentCourseChooseCla',
    method: 'post',
    data: data
  })
}

// 记上课
export function claTimeAttend(data) {
  return request({
    url: '/sc/studentCourse/update/claTimeAttend',
    method: 'post',
    data: data
  })
}

// 停课
export function stopStudentCourseStatus(studentCourseId) {
  return request({
    url: '/sc/studentCourse/update/stopStudentCourseStatus/' + studentCourseId,
    method: 'post'
  })
}

// 在读
export function atClaStudentCourseStatus(studentCourseId) {
  return request({
    url: '/sc/studentCourse/update/atClaStudentCourseStatus/' + studentCourseId,
    method: 'post'
  })
}

// 学生报读课程列表
export function searchStudentCourse(query) {
  return request({
    url: '/sc/studentCourse/list/searchStudentCourse',
    method: 'get',
    params: query
  })
}

// 报读列表
export function searchStuCourseSignUpList(query) {
  return request({
    url: '/sc/studentCourse/list/searchStuCourseSignUpList',
    method: 'get',
    params: query
  })
}

// 报读信息中 将学员从班级中移除
export function removeStuFromCla(studentCourseId) {
  return request({
    url: '/sc/studentCourse/update/removeStuFromCla/' + studentCourseId,
    method: 'post'
  })
}
