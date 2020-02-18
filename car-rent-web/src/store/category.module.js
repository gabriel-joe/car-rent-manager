
import { categoryService } from '@/api/api-service'
import { LIST_ALL_CATEGORIES } from '@/store/actions.type'

export const actions = {
  [LIST_ALL_CATEGORIES] ({commit}) {
    return categoryService.findAll()
      .then(({ data }) => {
        return data
      })
  }
}

export default {
  actions
}
