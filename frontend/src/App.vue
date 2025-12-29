<template>
  <el-container style="min-height: 100vh">
    <el-header>
      <div class="logo">在线编程学习系统</div>
      <el-menu mode="horizontal" :router="true" background-color="#409EFF" text-color="#fff">
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/courses">课程</el-menu-item>
        <el-menu-item index="/problems">题库</el-menu-item>
        <el-menu-item index="/community">社区</el-menu-item>
        <el-menu-item index="/dashboard">我的学习</el-menu-item>
        <el-menu-item index="/admin">后台</el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view />
    </el-main>
  </el-container>
</template>

<style scoped>
.logo {
  color: #fff;
  font-size: 18px;
  margin-bottom: 8px;
}
</style>
