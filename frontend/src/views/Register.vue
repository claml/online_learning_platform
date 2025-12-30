<template>
  <el-row justify="center" style="margin-top: 80px">
    <el-col :span="8">
      <el-card>
        <h2 style="margin-bottom: 20px">注册</h2>
        <el-form label-position="top" @submit.prevent>
          <el-form-item label="用户名">
            <el-input v-model="username" autocomplete="username" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="password" type="password" autocomplete="new-password" />
          </el-form-item>
          <el-space>
            <el-button type="primary" @click="handleRegister" :loading="loading">注册</el-button>
            <el-button type="text" @click="goLogin">已有账号？去登录</el-button>
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
import { register } from '../api/auth'

const username = ref('')
const password = ref('')
const loading = ref(false)
const router = useRouter()

const handleRegister = async () => {
  if (!username.value || !password.value) {
    ElMessage.error('请输入用户名和密码')
    return
  }
  loading.value = true
  try {
    await register(username.value, password.value)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } catch (e: any) {
    ElMessage.error(e.response?.data?.message || '注册失败')
  } finally {
    loading.value = false
  }
}

const goLogin = () => {
  router.push('/login')
}
</script>
