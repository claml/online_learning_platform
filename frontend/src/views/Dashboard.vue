<template>
  <el-card>
    <h2>我的学习</h2>
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in stats" :key="item.label">
        <el-card>
          <div>{{ item.label }}</div>
          <h3>{{ item.value }}</h3>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import api from '../api'

const stats = ref<{ label: string; value: any }[]>([])

const load = async () => {
  const res = await api.get('/stats')
  const data = res.data
  stats.value = [
    { label: '学习课程数', value: data.totalEnrollments },
    { label: '提交次数', value: data.totalSubmissions },
    { label: '课程总数', value: data.courseCount },
    { label: '学员人数', value: data.studentCount }
  ]
}

onMounted(load)
</script>
