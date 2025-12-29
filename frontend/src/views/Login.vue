<template>
  <el-card style="max-width: 400px; margin: 0 auto">
    <h2>登录 / 注册</h2>
    <el-form @submit.prevent>
      <el-form-item label="用户名">
        <el-input v-model="username" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" v-model="password" />
      </el-form-item>
      <el-form-item label="角色">
        <el-select v-model="role">
          <el-option label="学员" value="STUDENT" />
          <el-option label="讲师" value="INSTRUCTOR" />
          <el-option label="管理员" value="ADMIN" />
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="login">登录</el-button>
      <el-button @click="register">注册</el-button>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import api from '../api'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/auth'

const username = ref('')
const password = ref('')
const role = ref('STUDENT')
const router = useRouter()
const auth = useAuthStore()

const login = async () => {
  const res = await api.post('/auth/login', { username: username.value, password: password.value })
  auth.setAuth(res.data.token, res.data.role, username.value)
  router.push('/')
}

const register = async () => {
  const res = await api.post('/auth/register', { username: username.value, password: password.value, role: role.value })
  auth.setAuth(res.data.token, res.data.role, username.value)
  router.push('/')
}
</script>
