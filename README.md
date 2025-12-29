# 在线编程学习系统

本项目包含 Spring Boot 3 + Vue 3 的前后端分离在线编程学习平台，满足课程、在线编译、题目练习、学习社区等毕业设计展示需求。

## 后端运行
1. 安装并启动 MySQL 8，创建数据库 `online_learning`。
2. 配置 `backend/src/main/resources/application.properties` 中的数据库账号密码。
3. 进入 backend 目录：
   ```bash
   mvn clean spring-boot:run
   ```
4. 启动后访问 Swagger UI: `http://localhost:8080/swagger-ui.html`。

默认账户：
- admin / password
- instructor / password
- student / password

## 前端运行
1. 进入 frontend 目录，安装依赖并启动：
   ```bash
   npm install
   npm run dev
   ```
2. 浏览器访问 Vite 提示的地址即可（默认 http://localhost:5173）。

## Docker 沙箱前置要求
- 本地需安装 Docker，当前实现会拉取 `python:3.11-slim`、`eclipse-temurin:17-jdk`、`gcc:13` 等镜像。
- 代码执行接口：`POST /api/execute`，自动限制 CPU(0.5 核)、内存(256MB)、禁网、超时与输出长度，执行完毕后自动清理临时目录与容器。

## 初始化数据
- `data.sql` 提供课程、课时、题目与默认用户示例，可直接演示首页、课程详情、做题、社区等模块。

## 主要功能
- 课程管理：讲师创建课程/课时/题目，支持按语言、领域、难度筛选。
- 在线学习：学员加入学习课程、查看目录、记录进度。
- 在线编译：Docker 沙箱运行 Python/Java/C/C++ 代码。
- 题目练习：在线编辑器（Monaco）写题、运行并保存提交记录。
- 学习社区：发布笔记、点赞与评论。
- 统计看板：展示课程、用户与提交等基础数据。
