<template>
  <div class="home-container">
    <!-- Banner Carousel -->
    <div class="banner-section">
      <el-carousel :interval="5000" height="500px" arrow="always">
        <el-carousel-item v-for="(image, index) in bannerImages" :key="index">
          <div class="banner-item" :style="{ backgroundImage: `url(${image.url})` }">
            <div class="banner-overlay">
              <h2 class="banner-title">{{ image.title }}</h2>
              <p class="banner-desc">{{ image.description }}</p>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- Main Content -->
    <div class="main-content">
      <div class="content">
        <h1 class="title">黄山旅游服务平台</h1>
        <p class="subtitle">欢迎来到黄山，体验壮美山河</p>

        <div class="service-buttons">
          <el-button
            type="primary"
            size="large"
            @click="goToService('ticket')"
            class="service-btn">
            <el-icon><Ticket /></el-icon>
            <span>门票预订</span>
          </el-button>

          <el-button
            type="success"
            size="large"
            @click="goToService('hotel')"
            class="service-btn">
            <el-icon><House /></el-icon>
            <span>酒店预约</span>
          </el-button>

          <el-button
            type="warning"
            size="large"
            @click="goToService('cableCar')"
            class="service-btn">
            <el-icon><Connection /></el-icon>
            <span>索道购票</span>
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Ticket, House, Connection } from '@element-plus/icons-vue'

export default {
  name: 'Home',
  components: {
    Ticket,
    House,
    Connection
  },
  data() {
    return {
      bannerImages: [
        {
          url: '/images/huangshan1.jpg',
          title: '迎客松',
          description: '黄山标志性景观，千年古松迎接八方来客'
        },
        {
          url: '/images/huangshan2.jpg',
          title: '云海奇观',
          description: '黄山四绝之一，云雾缭绕如临仙境'
        },
        {
          url: '/images/huangshan3.jpg',
          title: '日出东方',
          description: '光明顶观日出，感受大自然的壮美'
        },
        {
          url: '/images/huangshan4.jpg',
          title: '奇峰怪石',
          description: '黄山奇峰七十二，怪石遍布山间'
        }
      ]
    }
  },
  methods: {
    goToService(type) {
      this.$router.push(`/service/${type}`)
    }
  }
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background: #f5f5f5;
}

/* Banner Section */
.banner-section {
  width: 100%;
  height: 500px;
}

.banner-item {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  position: relative;
}

.banner-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.7), transparent);
  padding: 40px;
  color: white;
}

.banner-title {
  font-size: 36px;
  font-weight: bold;
  margin-bottom: 10px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
}

.banner-desc {
  font-size: 18px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.8);
}

/* Main Content Section */
.main-content {
  padding: 80px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  min-height: calc(100vh - 500px);
  display: flex;
  align-items: center;
  justify-content: center;
}

.content {
  text-align: center;
  color: white;
  padding: 40px;
  max-width: 1200px;
  margin: 0 auto;
}

.title {
  font-size: 48px;
  font-weight: bold;
  margin-bottom: 20px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

.subtitle {
  font-size: 24px;
  margin-bottom: 60px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
}

.service-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
  max-width: 800px;
  margin: 0 auto;
}

.service-btn {
  min-width: 160px;
  height: 60px;
  font-size: 18px;
  font-weight: bold;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
}

.service-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.4);
}

/* Carousel Arrow Styles */
:deep(.el-carousel__arrow) {
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
}

:deep(.el-carousel__arrow:hover) {
  background-color: rgba(0, 0, 0, 0.7);
}

/* Carousel Indicator Styles */
:deep(.el-carousel__indicator) {
  button {
    background-color: rgba(255, 255, 255, 0.5);
  }
}

:deep(.el-carousel__indicator.is-active) {
  button {
    background-color: white;
  }
}

/* Responsive Design */
@media (max-width: 768px) {
  .banner-section {
    height: 300px;
  }

  .banner-title {
    font-size: 24px;
  }

  .banner-desc {
    font-size: 14px;
  }

  .title {
    font-size: 32px;
  }

  .subtitle {
    font-size: 18px;
  }

  .service-btn {
    min-width: 140px;
    height: 50px;
    font-size: 16px;
  }
}
</style>
