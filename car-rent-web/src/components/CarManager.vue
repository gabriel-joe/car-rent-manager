<template>
  <v-container fluid fill-height>
    <v-layout row>
      <v-flex xs12>
          <v-layout v-bind="layoutAttributes">
              <v-form ref="form">
                <v-select
                  v-model="category"
                  :items="items"
                  label="Category"
                  required
                ></v-select>
                <v-btn
                  small
                  @click="search"
                >Search
                </v-btn>
                <v-btn small @click="clear">Clear</v-btn>
                <v-btn small @click="newCar">NEW CAR</v-btn>
                <v-alert
                  v-model="alert"
                  dismissible
                  :type="typeMessage"
                >
                  {{message}}
                </v-alert>
              </v-form>
            </v-layout>
          <br>
          <br>
          <br>
          <div class="table-responsive">
          <table class="table table-striped table-bordered" style="width:100%">
            <thead width="400px">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Manufacter<i class="fas fa-sort-alpha-down float-right"></i></th>
                    <th scope="col">Model</th>
                    <th scope="col">Year</th>
                    <th scope="col">Category</th>
                    <th scope="col">Weekday Price</th>
                    <th scope="col">Weekend Price</th>
                    <th scope="col">Weekday Loyalty Price</th>
                    <th scope="col">Weekend Loyalty Price</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(car, index) in listCars" :key="index">
                  <td>{{index + 1}}</td>
                  <td style="text-align: left;">{{car.manufacturer}}</td>
                  <td style="text-align: left;">{{car.modelName}}</td>
                  <td>{{car.modelYear}}</td>
                  <td>{{car.category.name}}</td>
                  <td>{{car.weekdayPrice}}</td>
                  <td>{{car.weekendPrice}}</td>
                  <td>{{car.weekdayLoyaltyPrice}}</td>
                  <td>{{car.weekendLoyaltyPrice}}</td>
                  <td>
                    <v-btn flat icon small
                        @click="remove(car)">
                        <v-icon>delete</v-icon>
                    </v-btn>
                  </td>
                </tr>
            </tbody>
          </table>
        </div>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { LIST_ALL_CARS, LIST_CAR_BY_CATEGORY, DELETE_CAR, LIST_ALL_CATEGORIES } from '@/store/actions.type'
import NewCar from '@/components/NewCar'

export default {
  data () {
    return {
      alert: false,
      category: {
        id: null,
        name: null
      },
      items: [],
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
    search () {
      let load = this.$loading.show()
      this.$store.dispatch(LIST_CAR_BY_CATEGORY, this.category.id).then(action => {
        this.listCars = action
        load.hide()
      }).catch((error) => {
        load.hide()
        this.showAlertMessage(error.toString(), 'error')
        throw new Error(`[RWV] carService ${error}`)
      })
    },
    remove (car) {
      this.$store.dispatch(DELETE_CAR, car).then(action => {
        this.showAlertMessage('Registry deleted!', 'success')
        this.listGrid()
      }).catch((error) => {
        this.showAlertMessage(error.toString(), 'error')
        throw new Error(`[RWV] carService ${error}`)
      })
    },
    clear () {
      this.$refs.form.reset()
      this.category = null
      this.listGrid()
    },
    newCar () {
      this.$root.$emit('changeComponent', NewCar)
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
    listGrid () {
      let load = this.$loading.show()
      this.$store.dispatch(LIST_ALL_CARS).then(action => {
        this.listCars = action
        load.hide()
      }).catch((error) => {
        load.hide()
        this.showAlertMessage(error.toString(), 'error')
        throw new Error(`[RWV] carService ${error}`)
      })
    }
  },
  mounted () {
    this.listGrid()
    var itemsTmp = []
    this.$store.dispatch(LIST_ALL_CATEGORIES).then(action => {
      action.filter(function (value, key) {
        var item = {
          text: value.name,
          value: value
        }
        itemsTmp.push(item)
      })
      this.items = itemsTmp
    }).catch((error) => {
      this.showAlertMessage(error.toString(), 'error')
      throw new Error(`[RWV] categoryService ${error}`)
    })
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
