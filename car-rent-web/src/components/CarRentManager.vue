<template>
   <v-container fluid fill-height>
      <v-layout row wrap>
         <v-form ref="form" v-model="valid" lazy-validation>
            <v-layout row>
               <v-menu
                  v-model="menu1"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                  <template v-slot:activator="{ on }">
                    <v-text-field
                      v-model="carRentSearch.dateFrom"
                      label="Start date"
                      prepend-icon="event"
                      readonly
                      :rules="dateFromRules"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="carRentSearch.dateFrom" @input="menu1 = false"></v-date-picker>
                </v-menu>
                <br>
                <v-menu
                  v-model="menu2"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                  <template v-slot:activator="{ on }">
                    <v-text-field
                      v-model="carRentSearch.dateTo"
                      label="End Date"
                      prepend-icon="event"
                      :rules="dateToRules"
                      readonly
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="carRentSearch.dateTo" @input="menu2 = false"></v-date-picker>
                </v-menu>
            </v-layout>
            <v-layout row>
               <v-checkbox
                  v-model="carRentSearch.loyalty"
                  label="Loyalty"
                  required
                  ></v-checkbox>
                <v-text-field
                    v-model="userEmail"
                    label="E-mail"
                    :rules="[rules.email]"
                    required/>
            </v-layout>
            <v-btn
              small
              :disabled="!valid"
              @click="search"
              >Search
            </v-btn>
            <v-btn small @click="clear">Clear</v-btn>
            <br>
            <br>
            <br>
            <div class="table-responsive">
              <table class="table table-striped table-bordered" style="width:100%">
                <thead width="400px">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Model<i class="fas fa-sort-alpha-down float-right"></i></th>
                        <th scope="col">Price<i class="fas fa-sort-alpha-down float-right"></i></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(carRent, index) in listCars" :key="index">
                      <td>{{index + 1}}</td>
                      <td>{{carRent.modelComplete}}</td>
                      <td>{{carRent.sumPrice}}</td>
                      <td>
                        <v-btn flat icon small
                            title="Send e-mail"
                            :disabled="userEmail == null || userEmail == undefined"
                            @click="sendEmail()">
                            <v-icon>email</v-icon>
                        </v-btn>
                      </td>
                    </tr>
                </tbody>
              </table>
            </div>
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
import { SEARCH_CHEPEAST_RENT, SEND_EMAIL } from '@/store/actions.type'

export default {
  data () {
    return {
      alert: false,
      userEmail: null,
      subject: 'AWS SES E-MAIL - CAR RENT',
      carRent: {
        id: null,
        modelComplete: '',
        sumPrice: null
      },
      carRentSearch: {
        dateFrom: '',
        dateTo: '',
        loyalty: null
      },
      menu1: false,
      menu2: false,
      valid: true,
      dateFromRules: [
        v => !!v || 'DateFrom is required',
        v => (v && v.length <= 20) || 'A quantidade de caracteres máximos para o nome é de 20'
      ],
      dateToRules: [
        v => !!v || 'DateTo is required',
        v => (v && v.length <= 20) || 'A quantidade de caracteres máximos para o nome é de 20'
      ],
      rules: {
        email: value => {
          if (!value) {
            return true
          }
          const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
          return pattern.test(value) || 'Invalid e-mail.'
        }
      },
      message: '',
      showAlert: false,
      typeMessage: 'success',
      listCars: []
    }
  },
  methods: {
    search () {
      if (this.$refs.form.validate()) {
        let load = this.$loading.show()
        this.listCars = []
        this.$store.dispatch(SEARCH_CHEPEAST_RENT, this.carRentSearch).then(action => {
          load.hide()
          if (action.id === null || action.id === undefined) {
            this.showAlertMessage('No Car found in database', 'info')
          } else {
            this.carRent = action
            this.listCars.push(this.carRent)
          }
        }).catch((error) => {
          load.hide()
          if (error.response.data !== undefined && error.response.data.errors !== undefined) {
            this.showAlertMessage(error.response.data.errors[0].defaultMessage, 'error')
            throw new Error(`[RWV] carRentService ${error.response.data.errors[0].defaultMessage}`)
          } else {
            this.showAlertMessage(error.toString(), 'error')
            throw new Error(`[RWV] carRentService ${error}`)
          }
        })
      }
    },
    sendEmail () {
      if (this.$refs.form.validate()) {
        let payload = {
          email: this.userEmail,
          subject: this.subject,
          content: this.getContent()
        }
        let load = this.$loading.show()
        this.$store.dispatch(SEND_EMAIL, payload).then(action => {
          this.showAlertMessage('Email sent succesfully!', 'info')
          load.hide()
        }).catch((error) => {
          load.hide()
          if (error.response.data !== null && error.response.data.errors !== undefined) {
            this.showAlertMessage(error.response.data.errors[0].defaultMessage, 'warning')
            throw new Error(`[RWV] carRentService ${error.response.data.errors[0].defaultMessage}`)
          } else {
            this.showAlertMessage(error.toString(), 'error')
            throw new Error(`[RWV] carRentService ${error}`)
          }
        })
      }
    },
    clear () {
      this.$refs.form.reset()
      this.listCars = []
      this.carRent = null
    },
    getContent () {
      var content = '<h1>Rental Car Information</h1>'
      content = content.concat('<h2>This is the cheapest</h2>')
      content = content.concat('<table><tr><th>Model</th><th>Price</th></tr>')
      content = content.concat(`<tr><td style="text-align: left;">${this.carRent.modelComplete}</td><td style="text-align: center;">${this.carRent.sumPrice}</td></tr>`)
      return content
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
