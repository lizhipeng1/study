import request from '@/utils/request'

// 表单重置
export function resetForm(refName) {
  if (this.$refs[refName]) {
    this.$refs[refName].resetFields()
  }
}
// 回显数据字典
export function selectDictLabel(datas, value) {
  var actions = []
  Object.keys(datas).map((key) => {
    if (datas[key].dictValue === ('' + value)) {
      actions.push(datas[key].dictLabel)
      return false
    }
  })
  return actions.join('')
}

// 日期格式化
export function parseTime(time, pattern) {
  if (time === undefined) {
    return null
  }
  if (arguments.length === 0) {
    return null
  }
  const format = pattern || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
      time = parseInt(time)
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value] }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return time_str
}

/**
 * 比较时间
 * @param time
 * @return {null|boolean}
 */
export function afterNow(time) {
  if (time === undefined) {
    return null
  }
  if (arguments.length === 0) {
    return null
  }
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string') && (/^[0-9]+$/.test(time))) {
      time = parseInt(time)
    }
    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  return date.getTime() >= new Date().getTime()
}

// 添加日期范围
export function addDateRange(params, dateRange) {
  var search = params
  search.beginTime = ''
  search.endTime = ''
  if (dateRange != null && dateRange !== '') {
    search.beginTime = dateRange[0]
    search.endTime = dateRange[1]
  }
  return search
}

// 通用下载方法
export function downExportFile(fileName, logicName, notifyInstance) {
  return request({
    url: '/system/file/downExportFile',
    method: 'get',
    params: {
      fileName: fileName,
      logicName: logicName
    },
    timeout: 30000,
    responseType: 'blob'
  }).then(res => {
    if (res) {
      const content = res.data
      if ('download' in document.createElement('a')) {
        // 非IE下载
        const elink = document.createElement('a')
        elink.download = logicName
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(content)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href) // 释放URL 对象
        document.body.removeChild(elink)
      } else {
        // IE10+下载
        navigator.msSaveBlob(content)
      }
      setTimeout(function() {
        notifyInstance.close()
      }, 2000)
    }
  }).catch(() => {
    this.$message.error('下载附件失败，请联系管理员')
  })
  // window.location.href = baseURL + '/system/file/downExportFile?fileName=' + encodeURI(fileName) + '&logicName=' + logicName + '&access_token=' + getToken()
}
