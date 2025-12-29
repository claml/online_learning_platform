<template>
  <el-card>
    <h2>题目列表</h2>
    <el-table :data="problems">
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="tags" label="标签" />
      <el-table-column>
        <template #default="scope">
          <el-button size="small" @click="$router.push('/problems/' + scope.row.id)">做题</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import api from '../api'

const problems = ref<any[]>([])
const load = async () => {
  problems.value = (await api.get('/problems')).data
}

onMounted(load)
</script>
