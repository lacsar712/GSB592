import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import ServiceForm from '../views/ServiceForm.vue'
import Result from '../views/Result.vue'
import OrderQuery from '../views/OrderQuery.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/service/:type',
    name: 'ServiceForm',
    component: ServiceForm
  },
  {
    path: '/result',
    name: 'Result',
    component: Result
  },
  {
    path: '/order-query',
    name: 'OrderQuery',
    component: OrderQuery
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
