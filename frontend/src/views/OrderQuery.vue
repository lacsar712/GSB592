<template>
  <div class="order-query-container">
    <div class="query-wrapper">
      <el-card class="query-card">
        <template #header>
          <div class="card-header">
            <h2>订单查询</h2>
          </div>
        </template>

        <el-form ref="queryFormRef" :model="queryForm" :rules="queryRules" label-width="100px">
          <el-form-item label="订单号" prop="orderNo">
            <el-input
              v-model="queryForm.orderNo"
              placeholder="请输入订单号"
              clearable>
              <template #append>
                <el-button type="primary" @click="queryOrder" :loading="loading">查询</el-button>
              </template>
            </el-input>
          </el-form-item>
        </el-form>

        <div v-if="orderData" class="order-result">
          <el-divider>订单详情</el-divider>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="订单号">
              <span class="order-no">{{ orderData.orderNo }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="服务类型">
              {{ orderData.serviceName }}
            </el-descriptions-item>
            <el-descriptions-item label="订单详情">
              {{ orderData.detail }}
            </el-descriptions-item>
            <el-descriptions-item label="总价">
              <span class="total-price">¥{{ orderData.totalPrice }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="状态">
              <el-tag type="success">{{ orderData.status }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="创建时间">
              {{ orderData.createTime }}
            </el-descriptions-item>
          </el-descriptions>
        </div>

        <div class="actions">
          <el-button @click="goHome">返回首页</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '../utils/request'

export default {
  name: 'OrderQuery',
  setup() {
    const router = useRouter()
    const loading = ref(false)
    const queryFormRef = ref(null)
    const orderData = ref(null)

    const queryForm = reactive({
      orderNo: ''
    })

    const queryRules = {
      orderNo: [{ required: true, message: '请输入订单号', trigger: 'blur' }]
    }

    const queryOrder = async () => {
      if (!queryFormRef.value) return
      await queryFormRef.value.validate(async (valid) => {
        if (valid) {
          loading.value = true
          orderData.value = null
          try {
            const res = await request.get('/huangshan/order/query', {
              params: { orderNo: queryForm.orderNo }
            })
            orderData.value = res.data
            ElMessage.success('查询成功')
          } catch (error) {
            console.error(error)
          } finally {
            loading.value = false
          }
        }
      })
    }

    const goHome = () => {
      router.push('/')
    }

    return {
      loading,
      queryFormRef,
      queryForm,
      queryRules,
      orderData,
      queryOrder,
      goHome
    }
  }
}
</script>

<style scoped>
.order-query-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.query-wrapper {
  width: 100%;
  max-width: 700px;
}

.query-card {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.card-header h2 {
  margin: 0;
  color: #333;
  text-align: center;
}

.order-result {
  margin-top: 20px;
}

.order-no {
  font-weight: bold;
  color: #409EFF;
  font-size: 16px;
}

.total-price {
  font-weight: bold;
  color: #F56C6C;
  font-size: 20px;
}

.actions {
  margin-top: 30px;
  text-align: center;
}
</style>
