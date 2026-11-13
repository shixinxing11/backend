# backend

实验室后端项目，当前完成「后端线 02：REST 与 MySQL 集成」。

## 技术栈

- Spring Boot 3.0.2 / Java 17
- Spring Web、Spring Data JPA、MySQL

## 运行前准备

1. 启动本机 MySQL80 服务。
2. 建库：`CREATE DATABASE lab DEFAULT CHARACTER SET utf8mb4;`
3. 在 `src/main/resources/application-local.properties` 中填写数据库密码：

   ```properties
   spring.datasource.password=你的密码
   ```

   该文件已被 `.gitignore` 排除，不会提交到仓库。

## 启动

用 IDEA 运行 `lab.backend.BackendApplication`，默认端口 8080。

## 接口

| 方法   | 路径                 | 说明               |
| ------ | -------------------- | ------------------ |
| GET    | /api/students        | 学生列表           |
| GET    | /api/students/{id}   | 按 id 查询         |
| POST   | /api/students        | 新增学生（JSON）   |
| PUT    | /api/students/{id}   | 更新学生（JSON）   |
| DELETE | /api/students/{id}   | 删除学生           |

## 分层

Controller → Service → Repository（Spring Data JPA）→ MySQL。

本接口组完全由我 + DeepSeek 协作完成。
