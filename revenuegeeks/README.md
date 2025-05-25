# RevenueGeeks

## 项目介绍
RevenueGeeks是一个电子商务知识中心网站，提供工具比较、优惠券和指南等内容，帮助用户选择适合其业务的电子商务解决方案。

该项目使用Vue.js + Element UI框架构建前端，Express.js提供后端API服务。支持多语言国际化(i18n)，响应式设计，适配各种设备屏幕。

## 技术栈
- 前端: Vue.js 2.x + Element UI
- 后端: Express.js
- HTTP客户端: Axios
- 国际化: Vue I18n
- 状态管理: Vuex
- 路由: Vue Router

## 安装指南

### 前提条件
- Node.js (v12.x 或以上)
- npm (v6.x 或以上)

### 安装依赖
```
npm install
```

### 启动开发服务器
```
# 前端开发服务器
npm run serve

# 后端API服务器
npm run backend
```

### 生产环境构建
```
npm run build
```

## 项目结构
```
revenuegeeks/
├── public/              # 静态资源
├── server/              # Express后端服务
│   └── index.js         # 主服务器文件
├── src/                 # 前端源代码
│   ├── api/             # API接口
│   ├── assets/          # 静态资源(图片等)
│   ├── components/      # Vue组件
│   ├── i18n/            # 国际化配置
│   │   └── locales/     # 语言文件
│   ├── router/          # 路由配置
│   ├── store/           # Vuex状态管理
│   ├── views/           # 页面视图
│   ├── App.vue          # 主应用组件
│   └── main.js          # 应用入口
├── .gitignore           # Git忽略文件
├── babel.config.js      # Babel配置
├── package.json         # 项目依赖
└── README.md            # 项目说明
```

## 功能特性
- 响应式设计，适配移动端和桌面端
- 多语言支持 (中文、英文)
- 品牌工具展示
- 文章列表和详情
- 团队成员展示
- 搜索功能
- 邮件订阅功能

## API接口
后端提供以下API接口:
- GET /api/brands - 获取所有品牌
- GET /api/brands/:id - 获取特定品牌
- GET /api/articles - 获取所有文章
- GET /api/articles/:id - 获取特定文章
- GET /api/articles/category/:tag - 获取特定类别的文章
- GET /api/team - 获取团队成员
- GET /api/search - 搜索功能
- POST /api/subscribe - 订阅功能

## 开发者
RevenueGeeks团队 