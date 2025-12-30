<template>
  <el-row justify="center" style="margin-top: 80px">
    <el-col :span="8">
      <el-card>
        <h2 style="margin-bottom: 20px">登录</h2>
        <el-form label-position="top" @submit.prevent>
          <el-form-item label="用户名">
            <el-input v-model="username" autocomplete="username" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="password" type="password" autocomplete="current-password" />
          </el-form-item>
          <el-space>
            <el-button type="primary" @click="handleLogin" :loading="loading">登录</el-button>
            <el-button type="text" @click="goRegister">还没有账号？去注册</el-button>
          </el-space>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
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
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api/auth'
import api from '../api'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/auth'

const username = ref('')
const password = ref('')
const loading = ref(false)
const router = useRouter()
const authStore = useAuthStore()

const handleLogin = async () => {
  if (!username.value || !password.value) {
    ElMessage.error('请输入用户名和密码')
    return
  }
  loading.value = true
  try {
    const res = await login(username.value, password.value)
    const { token, role } = res.data
    authStore.setAuth(token, role, username.value)
    ElMessage.success('登录成功')
    router.push('/')
  } catch (e: any) {
    ElMessage.error(e.response?.data?.message || '登录失败')
  } finally {
    loading.value = false
  }
}

const goRegister = () => {
  router.push('/register')
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
