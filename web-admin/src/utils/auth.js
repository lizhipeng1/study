import Cookies from 'js-cookie'

const TokenKey = 'reqToken'
const rememberMeCookieName = process.env.VUE_REMEMBER_ME_COOKIE_NAME

export function getToken() {
  return Cookies.get(TokenKey) || ''
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

// 是否自动登录
export function isRememberMe() {
  return Cookies.get(rememberMeCookieName) || ''
}
