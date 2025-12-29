<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="16">
        <video v-if="currentLesson" controls style="width: 100%" :src="currentLesson.videoUrl"></video>
        <p v-if="progress">已观看 {{ progress }}%</p>
      </el-col>
      <el-col :span="8">
        <el-card>
          <h3>课时目录</h3>
          <el-menu @select="selectLesson">
            <el-menu-item v-for="lesson in lessons" :index="lesson.id + ''" :key="lesson.id">
              {{ lesson.title }}
            </el-menu-item>
          </el-menu>
        </el-card>
        <el-card style="margin-top: 16px">
          <h3>练习题</h3>
          <el-button v-for="p in problems" :key="p.id" type="text" @click="$router.push('/problems/' + p.id)">{{ p.title }}</el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import api from '../api'

const route = useRoute()
const lessons = ref<any[]>([])
const problems = ref<any[]>([])
const currentLesson = ref<any>(null)
const progress = ref(0)

const load = async () => {
  const id = route.params.id
  lessons.value = (await api.get(`/courses/${id}/lessons`)).data
  problems.value = (await api.get('/problems', { params: { courseId: id } })).data
  currentLesson.value = lessons.value[0]
}

const selectLesson = (index: string) => {
  currentLesson.value = lessons.value.find((l: any) => l.id + '' === index)
  progress.value = Math.min(100, progress.value + 10)
}

onMounted(load)
</script>
