<template>
  <div v-if="course">
    <el-card>
      <h2>{{ course.title }}</h2>
      <p>{{ course.description }}</p>
      <div>语言：{{ course.language }} · 难度：{{ course.difficulty }}</div>
      <el-button type="primary" @click="enroll">加入学习</el-button>
    </el-card>
    <el-card style="margin-top: 16px">
      <h3>课时</h3>
      <el-timeline>
        <el-timeline-item v-for="lesson in lessons" :key="lesson.id" :timestamp="'第' + lesson.lessonOrder + '课'">
          {{ lesson.title }}
        </el-timeline-item>
      </el-timeline>
    </el-card>
    <el-card style="margin-top: 16px">
      <h3>题目</h3>
      <el-list>
        <div v-for="p in problems" :key="p.id">
          <el-link @click="$router.push('/problems/' + p.id)">{{ p.title }}</el-link>
        </div>
      </el-list>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import api from '../api'

const route = useRoute()
const router = useRouter()
const course = ref<any>(null)
const lessons = ref<any[]>([])
const problems = ref<any[]>([])

const load = async () => {
  const id = route.params.id
  const res = await api.get(`/courses/${id}`)
  course.value = res.data
  const lessonRes = await api.get(`/courses/${id}/lessons`)
  lessons.value = lessonRes.data
  const problemRes = await api.get('/problems', { params: { courseId: id } })
  problems.value = problemRes.data
}

const enroll = async () => {
  await api.post(`/courses/${route.params.id}/enroll`)
  router.push(`/learn/${route.params.id}`)
}

onMounted(load)
</script>
