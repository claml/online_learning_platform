import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import CourseList from '../views/CourseList.vue'
import CourseDetail from '../views/CourseDetail.vue'
import Learning from '../views/Learning.vue'
import ProblemList from '../views/ProblemList.vue'
import ProblemDetail from '../views/ProblemDetail.vue'
import Community from '../views/Community.vue'
import Dashboard from '../views/Dashboard.vue'
import Admin from '../views/Admin.vue'
import Register from '../views/Register.vue'
import { useAuthStore } from '../store/auth'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/courses', component: CourseList },
    { path: '/courses/:id', component: CourseDetail, props: true },
    { path: '/learn/:id', component: Learning, props: true, meta: { requiresAuth: true } },
    { path: '/problems', component: ProblemList },
    { path: '/problems/:id', component: ProblemDetail, props: true, meta: { requiresAuth: true } },
    { path: '/community', component: Community, meta: { requiresAuth: true } },
    { path: '/dashboard', component: Dashboard, meta: { requiresAuth: true } },
    { path: '/admin', component: Admin, meta: { requiresAuth: true } }
  ]
})

router.beforeEach((to, _from, next) => {
  const auth = useAuthStore()
  const isAuthenticated = !!auth.token
  if ((to.path === '/login' || to.path === '/register') && isAuthenticated) {
    next('/')
    return
  }
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login')
    return
  }
  next()
})

export default router
