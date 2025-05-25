module.exports = {
  lintOnSave: false,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:3358',
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