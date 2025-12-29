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

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/courses', component: CourseList },
    { path: '/courses/:id', component: CourseDetail, props: true },
    { path: '/learn/:id', component: Learning, props: true },
    { path: '/problems', component: ProblemList },
    { path: '/problems/:id', component: ProblemDetail, props: true },
    { path: '/community', component: Community },
    { path: '/dashboard', component: Dashboard },
    { path: '/admin', component: Admin }
  ]
})

export default router
