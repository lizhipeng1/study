import request from '@/utils/request'

// 新增班级学员
export function addClaStu(data) {
  return request({
    url: '/sc/course/cla/stu/add/addScCourseClaStu',
    method: 'post',
    data: data
  })
}

// 新增学员班级
export function addStuCla(data) {
  return request({
    url: '/sc/course/cla/stu/add/addScCourseStuCla',
    method: 'post',
    data: data
  })
}

// 删除班级学员
export function delClaStu(studentIds, claId) {
  return request({
    url: '/sc/course/cla/stu/delete/deleteById/' + studentIds + '/' + claId,
    method: 'delete'
  })
}

// 删除学员班级
export function delStuCla(claIds, studentId) {
  return request({
    url: '/sc/course/cla/stu/delete/deleteStuCla/' + claIds + '/' + studentId,
    method: 'delete'
  })
}

// 删除
export function claStuChargeInfo(query) {
  return request({
    url: 'sc/course/cla/stu/info/claStuChargeInfo',
    method: 'get',
    params: query
  })
}

// 修改资费
export function updateClaStuCharge(data) {
  return request({
    url: 'sc/course/cla/stu/update/claStuCharge',
    method: 'put',
    data: data
  })
}

