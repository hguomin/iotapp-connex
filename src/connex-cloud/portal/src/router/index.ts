import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      //component: HomeView
      component: () => import('../views/EdgeGatewaysView.vue')
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import('../views/Dashboard.vue')
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path:'/edge-gateways',
      name: 'edge-gateways',
      component: () => import('../views/EdgeGatewaysView.vue'),
      children: [
        {
          // When "/edge-gateways/add" matchs
          path: 'add',
          component: () => import('../views/edge-gateways/AddView.vue'),
        },
        {
          path: ':id',
          component: () => import('../views/edge-gateways/HomeView.vue'),
          children: [
            {
              path: 'overview',
              component: () => import('../views/edge-gateways/OverviewView.vue')
            },
            {
              path: 'data-sources/list',
              component: () => import('../views/edge-gateways/DataSourcesView.vue')
            },
            {
              path: 'data-sources/opc-ua/:connector/:dataSrcName',
              component: () => import('../views/edge-gateways/DataSourceDetailView.vue')
            },
            {
              path: 'data-sources/modbus/:connector/:dataSrcName',
              component: () => import('../views/edge-gateways/DataSourceModbusDetailView.vue')
            },
            {
              path: 'telemetries',
              component: () => import('../views/edge-gateways/TelemetryView.vue')
            },
            {
              path: 'configuration',
              component: () => import('../views/edge-gateways/ConfigurationView.vue')
            },
            {
              path: 'actions',
              component: () => import('../views/edge-gateways/ActionsView.vue')
            },
            {
              path: 'logs',
              component: () => import('../views/edge-gateways/LogsView.vue')
            },
          ]
        }
      ]
    }
  ]
})

export default router
