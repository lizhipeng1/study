import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import './assets/icons' // icon
import '@/permission' // permission control

import permission from './directive'

import { getDictListByDictType, dictTypeDataListByParentValue } from '@/api/system/dict/data'
import { resetForm, selectDictLabel, parseTime, addDateRange, downExportFile } from '@/utils/commonUtils'
import Pagination from '@/components/Pagination'
import Print from '@/plugins/print'
import * as echarts from 'echarts'
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
/*
import { mockXHR } from '../mock'
mockXHR()
if (process.env.NODE_ENV === 'production') {
  mockXHR()
}*/

// 全局组件挂载
Vue.component('Pagination', Pagination)

Vue.use(permission)

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })

// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

// 打印
Vue.use(Print)

Vue.config.productionTip = false

// 全局方法挂载
Vue.prototype.getDictListByDictType = getDictListByDictType
Vue.prototype.dictTypeDataListByParentValue = dictTypeDataListByParentValue
Vue.prototype.selectDictLabel = selectDictLabel

Vue.prototype.resetForm = resetForm
Vue.prototype.parseTime = parseTime
Vue.prototype.addDateRange = addDateRange
Vue.prototype.downExportFile = downExportFile

// echarts
Vue.prototype.$echarts = echarts

Vue.prototype.msgSuccess = function(msg) {
  this.$message({ showClose: true, message: msg, type: 'success' })
}
Vue.prototype.msgError = function(msg) {
  this.$message({ showClose: true, message: msg, type: 'error' })
}
Vue.prototype.msgWarning = function(msg) {
  this.$message({ showClose: true, message: msg, type: 'warning' })
}
Vue.prototype.msgInfo = function(msg) {
  this.$message.info(msg)
}
Vue.prototype.alert = function(msg, fcn) {
  this.$alert(msg, '消息提醒', {
    confirmButtonText: '确定',
    callback: action => {
      if (typeof fcn === 'function') {
        fcn()
      }
    }
  })
}
Vue.prototype.confirm = function(msg, fcn) {
  this.$confirm(msg, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    if (typeof fcn === 'function') {
      fcn()
    }
  }).catch(() => {
  })
}

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
