import axios from 'axios'

const fipeApiService = {
  get (resource) {
    let url = process.env.URL_FIPE_API + resource
    return axios
      .get(`${url}`, {
        headers: {
        }
      })
      .catch((error) => {
        throw new Error(`[RWV] manufacturerService ${error}`)
      })
  }
}

export default fipeApiService

export const manufacturerService = {
  findAllManufacturers () {
    return fipeApiService.get('/carros/marcas.json')
  },
  findModelByManufacturerId (manufacturerId) {
    return fipeApiService.get(`/carros/veiculos/${manufacturerId}.json`)
  },
  findYearByModelId (manufacturerId, modelId) {
    return fipeApiService.get(`/carros/veiculo/${manufacturerId}/${modelId}.json`)
  }
}
