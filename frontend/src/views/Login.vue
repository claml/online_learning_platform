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
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api/auth'
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
}
</script>
