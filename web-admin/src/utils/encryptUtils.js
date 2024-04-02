import CryptoJS from 'crypto-js'

function encrypt(word, keyStr) {
  keyStr = keyStr || '0123456789ABCDEF'
  const key = CryptoJS.enc.Utf8.parse(keyStr)
  const srcs = CryptoJS.enc.Utf8.parse(word)
  const encrypted = CryptoJS.AES.encrypt(srcs, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  })
  return encrypted
}

export function decryptBase64(word, keyStr) {
  keyStr = keyStr || '0123456789ABCDEF'
  var key = CryptoJS.enc.Utf8.parse(keyStr)
  var decrypt = CryptoJS.AES.decrypt(word, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  })
  return CryptoJS.enc.Utf8.stringify(decrypt).toString()
}

export function encryptHex(word, keyStr) {
  const encrypted = encrypt(word, keyStr)
  return encrypted.ciphertext.toString().toUpperCase()
}

export function encryptBase64(word, keyStr) {
  const encrypted = encrypt(word, keyStr)
  return CryptoJS.enc.Base64.stringify(encrypted.ciphertext)
}

export function decryptHex(hexWord, keyStr) {
  const wordArray = CryptoJS.enc.Hex.parse(hexWord)
  const base64Word = CryptoJS.enc.Base64.stringify(wordArray)
  return decryptBase64(base64Word, keyStr)
}
