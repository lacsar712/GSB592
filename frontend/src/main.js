import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import { ElMessage } from 'element-plus'
import App from './App.vue'
import router from './router'

const app = createApp(App)

// 全局错误处理
app.config.errorHandler = (err, instance, info) => {
  console.error('全局错误捕获:', err, info)
  ElMessage.error('系统错误，请刷新页面重试')
}

// 全局警告处理
app.config.warnHandler = (msg, instance, trace) => {
  console.warn('警告:', msg, trace)
}

app.use(ElementPlus, { locale: zhCn })
app.use(router)
app.mount('#app')
