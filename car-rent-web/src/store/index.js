import Vue from 'vue'
import Vuex from 'vuex'

import car from './car.module'
import carRent from './car.rent.module'
import category from './category.module'
import manufacturer from './manufacturer.module'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    car,
    carRent,
    category,
    manufacturer
  }
})
