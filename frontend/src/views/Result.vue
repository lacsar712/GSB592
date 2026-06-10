<template>
  <div class="result-container">
    <div class="result-wrapper">
      <el-card class="result-card">
        <template #header>
          <div class="card-header">
            <el-icon :size="50" color="#67C23A"><SuccessFilled /></el-icon>
            <h2>预订成功</h2>
          </div>
        </template>

        <div class="result-content" v-if="orderData">
          <el-descriptions :column="1" border>
            <el-descriptions-item label="服务类型">
              <span class="service-type">{{ orderData.serviceName }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="订单号">
              <span class="order-no">{{ orderData.orderNo }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="订单明细">
              <div class="detail-content">{{ orderData.detail }}</div>
            </el-descriptions-item>
            <el-descriptions-item label="总价">
              <span class="total-price">¥{{ orderData.totalPrice }}</span>
            </el-descriptions-item>
          </el-descriptions>

          <div class="actions">
            <el-button type="primary" size="large" @click="goHome">返回首页</el-button>
          </div>
        </div>

        <div v-else class="no-data">
          <p>暂无订单数据</p>
          <el-button type="primary" @click="goHome">返回首页</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { SuccessFilled } from '@element-plus/icons-vue'

export default {
  name: 'Result',
  components: {
    SuccessFilled
  },
  setup() {
    const router = useRouter()
    const orderData = ref(null)

    onMounted(() => {
      orderData.value = history.state.orderData
    })

    const goHome = () => {
      router.push('/')
    }

    return {
      orderData,
      goHome
    }
  }
}
</script>

<style scoped>
.result-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.result-wrapper {
  width: 100%;
  max-width: 700px;
}

.result-card {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.card-header {
  text-align: center;
}

.card-header h2 {
  margin: 10px 0 0 0;
  color: #333;
}

.result-content {
  padding: 20px 0;
}

.service-type {
  font-size: 16px;
  color: #333;
  font-weight: 500;
}

.order-no {
  font-weight: bold;
  color: #409EFF;
  font-size: 16px;
  font-family: 'Courier New', monospace;
}

.detail-content {
  line-height: 1.8;
  color: #333;
  white-space: pre-line;
}

.total-price {
  font-weight: bold;
  color: #F56C6C;
  font-size: 24px;
}

.actions {
  margin-top: 30px;
  text-align: center;
}

.actions .el-button {
  min-width: 160px;
}

.no-data {
  text-align: center;
  padding: 40px 0;
}

.no-data p {
  font-size: 16px;
  color: #999;
  margin-bottom: 20px;
}
</style>
