
import { carService } from '@/api/api-service'
import { SAVE_CAR, LIST_ALL_CARS, LIST_CAR_BY_CATEGORY, DELETE_CAR } from '@/store/actions.type'

export const actions = {
  [SAVE_CAR] ({commit}, car) {
    return carService.save(car)
      .then(({ data }) => {
        return data
      })
  },
  [LIST_ALL_CARS] (result) {
    return carService.findAll()
      .then(({ data }) => {
        return data
      })
  },
  [LIST_CAR_BY_CATEGORY] ({commit}, categoryId) {
    return carService.findByCategoryId(categoryId)
      .then(({ data }) => {
        return data
      })
  },
  [DELETE_CAR] ({commit}, car) {
    return carService.delete(car)
      .then(({ data }) => {
        return data
      })
  }
}

export default {
  actions
}
