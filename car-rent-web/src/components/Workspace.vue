<template>
  <div id="app">
  <v-app id="inspire">
    <v-navigation-drawer
      clipped
      fixed
      disable-resize-watcher
      stateless
      v-model="drawer"
      app
    >
      <v-list dense>
        <v-list-tile @click="changeComponent(carComponent)" ripple>
          <v-list-tile-action>
            <v-icon>directions_car</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{$i18n.t('messages.menu.carManager')}}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
        <v-list-tile @click="changeComponent(carRentComponent)" ripple>
          <v-list-tile-action>
            <v-icon>attach_money</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{$i18n.t('messages.menu.carRentManager')}}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar color="#1A405F" dark fixed app clipped-left>
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-toolbar-title>{{$i18n.t('messages.appName')}}</v-toolbar-title>
       <v-spacer></v-spacer>
      <!-- <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn> -->
    </v-toolbar>
     <main>
    </main>
    <v-content>
      <v-container id="containerFrame" fluid fill-height>
        <v-layout align-start>
         <v-flex xs12 d-flex fill-height>
            <component :is="component"></component>
         </v-flex>
        </v-layout>
      </v-container>
    </v-content>
    <v-footer color="#1A405F" app fixed>
      <span class="white--text">&copy;</span>
    </v-footer>
  </v-app>
</div>
</template>

<style>
main {
  padding-top: 10px !important;
}
</style>

<script>
import CarManager from '@/components/CarManager'
import CarRentManager from '@/components/CarRentManager'

export default {
  data: () => ({
    drawer: true,
    loadDefault: true,
    carComponent: CarManager,
    carRentComponent: CarRentManager,
    carRentActionComponent: null,
    component: null
  }),
  components: {
    CarManager
  },
  methods: {
    changeComponent (component) {
      this.component = component
    }
  },
  props: {
    source: String
  },
  mounted () {
    this.$root.$on('changeComponent', (component) => {
      this.changeComponent(component)
    })
  }
}
</script>
