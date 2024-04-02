/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}

export function isPass(value) {
  const pattern = /^.*(?=.{6,16})(?=.*\d)(?=.*[A-Z]{1,})(?=.*[a-z]{1,})(?=.*[!@#$%^&*?\(\)]).*$/
  return pattern.test(value)
}

export function isSerialNumber(value) {
  return /^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)
}

export function isUsername(rule, value, callback) {
  return /^[a-zA-Z0-9_]{4,16}$/.test(value)
}
