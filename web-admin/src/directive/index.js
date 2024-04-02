import hasRole from './permission/hasRole'
import hasPermi from './permission/hasPermi'
import selectLoadMore from './select/selectLoadMore'
import tableLoadMore from './table/tableLoadMore'

const install = function(Vue) {
  Vue.directive('hasRole', hasRole)
  Vue.directive('hasPermi', hasPermi)
  Vue.directive('selectLoadMore', selectLoadMore)
  Vue.directive('tableLoadMore', tableLoadMore)
}

if (window.Vue) {
  window['hasRole'] = hasRole
  window['hasPermi'] = hasPermi
}

export default install
