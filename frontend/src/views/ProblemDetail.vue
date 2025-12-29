<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <el-card v-if="problem">
        <h2>{{ problem.title }}</h2>
        <p>{{ problem.description }}</p>
        <p>示例输入：{{ problem.sampleInput }}</p>
        <p>示例输出：{{ problem.sampleOutput }}</p>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card>
        <el-select v-model="language" style="width: 120px">
          <el-option label="Python" value="python" />
          <el-option label="Java" value="java" />
          <el-option label="C" value="c" />
          <el-option label="C++" value="cpp" />
        </el-select>
        <div ref="editorRef" style="height: 300px; border: 1px solid #eee"></div>
        <el-input type="textarea" v-model="stdin" placeholder="输入" />
        <el-button type="primary" @click="run">运行</el-button>
        <el-button @click="save">保存提交</el-button>
        <div v-if="output">
          <h4>输出</h4>
          <pre>{{ output }}</pre>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import * as monaco from 'monaco-editor'
import { useRoute } from 'vue-router'
import api from '../api'

const route = useRoute()
const problem = ref<any>(null)
const editorRef = ref<HTMLDivElement | null>(null)
const editor = ref<monaco.editor.IStandaloneCodeEditor | null>(null)
const language = ref('python')
const stdin = ref('')
const output = ref('')

onMounted(async () => {
  const res = await api.get(`/problems/${route.params.id}`)
  problem.value = res.data
  editor.value = monaco.editor.create(editorRef.value as HTMLDivElement, {
    value: '# 在此编写代码',
    language: language.value,
    automaticLayout: true
  })
})

const run = async () => {
  const res = await api.post('/execute', { language: language.value, code: editor.value?.getValue(), stdin: stdin.value })
  output.value = res.data.stdout || res.data.stderr
}

const save = async () => {
  await api.post(`/problems/${route.params.id}/submit`, {
    language: language.value,
    code: editor.value?.getValue(),
    stdout: output.value,
    stderr: '',
    status: 'SAVED'
  })
}
</script>
