
import { manufacturerService } from '@/api/fipe-api-service'
import { LIST_ALL_MANUFACTURERS, LIST_MODELS_BY_MANUFACTURER, LIST_YEAR_BY_MODEL } from '@/store/actions.type'

export const actions = {
  [LIST_ALL_MANUFACTURERS] ({commit}) {
    return manufacturerService.findAllManufacturers()
      .then(({ data }) => {
        return data
      })
  },
  [LIST_MODELS_BY_MANUFACTURER] ({commit}, manufacturerId) {
    return manufacturerService.findModelByManufacturerId(manufacturerId)
      .then(({ data }) => {
        return data
      })
  },
  [LIST_YEAR_BY_MODEL] ({commit}, payload) {
    return manufacturerService.findYearByModelId(payload.manufacturerId, payload.modelId)
      .then(({ data }) => {
        return data
      })
  }
}

export default {
  actions
}
