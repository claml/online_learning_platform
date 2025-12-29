<template>
  <el-card>
    <h2>课程列表</h2>
    <el-table :data="courses">
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="language" label="语言" />
      <el-table-column prop="difficulty" label="难度" />
      <el-table-column>
        <template #default="scope">
          <el-button size="small" @click="$router.push('/courses/' + scope.row.id)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import api from '../api'

const courses = ref<any[]>([])
const load = async () => {
  const res = await api.get('/courses')
  courses.value = res.data.content
}

onMounted(load)
</script>
