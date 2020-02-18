'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  URL_API: '"http://localhost:8090/car-rent-service"',
  URL_FIPE_API: '"http://fipeapi.appspot.com/api/1"'
})


