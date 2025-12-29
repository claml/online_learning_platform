<template>
  <div>
    <el-card>
      <h2>发现课程</h2>
      <el-input v-model="keyword" placeholder="搜索课程" style="width: 200px" />
      <el-select v-model="language" placeholder="语言" style="width: 150px">
        <el-option label="全部" value="" />
        <el-option label="Java" value="Java" />
        <el-option label="Python" value="Python" />
      </el-select>
      <el-button type="primary" @click="loadCourses">搜索</el-button>
    </el-card>
    <el-row :gutter="20" style="margin-top: 16px">
      <el-col :span="6" v-for="course in courses" :key="course.id">
        <el-card @click="$router.push('/courses/' + course.id)" class="course-card">
          <img :src="course.coverImage" style="width: 100%" />
          <h3>{{ course.title }}</h3>
          <p>{{ course.description }}</p>
          <div>语言：{{ course.language }} · 难度：{{ course.difficulty }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import api from '../api'

const courses = ref<any[]>([])
const keyword = ref('')
const language = ref('')

const loadCourses = async () => {
  const res = await api.get('/courses', { params: { keyword: keyword.value, language: language.value } })
  courses.value = res.data.content || []
}

onMounted(loadCourses)
</script>

<style scoped>
.course-card {
  cursor: pointer;
  margin-bottom: 16px;
}
</style>
