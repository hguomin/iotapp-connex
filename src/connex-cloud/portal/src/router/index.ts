import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
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
              path: 'data-sources/list',
              component: () => import('../views/edge-gateways/DataSourcesView.vue')
            },
            {
              path: 'data-sources/:dataSrcName',
              component: () => import('../views/edge-gateways/DataSourceDetailView.vue')
            }
          ]
        }
      ]
    }
  ]
})

export default router
