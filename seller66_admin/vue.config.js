module.exports = {
  lintOnSave: false,
  devServer: {
    disableHostCheck: true,
    port: 8080,
    proxy: {
      '/api': {
        target: 'http://localhost',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/admin'
        }
      },
      '/images': {
        target: 'http://localhost',
        changeOrigin: true,
        pathRewrite: {
          '^/images': '/images'
        }
      }
    }
  }
} 