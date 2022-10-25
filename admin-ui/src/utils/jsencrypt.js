import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDArRrHR67yhdC2VKkgHS5VCn30\n' +
    'vrSexCFaTeRMLUIRnlLMrfhr8xaqBckFNVC2BR9FzyLUVhSoaGEMtjfGEUTgkxjP\n' +
    'AXMzgvPUukxIg0SrmES3lX0yumSUJHW1jZpcpp8GaKzEa81XBM/1rQ9okN8iDYhA\n' +
    'wm84A6FlbX/LuUru6QIDAQAB'

const privateKey = 'MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMCtGsdHrvKF0LZU\n' +
    'qSAdLlUKffS+tJ7EIVpN5EwtQhGeUsyt+GvzFqoFyQU1ULYFH0XPItRWFKhoYQy2\n' +
    'N8YRROCTGM8BczOC89S6TEiDRKuYRLeVfTK6ZJQkdbWNmlymnwZorMRrzVcEz/Wt\n' +
    'D2iQ3yINiEDCbzgDoWVtf8u5Su7pAgMBAAECgYEAlJ4xdJtXV5xeM/WziqDXlyMD\n' +
    'qt/axEf2jghc22Fhf+OGbHiYkPOtfLH/r3PDcNMcnyU58V0AjDIPAdYfyTiSzei4\n' +
    'eD/TNO00zX2yqjcvolYVvG3nA6f10hHQ+d9MZzmxpWRb736jyFZe80I/PeyeaLb6\n' +
    'dSPy9q4ciS7FmzaSao0CQQD/4ip7q6vUc+uhgfYpnxTTXmeFslpuehD8NLS0Vb+G\n' +
    'DmVdNhikAFB4AhxZ6/8p6J3/NwQ3xSLNBOGV0T6gEDbfAkEAwMORtSXGoKFUpxaZ\n' +
    'Vz36XOvZsW3T+2NO8TNq7AEPUwKRYuKTEw2qQRuv/Bj2o188kVzRVKTk2ZN1cDn9\n' +
    'HM57NwJBANM6i/9TE6Fpn9XPRrnNDI//mk2PkX8pXOQVTiafsl6je6GainS3nbWz\n' +
    'aFwCqLybvfF0JRM0tSmxIddex+ex+tkCQDWGmLEqWitEYRM8jTvSc/DVdM915cUK\n' +
    'YGNNmcUKzkyIjAJfU+OYicwmegmQjPv0eAcNNPtCBmH8NkIBeC1FkKUCQQCNTZZy\n' +
    'Aa2Z8FoOxN7eE8DVN9zt4X+Yl34t8aCdzEuqus8E9O3sJZ6o2mpsMfVjvuE3FNo0\n' +
    '8oClCcB/GJJ2JF7C'

// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对数据进行加密
}

// 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey) // 设置私钥
  return encryptor.decrypt(txt) // 对数据进行解密
}

