<template>
  <v-container fluid fill-height>
    <v-layout column>
      <v-form ref="form" v-model="valid" lazy-validation>
          <v-layout row>
            <v-select
              v-model="manufacturer"
              :items="manufacturerItems"
              :rules="manufacturerRules"
              label="Manufacturer"
              required
            ></v-select>
            <v-spacer></v-spacer>
            <v-select
              v-model="modelName"
              :items="modelNameItems"
              :rules="modelNameRules"
              label="Model"
              required
            ></v-select>
          </v-layout>
          <v-layout row>
            <v-select
              v-model="modelYear"
              :items="modelYearItems"
              :rules="modelYearRules"
              label="Year"
              required
            ></v-select>
            <v-spacer></v-spacer>
            <v-select
              v-model="category"
              :items="categoryItems"
              :rules="categoryRules"
              label="Category"
              required
            ></v-select>
          </v-layout>
          <v-layout>
            <v-text-field
              v-model="car.weekdayPrice"
              :counter="10"
              type="number"
              label="Weekday Price"
              required
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-text-field
              v-model="car.weekendPrice"
              :counter="10"
              type="number"
              label="Weekend Price"
              required
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-text-field
              v-model="car.weekdayLoyaltyPrice"
              :counter="10"
              type="number"
              label="Weekday Loyalty Price"
              required
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-text-field
              v-model="car.weekendLoyaltyPrice"
              :counter="10"
              type="number"
              label="Weekend Loyalty Price"
              required
            ></v-text-field>
          </v-layout>
          <br>
          <v-btn
            small
            :disabled="!valid"
            @click="submit"
          >Save
          </v-btn>
          <v-btn small @click="clear">Clear</v-btn>
          <v-alert
            v-model="alert"
            dismissible
            :type="typeMessage"
          >
            {{message}}
          </v-alert>
      </v-form>
    </v-layout>
  </v-container>
</template>

<script>
import { SAVE_CAR, LIST_ALL_CATEGORIES, LIST_ALL_MANUFACTURERS, LIST_MODELS_BY_MANUFACTURER, LIST_YEAR_BY_MODEL } from '@/store/actions.type'
import CarManager from '@/components/CarManager'

export default {
  data () {
    return {
      alert: false,
      manufacturer: {
        id: null,
        name: null
      },
      modelName: {
        id: null,
        name: null
      },
      modelYear: {
        id: null,
        value: null
      },
      category: {
        id: null,
        name: null
      },
      car: {
        id: null,
        manufacturer: '',
        modelName: '',
        modelYear: '',
        category: {
          id: null,
          name: null
        },
        weekdayPrice: 0,
        weekendPrice: 0,
        weekdayLoyaltyPrice: 0,
        weekendLoyaltyPrice: 0
      },
      valid: true,
      manufacturerRules: [
        v => !!v || 'Manufacturer is required'
      ],
      modelNameRules: [
        v => !!v || 'ModelName is required'
      ],
      modelYearRules: [
        v => !!v || 'ModelYear is required'
      ],
      categoryRules: [
        v => !!v || 'Category is required'
      ],
      categoryItems: [],
      manufacturerItems: [],
      modelNameItems: [],
      modelYearItems: [],
      message: '',
      showAlert: false,
      typeMessage: 'success',
      listCars: []
    }
  },
  computed: {
    layoutAttributes () {
      return {
        [this.alignment]: 'align-start',
        [this.justify]: false,
        [this.flexDirection]: 'row',
        'fill-height': false
      }
    }
  },
  methods: {
    submit () {
      if (this.$refs.form.validate()) {
        let load = this.$loading.show()
        this.car.manufacturer = this.manufacturer.name
        this.car.modelName = this.modelName.name
        this.car.modelYear = this.modelYear.name.substring(0, 4)
        this.car.category = this.category
        this.$store.dispatch(SAVE_CAR, this.car).then(action => {
          this.car.id = action.id
          this.showAlertMessage('Registry saved!', 'success')
          load.hide()
          this.backToList()
        }).catch((error) => {
          load.hide()
          if (error.response.data !== null && error.response.data.errors !== undefined) {
            this.showAlertMessage(error.response.data.errors[0].defaultMessage, 'error')
            throw new Error(`[RWV] carService ${error.response.data.errors[0].defaultMessage}`)
          } else {
            this.showAlertMessage(error.toString(), 'error')
            throw new Error(`[RWV] carService ${error}`)
          }
        })
      }
    },
    clear () {
      this.$refs.form.reset()
      this.car.id = null
    },
    backToList () {
      this.$root.$emit('changeComponent', CarManager)
    },
    showAlertMessage (message, typeMessage) {
      this.alert = true
      this.message = message
      this.typeMessage = typeMessage
    },
    hideAlert () {
      this.alert = false
      this.message = ''
      this.typeMessage = ''
    },
    loadCategoryItems () {
      var itemsTmp = []
      this.$store.dispatch(LIST_ALL_CATEGORIES).then(action => {
        action.filter(function (value, key) {
          var item = {
            text: value.name,
            value: value
          }
          itemsTmp.push(item)
        })
        this.categoryItems = itemsTmp
      }).catch((error) => {
        this.showAlertMessage(error.toString(), 'error')
        throw new Error(`[RWV] categoryService ${error}`)
      })
    },
    loadManufacturers () {
      var itemsTmp = []
      this.$store.dispatch(LIST_ALL_MANUFACTURERS).then(action => {
        action.filter(function (value, key) {
          var item = {
            text: value.name,
            value: value
          }
          itemsTmp.push(item)
        })
        this.manufacturerItems = itemsTmp
      }).catch((error) => {
        this.showAlertMessage(error.toString(), 'error')
        throw new Error(`[RWV] manufacturerService ${error}`)
      })
    },
    loadModelName (manufacturerId) {
      if (manufacturerId === null) {
        return
      }
      var itemsTmp = []
      this.$store.dispatch(LIST_MODELS_BY_MANUFACTURER, manufacturerId).then(action => {
        action.filter(function (value, key) {
          var item = {
            text: value.name,
            value: value
          }
          itemsTmp.push(item)
        })
        this.modelNameItems = itemsTmp
      }).catch((error) => {
        this.showAlertMessage(error.toString(), 'error')
        throw new Error(`[RWV] manufacturerService ${error}`)
      })
    },
    loadModelYear (manufacturerId, modelId) {
      let payload = {
        manufacturerId: manufacturerId,
        modelId: modelId
      }
      var itemsTmp = []
      this.$store.dispatch(LIST_YEAR_BY_MODEL, payload).then(action => {
        action.filter(function (value, key) {
          var item = {
            text: value.name.substring(0, 4),
            value: value
          }
          itemsTmp.push(item)
        })
        this.modelYearItems = itemsTmp
      }).catch((error) => {
        this.showAlertMessage(error.toString(), 'error')
        throw new Error(`[RWV] manufacturerService ${error}`)
      })
    }
  },
  mounted () {
    this.loadCategoryItems()
    this.loadManufacturers()
  },
  watch: {
    manufacturer (val, oldVal) {
      if (val != null && val.id !== oldVal.id) {
        this.loadModelName(val.id)
      }
    },
    modelName (val, oldVal) {
      if (val != null && val.id !== oldVal.id) {
        this.loadModelYear(this.manufacturer.id, val.id)
      }
    }
  }
}
</script>

<style scoped>
  .resultContainer {
    height: 350px;
  }

  .item {
    min-height: 50px;
    min-width: 80px;
    margin: 10px;
  }

  td {
    text-align: center
  }
</style>

<!--alignmentsAvailable: ['align-center', 'align-end', 'align-space-around', 'align-space-between', 'align-start', ''],
alignmentsContentAvailable: ['align-content-center', 'align-content-end', 'align-content-space-around', 'align-content-space-between', 'align-content-start', ''],
justifyAvailable: ['justify-center', 'justify-end', 'justify-space-around', 'justify-space-between', 'justify-start', ''],
flexDirectionAvailable: ['row', 'column', ''],
-->
