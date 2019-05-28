const path = require('path')
const CompressionWebpackPlugin = require('compression-webpack-plugin')
const productionGzipExtensions = ['js', 'css']
const isProduction = process.env.NODE_ENV === 'production'

module.exports = {
  configureWebpack: config => {
    if (isProduction) {
      config.plugins.push(new CompressionWebpackPlugin({
          algorithm: 'gzip',
          test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
          threshold: 10240,
          minRatio: 0.8
        })
      )
    }
  }
}