import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'

const apiService = {
  init () {
    Vue.use(VueAxios, axios)
    Vue.axios.defaults.baseURL = process.env.URL_API
  },

  setHeader () {
    Vue.axios.defaults.headers.common['Content-Type'] = 'application/json'
  },

  query (resource, params) {
    return Vue.axios
      .get(resource, params)
      .catch((error) => {
        throw new Error(`[RWV] apiService ${error}`)
      })
  },

  get (resource) {
    return Vue.axios
      .get(`${resource}`, this.setHeader())
      .catch((error) => {
        throw new Error(`[RWV] apiService ${error}`)
      })
  },

  post (resource, params) {
    return Vue.axios.post(`${resource}`, params, this.setHeader())
  },

  update (resource, slug, params) {
    return Vue.axios.put(`${resource}/${slug}`, params)
  },

  put (resource, params) {
    return Vue.axios
      .put(`${resource}`, params)
  },
  delete (resource) {
    return Vue.axios
      .delete(resource)
      .catch((error) => {
        throw new Error(`[RWV] apiService ${error}`)
      })
  }
}

export default apiService

export const carService = {
  findAll () {
    return apiService.get('/car/findAll')
  },
  findByCategoryId (categoryId) {
    return apiService.get(`/car/findByCategory/${categoryId}`)
  },
  save (car) {
    return apiService.post('/car/save', car)
  },
  delete (car) {
    return apiService.delete(`/car/delete/${car.id}`)
  }
}

export const categoryService = {
  findAll () {
    return apiService.get('/category/findAll')
  }
}

export const carRentService = {
  findTheChepeast (carRentSearch) {
    return apiService.post('/rent/search', carRentSearch)
  },
  sendEmail (email) {
    return apiService.post('/rent/sendEmail', email)
  }
}
