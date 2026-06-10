<template>
  <div class="service-form-container">
    <div class="form-wrapper">
      <el-card class="form-card">
        <template #header>
          <div class="card-header">
            <h2>{{ serviceTitle }}</h2>
          </div>
        </template>

        <!-- 门票表单 -->
        <el-form
          v-if="serviceType === 'ticket'"
          ref="ticketFormRef"
          :model="ticketForm"
          :rules="ticketRules"
          label-width="120px">
          <el-form-item label="游客类型" prop="visitorType">
            <el-select v-model="ticketForm.visitorType" placeholder="请选择游客类型" style="width: 100%">
              <el-option label="成人" value="adult" />
              <el-option label="学生" value="student" />
              <el-option label="老人（70岁以上）" value="senior" />
            </el-select>
          </el-form-item>

          <el-form-item label="游玩日期" prop="playDate">
            <el-date-picker
              v-model="ticketForm.playDate"
              type="date"
              placeholder="请选择游玩日期"
              style="width: 100%"
              :disabled-date="disabledDate"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD" />
          </el-form-item>

          <el-form-item label="购票数量" prop="quantity">
            <el-input-number v-model="ticketForm.quantity" :min="1" :max="99" style="width: 100%" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitTicketForm" :loading="loading">提交订单</el-button>
            <el-button @click="goBack">返回首页</el-button>
          </el-form-item>
        </el-form>

        <!-- 酒店表单 -->
        <el-form
          v-if="serviceType === 'hotel'"
          ref="hotelFormRef"
          :model="hotelForm"
          :rules="hotelRules"
          label-width="120px">
          <el-form-item label="酒店类型" prop="hotelType">
            <el-select v-model="hotelForm.hotelType" placeholder="请选择酒店类型" style="width: 100%">
              <el-option label="经济型" value="economy" />
              <el-option label="豪华型" value="luxury" />
            </el-select>
          </el-form-item>

          <el-form-item label="入住日期" prop="checkInDate">
            <el-date-picker
              v-model="hotelForm.checkInDate"
              type="date"
              placeholder="请选择入住日期"
              style="width: 100%"
              :disabled-date="disabledDate"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD" />
          </el-form-item>

          <el-form-item label="离店日期" prop="checkOutDate">
            <el-date-picker
              v-model="hotelForm.checkOutDate"
              type="date"
              placeholder="请选择离店日期"
              style="width: 100%"
              :disabled-date="disabledCheckOutDate"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD" />
          </el-form-item>

          <el-form-item label="房间数" prop="rooms">
            <el-input-number v-model="hotelForm.rooms" :min="1" :max="99" style="width: 100%" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitHotelForm" :loading="loading">提交订单</el-button>
            <el-button @click="goBack">返回首页</el-button>
          </el-form-item>
        </el-form>

        <!-- 索道表单 -->
        <el-form
          v-if="serviceType === 'cableCar'"
          ref="cableCarFormRef"
          :model="cableCarForm"
          :rules="cableCarRules"
          label-width="120px">
          <el-form-item label="索道类型" prop="cableType">
            <el-radio-group v-model="cableCarForm.cableType">
              <el-radio value="up">上行</el-radio>
              <el-radio value="down">下行</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="购票数量" prop="quantity">
            <el-input-number v-model="cableCarForm.quantity" :min="1" :max="99" style="width: 100%" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitCableCarForm" :loading="loading">提交订单</el-button>
            <el-button @click="goBack">返回首页</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '../utils/request'

export default {
  name: 'ServiceForm',
  setup() {
    const route = useRoute()
    const router = useRouter()
    const loading = ref(false)

    const serviceType = ref('')
    const serviceTitle = computed(() => {
      const titles = {
        ticket: '门票预订',
        hotel: '酒店预约',
        cableCar: '索道购票'
      }
      return titles[serviceType.value] || ''
    })

    // 门票表单
    const ticketFormRef = ref(null)
    const ticketForm = reactive({
      visitorType: '',
      playDate: '',
      quantity: 1
    })

    const ticketRules = {
      visitorType: [{ required: true, message: '请选择游客类型', trigger: 'change' }],
      playDate: [{ required: true, message: '请选择游玩日期', trigger: 'change' }],
      quantity: [{ required: true, message: '请输入购票数量', trigger: 'blur' }]
    }

    // 酒店表单
    const hotelFormRef = ref(null)
    const hotelForm = reactive({
      hotelType: '',
      checkInDate: '',
      checkOutDate: '',
      rooms: 1
    })

    const hotelRules = {
      hotelType: [{ required: true, message: '请选择酒店类型', trigger: 'change' }],
      checkInDate: [{ required: true, message: '请选择入住日期', trigger: 'change' }],
      checkOutDate: [
        { required: true, message: '请选择离店日期', trigger: 'change' },
        {
          validator: (rule, value, callback) => {
            if (value && hotelForm.checkInDate && value <= hotelForm.checkInDate) {
              callback(new Error('离店日期必须大于入住日期'))
            } else {
              callback()
            }
          },
          trigger: 'change'
        }
      ],
      rooms: [{ required: true, message: '请输入房间数', trigger: 'blur' }]
    }

    // 索道表单
    const cableCarFormRef = ref(null)
    const cableCarForm = reactive({
      cableType: 'up',
      quantity: 1
    })

    const cableCarRules = {
      cableType: [{ required: true, message: '请选择索道类型', trigger: 'change' }],
      quantity: [{ required: true, message: '请输入购票数量', trigger: 'blur' }]
    }

    // 禁用过去日期
    const disabledDate = (time) => {
      return time.getTime() < Date.now() - 8.64e7
    }

    // 禁用离店日期（必须大于入住日期）
    const disabledCheckOutDate = (time) => {
      if (!hotelForm.checkInDate) {
        return time.getTime() < Date.now() - 8.64e7
      }
      const checkInTime = new Date(hotelForm.checkInDate).getTime()
      return time.getTime() <= checkInTime
    }

    // 提交门票表单
    const submitTicketForm = async () => {
      if (!ticketFormRef.value) return
      await ticketFormRef.value.validate(async (valid) => {
        if (valid) {
          loading.value = true
          try {
            const res = await request.post('/huangshan/service', {
              serviceType: 'ticket',
              requestParam: ticketForm
            })
            ElMessage.success('订单提交成功')
            router.push({
              name: 'Result',
              state: { orderData: res.data }
            })
          } catch (error) {
            console.error(error)
          } finally {
            loading.value = false
          }
        }
      })
    }

    // 提交酒店表单
    const submitHotelForm = async () => {
      if (!hotelFormRef.value) return
      await hotelFormRef.value.validate(async (valid) => {
        if (valid) {
          loading.value = true
          try {
            const res = await request.post('/huangshan/service', {
              serviceType: 'hotel',
              requestParam: hotelForm
            })
            ElMessage.success('订单提交成功')
            router.push({
              name: 'Result',
              state: { orderData: res.data }
            })
          } catch (error) {
            console.error(error)
          } finally {
            loading.value = false
          }
        }
      })
    }

    // 提交索道表单
    const submitCableCarForm = async () => {
      if (!cableCarFormRef.value) return
      await cableCarFormRef.value.validate(async (valid) => {
        if (valid) {
          loading.value = true
          try {
            const res = await request.post('/huangshan/service', {
              serviceType: 'cableCar',
              requestParam: cableCarForm
            })
            ElMessage.success('订单提交成功')
            router.push({
              name: 'Result',
              state: { orderData: res.data }
            })
          } catch (error) {
            console.error(error)
          } finally {
            loading.value = false
          }
        }
      })
    }

    const goBack = () => {
      router.push('/')
    }

    onMounted(() => {
      serviceType.value = route.params.type
    })

    return {
      serviceType,
      serviceTitle,
      loading,
      ticketFormRef,
      ticketForm,
      ticketRules,
      hotelFormRef,
      hotelForm,
      hotelRules,
      cableCarFormRef,
      cableCarForm,
      cableCarRules,
      disabledDate,
      disabledCheckOutDate,
      submitTicketForm,
      submitHotelForm,
      submitCableCarForm,
      goBack
    }
  }
}
</script>

<style scoped>
.service-form-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.form-wrapper {
  width: 100%;
  max-width: 600px;
}

.form-card {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.card-header h2 {
  margin: 0;
  color: #333;
  text-align: center;
}
</style>
