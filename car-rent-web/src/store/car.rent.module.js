
import { carRentService } from '@/api/api-service'
import { SEARCH_CHEPEAST_RENT, SEND_EMAIL } from '@/store/actions.type'

export const actions = {
  [SEARCH_CHEPEAST_RENT] ({commit}, carRentSearch) {
    return carRentService.findTheChepeast(carRentSearch)
      .then(({ data }) => {
        return data
      })
  },
  [SEND_EMAIL] ({commit}, email) {
    return carRentService.sendEmail(email)
      .then(({ data }) => {
        return data
      })
  }
}

export default {
  actions
}
