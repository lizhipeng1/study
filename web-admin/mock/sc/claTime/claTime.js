export default [
  {
    url: '/sc/claTime/claTimeByClaId/*',
    type: 'get',
    response: config => {
      return {
        respCode: '0000',
        data: [
          {
            title: '{"courseName":"语文","claColor":"#409EFF","date":"2020-03-28","startTime":"21:00","endTime":"22:00","status":"wait","id":"1"}',
            start: '2020-03-28 21:00',
            end: '2020-03-28 22:00',
            className: 'course-event',
            id: '1'
          },
          {
            title: '{"courseName":"数学","claColor":"#67C23A","date":"2020-03-28","startTime":"09:00","endTime":"11:00","status":"complete","id":"2"}',
            start: '2020-03-28 09:00',
            end: '2020-03-28 11:00',
            className: 'course-event',
            id: '2'
          },
          {
            title: '{"courseName":"作文","claColor":"#E6A23C","date":"2020-03-28","startTime":"09:00","endTime":"11:00","status":"complete","id":"3"}',
            start: '2020-03-28 09:00',
            end: '2020-03-28 11:00',
            className: 'course-event',
            id: '3'
          }
        ]
      }
    }
  }
]
