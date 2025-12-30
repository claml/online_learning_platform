<template>
  <el-row :gutter="20">
    <el-col :span="16">
      <el-card>
        <h2>学习社区</h2>
        <el-radio-group v-model="order">
          <el-radio-button label="latest">最新</el-radio-button>
          <el-radio-button label="hot">最热</el-radio-button>
        </el-radio-group>
        <el-timeline style="margin-top: 16px">
          <el-timeline-item v-for="note in notes" :key="note.id" :timestamp="note.createdAt">
            <strong>{{ note.user?.username || '匿名' }}</strong>: {{ note.content }}
            <div>
              <el-button size="small" @click="like(note)">👍 {{ note.likes }}</el-button>
            </div>
          </el-timeline-item>
        </el-timeline>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card>
        <h3>发布笔记</h3>
        <el-input type="textarea" v-model="content" />
        <el-checkbox v-model="published">公开到社区</el-checkbox>
        <el-button type="primary" @click="publish">发布</el-button>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import api from '../api'

const notes = ref<any[]>([])
const content = ref('')
const published = ref(true)
const order = ref('latest')

const load = async () => {
  notes.value = (await api.get('/community/notes', { params: { orderBy: order.value } })).data
}

watch(order, load)
onMounted(load)

const publish = async () => {
  await api.post('/community/notes', { content: content.value, published: published.value })
  content.value = ''
  load()
}

const like = async (note: any) => {
  await api.post(`/community/notes/${note.id}/like`)
  load()
}
</script>
