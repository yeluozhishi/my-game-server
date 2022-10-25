const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    host: '127.0.0.1',
    port: 8080,
    proxy:{
      '/WEB-CENTER': {
        target: "http://127.0.0.1:5020/WEB-CENTER",
        secure: false,
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          '^/WEB-CENTER':'/'
        }
      }
    }
  }
})

