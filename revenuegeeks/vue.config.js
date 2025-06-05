module.exports = {
  lintOnSave: false,
  devServer: {
    disableHostCheck: true,
    proxy: {
      '/api': {
        target: 'http://localhost',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/front'
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