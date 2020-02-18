import Vue from 'vue'
import Router from 'vue-router'
import Workspace from '@/components/Workspace'

Vue.use(Router)

export const routes = [
  {
    path: '/',
    name: 'Default',
    redirect: {
      name: 'Workspace'
    }
  },
  {
    path: '/workspace',
    name: 'Workspace',
    component: Workspace
  }
]
