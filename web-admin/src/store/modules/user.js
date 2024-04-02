import { logout, getInfo, getMenu } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import ParentView from '@/components/ParentView'
import Layout from '@/layout'
import Vue from 'vue'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    username: '',
    avatar: '',
    menuList: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    Vue.set(state, 'token', token)
  },
  SET_NAME: (state, name) => {
    Vue.set(state, 'name', name)
  },
  SET_USERNAME: (state, username) => {
    Vue.set(state, 'username', username)
  },
  SET_AVATAR: (state, avatar) => {
    Vue.set(state, 'avatar', avatar)
  },
  SET_MENU: (state, menuList) => {
    Vue.set(state, 'menuList', menuList)
  },
  SET_PERMISSIONS: (state, permissions) => {
    Vue.set(state, 'permissions', permissions)
  }
}

const actions = {
  // user login success
  loginSuccess({ commit }, token) {
    commit('SET_TOKEN', token)
    setToken(token)
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response

        if (!data) {
          reject('Verification failed, please Login again.')
        }

        const { name, permissions, username } = data

        const avatar = data.avatar === '' ? require('@/assets/image/defaultAvatar.png') : process.env.VUE_APP_RESOURCE_API + data.avatar

        commit('SET_NAME', name)
        commit('SET_USERNAME', username)
        commit('SET_AVATAR', avatar)
        commit('SET_PERMISSIONS', permissions)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  },

  getMenu({ commit, state }) {
    return new Promise((resolve, reject) => {
      getMenu().then(response => {
        const { data } = response
        const accessedRoutes = filterAsyncRouter(data)
        accessedRoutes.push({ path: '*', redirect: '/404', hidden: true })
        // 404 page must be placed at the end !!!

        commit('SET_MENU', accessedRoutes)
        resolve(accessedRoutes)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap) {
  if (asyncRouterMap === null) {
    return []
  }
  return asyncRouterMap.filter(route => {
    if (route.component) {
      // Layout组件特殊处理
      if (route.component === 'Layout') {
        route.component = Layout
      } else if (route.component === 'ParentView') {
        route.component = ParentView
      } else {
        route.component = loadView(route.component)
      }
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children)
    }
    return true
  })
}

export const loadView = (view) => { // 路由懒加载
  // return () => import(`@/views/${view}`) // 这种方式 ESLint 报错
  return resolve => require(['@/views/' + view], resolve)
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

