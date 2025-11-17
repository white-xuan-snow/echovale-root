# echovale - 音乐播放器与管理平台

`echovale` 是一个基于 Vue 3、TypeScript 和 Vite 构建的现代 Web 应用程序，主要使用 Vuetify 组件库提供美观且响应式的用户界面。本项目旨在提供一个音乐播放和管理平台。

## 技术栈

*   **前端框架**：Vue 3 (使用 Composition API 和 `<script setup>` 语法糖)
*   **构建工具**：Vite
*   **语言**：TypeScript
*   **UI 组件库**：Vuetify
*   **路由管理**：Vue Router
*   **状态管理**：Pinia
*   **代码风格**：ESLint (遵循 Airbnb 规范)
*   **图标库**：Font Awesome (通过 `plugins/fontawesome.ts` 集成)

## 项目结构

`src` 目录是项目的核心，其结构如下：

*   `assets/`：存放静态资源，如图片 (`love.jpg`, `me.jpg`, `vue.svg`)、字体 (`PingFang Heavy.ttf`) 和音乐文件 (`Fortnight.lrc`, `Fortnight.m4a`, `me.flac`, `WhatIsLove.flac`)。
*   `components/`：存放可复用的 Vue 组件，例如：
    *   `Bottom.vue`：底部组件，可能用于播放控制或导航。
    *   `Content.vue`：主要内容区域组件。
    *   `LeftTab.vue`：左侧边栏或导航组件。
    *   `Top.vue`：顶部组件，可能用于标题或用户操作。
*   `constant/`：存放常量定义，如 `system.ts` 和 `testResource.ts`。
*   `hooks/`：存放可复用的组合式函数 (Composition API)，例如 `initStoreHook.ts` 和 `utils.ts`。
*   `pages/`：存放应用的页面组件，例如：
    *   `Index.vue`：主页或仪表板。
    *   `Login.vue`：登录页面。
    *   `Player.vue`：音乐播放器页面。
*   `plugins/`：存放 Vue 插件和第三方库的配置，例如 `fontawesome.ts` (Font Awesome), `lyric.ts` (歌词处理), `vuetify.ts` (Vuetify 配置)。
*   `router/`：存放 Vue Router 的配置，`index.ts` 定义了应用的路由。
*   `stores/`：存放 Pinia 状态管理模块，例如：
    *   `playerStore.ts`：管理音乐播放状态。
    *   `themeStore.ts`：管理应用主题。
    *   `windowStore.ts`：管理窗口相关状态。
*   `style/`：存放全局样式文件，如 `common.css`。
*   `App.vue`：应用的根组件。
*   `main.ts`：应用的入口文件，负责初始化 Vue 实例、路由、Pinia 和 Vuetify。
*   `style.css`：全局样式文件。
*   `vite-env.d.ts`：Vite 相关的 TypeScript 类型声明。

## 主要功能 (推测)

*   **音乐播放**：支持播放本地音乐文件，可能包含歌词显示功能。
*   **用户界面**：基于 Vuetify 构建，提供响应式和现代化的设计。
*   **路由导航**：实现页面之间的切换，如主页、登录页和播放器页。
*   **状态管理**：通过 Pinia 集中管理应用状态，包括播放状态、主题设置和窗口行为。
*   **组件化**：高度模块化的组件设计，提高代码复用性和可维护性。

## 安装与运行

1.  **克隆仓库**：
    ```bash
    git clone https://github.com/white-xuan-snow/echovale-root.git
    cd echovale-root/echovale-vue
    ```
2.  **安装依赖**：
    ```bash
    npm install
    ```
3.  **运行开发服务器**：
    ```bash
    npm run dev
    ```
    应用将在本地开发服务器上运行，通常是 `http://localhost:5173`。

4.  **构建生产版本**：
    ```bash
    npm run build
    ```
    构建后的文件将输出到 `dist` 目录。

## 代码风格

本项目使用 ESLint 进行代码风格检查，并遵循 Airbnb 规范。建议在开发过程中配置 IDE 以自动格式化代码，确保代码风格的一致性。

## 贡献

欢迎对本项目提出建议和贡献。请遵循以下步骤：

1.  Fork 本仓库。
2.  创建新的功能分支 (`git checkout -b feature/YourFeature`)。
3.  提交您的更改 (`git commit -m 'Add some feature'`)。
4.  推送到分支 (`git push origin feature/YourFeature`)。
5.  创建 Pull Request。

## 许可证

[待定]
