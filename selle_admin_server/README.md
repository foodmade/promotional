# Seller66后台管理系统API服务

基于SpringBoot + MyBatis + MySQL + JWT的后台管理系统API服务

## 项目结构

```
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─seller66
│  │  │          └─admin
│  │  │              ├─common       # 通用类
│  │  │              │  └─api       # API相关类
│  │  │              ├─config       # 配置类
│  │  │              ├─controller   # 控制器
│  │  │              ├─entity       # 实体类
│  │  │              ├─mapper       # MyBatis Mapper
│  │  │              ├─security     # 安全相关类
│  │  │              ├─service      # 服务接口
│  │  │              │  └─impl      # 服务实现
│  │  │              └─utils        # 工具类
│  │  └─resources
│  │      ├─db                  # 数据库脚本
│  │      └─mapper              # MyBatis XML映射文件
```

## 技术栈

- SpringBoot 2.7.13
- Spring Security
- MyBatis
- MySQL
- JWT
- Druid

## 特性

- RESTful API
- JWT认证和授权
- 基于角色的权限控制

## 接口说明

### 认证接口

- 用户注册: `POST /api/auth/register`
- 用户登录: `POST /api/auth/login`

### 用户接口

- 获取用户信息: `GET /api/user/info`

## 快速开始

### 1. 创建数据库

执行`src/main/resources/db/seller66_admin.sql`脚本创建数据库和表

### 2. 修改配置

根据本地环境修改`application.yml`中的数据库配置

### 3. 编译运行

```bash
mvn clean package
java -jar target/seller66-admin-server-0.0.1-SNAPSHOT.jar
```

## 初始用户

- 用户名: admin
- 密码: 123456 