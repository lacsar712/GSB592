# 黄山旅游服务平台

基于 Vue + Spring Boot + MySQL 8 的前后端分离旅游服务平台，展示 Java 多态特性处理三类核心服务。

## 原始需求

> 基于Vue+Java的黄山旅游服务平台
> 题目背景
> 随着黄山旅游数字化发展，需开发一款前后端分离的旅游服务平台：前端用Vue实现用户交互，后端用Java多态特性处理门票预订、酒店预约、索道购票三类核心服务，最终需录制操作视频展示完整功能流程，验证项目可用性。
> 一、核心需求（分前后端 + 视频录制）
> （一）后端开发
> l 抽象父类设计
> 定义抽象类HuangshanService，包含：​抽象方法ServiceResponse process(ServiceRequest request)：接收服务请求参数（如游客类型、日期、数量），返回处理结果（含订单号、价格、状态）；​成员变量serviceId（服务标识）、serviceName（服务名称），提供带参构造方法初始化。​
> l 子类实现（3 个核心服务）​
> TicketService（门票服务）：​
> 重写process：根据请求参数（游客类型：成人 / 学生 / 老人、日期、购票数量）计算价格（成人 150 元 / 学生 75 元 / 70 岁 + 免票），返回含总价、订单号的结果；​
> HotelService（酒店服务）：​
> 重写process：根据请求参数（酒店类型：经济型 / 豪华型、入住 / 离店日期、房间数）计算价格（经济型 300 元 / 晚、豪华型 800 元 / 晚），返回含总价、订单号的结果；​
> CableCarService（索道服务）：​
> 重写process：根据请求参数（索道类型：上行 / 下行、购票数量）计算价格（上行 80 元 / 下行 70 元），返回含总价、订单号的结果。​
> 后端接口开发
> 基于 Spring Boot 提供 RESTful 接口/api/huangshan/service，接收前端传递的serviceType（服务类型：ticket/hotel/cableCar）和requestParam（服务参数 JSON），根据serviceType实例化对应子类（父类引用指向子类对象），调用process方法处理，返回 JSON 格式的ServiceResponse。
> （二）前端开发（Vue 实现）
> 核心页面组件
> l 首页（Home.vue）：展示黄山景区 banner 图，提供 “门票预订”“酒店预约”“索道购票” 三个服务入口按钮，点击按钮跳转至对应服务表单页；
> l 服务表单页（ServiceForm.vue）：根据 URL 参数（服务类型）动态渲染表单：
> l 门票表单：包含 “游客类型（下拉选择）”“游玩日期（日期选择器）”“购票数量（数字输入框）”；
> l 酒店表单：包含 “酒店类型（下拉选择）”“入住日期”“离店日期”“房间数（数字输入框）”；
> l 索道表单：包含 “索道类型（上行 / 下行，单选）”“购票数量（数字输入框）”；
> l 表单需做基础校验（如日期不能选过去时间、数量≥1）；
> l 结果展示页（Result.vue）：接收后端返回的ServiceResponse，展示 “服务类型”“订单号”“明细（如‘成人票 2 张：300 元’）”“总价”，提供 “返回首页” 按钮。​
> 前后端交互
> 使用 Axios 封装请求工具（request.js），在表单提交时调用后端/api/huangshan/service接口，传递serviceType和表单参数，接收响应后跳转至Result.vue展示结果。
> 学生需录制时长 5-8 分钟的操作视频，需包含以下 4 个核心环节，画面需清晰展示 “操作步骤 + 页面效果 + 后端接口反馈”：​
> 环境启动演示（1 分钟）：​
> 展示启动后端 Spring Boot 项目（控制台无报错）、启动 Vue 项目（终端显示Compiled successfully）；​
> 功能流程演示（3-5 分钟）：​
> l 从首页进入 “门票预订”，填写表单（如 “学生票 1 张，日期选次日”），提交后展示正确订单结果（总价 75 元 + 订单号）；​
> l 返回首页进入 “酒店预约”，填写表单（如 “豪华型 1 间，入住 3 晚”），提交后展示正确订单结果（总价 2400 元 + 订单号）；​
> l 返回首页进入 “索道购票”，填写表单（如 “上行 2 张”），提交后展示正确订单结果（总价 160 元 + 订单号）；​
> 异常场景测试（1 分钟）：​
> l 演示 1 个异常场景（如门票表单 “数量填 0”，触发前端校验提示；或选择 “过去日期”，提交后后端返回 “日期无效” 提示）；​
> l 项目结构说明（1 分钟）：​
> l 快速展示后端项目结构（抽象类、子类、接口类所在位置）、前端项目结构（核心组件、请求工具所在位置），说明 “多态如何体现”（如后端通过serviceType实例化不同子类，前端统一调用同一接口）。​
> l 视频需带清晰配音，讲解每一步操作的目的；画面需同时展示 “操作窗口（如浏览器）” 和 “必要的项目文件（如后端子类代码、前端组件代码）”，可使用分屏工具。​
> 二、考察要点​
> l 后端多态设计：抽象类与子类的继承关系、方法重写的正确性，能否通过父类引用统一处理不同服务；​
> l 前端 Vue 开发：组件间路由跳转、动态表单渲染、Axios 请求封装、表单校验逻辑；​
> l 前后端联调：能否正确传递参数、处理响应，解决跨域（若有）、数据格式不匹配等问题；​
> l 视频演示能力：能否清晰展示项目功能流程、异常处理、核心设计思路，体现对项目的整体理解。​ 给我生成一个完整的系统 订单查询 数据库的相关配置要弄好 用户名root 密码是123456 保证前后端api联调正确 然后随便找个涂色图片为背景 做主页 详细阅读我的要求 要符合

## 技术栈

- **前端**: Vue 3 + Element Plus + Vite
- **后端**: Spring Boot 3 + JPA + MySQL 8
- **容器化**: Docker + Docker Compose

## 功能特性

### 三类核心服务（多态实现）

1. **门票预订** - 支持成人票、学生票、老人票
2. **酒店预约** - 支持经济型、豪华型酒店
3. **索道购票** - 支持上行、下行索道

### 其他功能

- 订单查询（按订单号查询）
- 完整的表单验证
- 统一的错误处理
- 中文友好界面

## 多态体现

后端使用抽象类 `HuangshanService` 作为父类，三个子类 `TicketService`、`HotelService`、`CableCarService` 继承并实现 `process()` 方法。

Controller 根据 `serviceType` 创建不同的子类实例，使用父类引用统一调用 `process()` 方法，体现了 Java 的多态特性。

## 快速启动

### 前置要求

- Docker
- Docker Compose

### 启动步骤

1. 克隆项目到本地

2. 在项目根目录执行：

```bash
docker-compose up --build
```

3. 等待所有服务启动完成（首次启动需要下载依赖，约 5-10 分钟）

4. 访问应用：

- 前端页面: http://localhost:3000
- 后端 API: http://localhost:8080

### 测试订单号

系统已预置 10 条测试订单，可用于订单查询功能测试：

- `HS20260130120000123456` - 成人门票订单
- `HS20260130120100234567` - 学生门票订单
- `HS20260130120300456789` - 经济型酒店订单
- `HS20260130120400567890` - 豪华型酒店订单
- `HS20260130120600789012` - 上行索道订单
- `HS20260130120700890123` - 下行索道订单

## 代码架构

### 系统架构图

```
┌─────────────────────────────────────────────────────────────┐
│                         用户浏览器                            │
│                    http://localhost:3000                     │
└────────────────────────┬────────────────────────────────────┘
                         │
                         │ HTTP Request
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                    Nginx (Frontend)                          │
│                      Port: 3000                              │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Vue 3 SPA                                           │  │
│  │  - Home.vue (首页 + Banner)                          │  │
│  │  - ServiceForm.vue (动态表单)                        │  │
│  │  - Result.vue (结果展示)                             │  │
│  │  - OrderQuery.vue (订单查询)                         │  │
│  └──────────────────────────────────────────────────────┘  │
└────────────────────────┬────────────────────────────────────┘
                         │
                         │ Axios HTTP Request
                         │ /api/huangshan/*
                         ▼
┌─────────────────────────────────────────────────────────────┐
│              Spring Boot Backend (Port: 8085)                │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  Controller Layer                                    │  │
│  │  ├─ ServiceController (@RestController)             │  │
│  │  │   └─ POST /api/huangshan/service                 │  │
│  │  └─ OrderController (@RestController)               │  │
│  │      └─ GET /api/huangshan/order/query              │  │
│  └──────────────────────┬───────────────────────────────┘  │
│                         │                                    │
│  ┌──────────────────────▼───────────────────────────────┐  │
│  │  Service Layer (多态实现)                            │  │
│  │  ┌────────────────────────────────────────────────┐ │  │
│  │  │  HuangshanService (抽象父类)                   │ │  │
│  │  │  + serviceId: String                           │ │  │
│  │  │  + serviceName: String                         │ │  │
│  │  │  + process(request): ServiceResponse (抽象)    │ │  │
│  │  └────────────────────┬───────────────────────────┘ │  │
│  │           ┌───────────┼───────────┐                 │  │
│  │           │           │           │                 │  │
│  │  ┌────────▼──┐  ┌────▼─────┐  ┌─▼──────────┐      │  │
│  │  │ Ticket    │  │ Hotel    │  │ CableCar   │      │  │
│  │  │ Service   │  │ Service  │  │ Service    │      │  │
│  │  │ (门票)    │  │ (酒店)   │  │ (索道)     │      │  │
│  │  └───────────┘  └──────────┘  └────────────┘      │  │
│  └──────────────────────┬───────────────────────────────┘  │
│                         │                                    │
│  ┌──────────────────────▼───────────────────────────────┐  │
│  │  Repository Layer (JPA)                              │  │
│  │  └─ HsOrderRepository extends JpaRepository         │  │
│  └──────────────────────┬───────────────────────────────┘  │
└─────────────────────────┼────────────────────────────────────┘
                          │
                          │ JDBC
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                  MySQL 8.0 (Port: 3307)                      │
│  Database: huangshan_db                                      │
│  Table: hs_order                                             │
│  - 订单数据持久化                                             │
│  - 10条种子数据                                               │
└─────────────────────────────────────────────────────────────┘
```

### 后端架构详解

#### 1. 分层架构

```
backend/src/main/java/com/huangshan/
│
├── controller/              # 控制器层 (接收HTTP请求)
│   ├── ServiceController.java
│   └── OrderController.java
│
├── service/                 # 服务层 (业务逻辑 + 多态实现)
│   ├── HuangshanService.java      # 抽象父类
│   ├── TicketService.java         # 门票服务子类
│   ├── HotelService.java          # 酒店服务子类
│   └── CableCarService.java       # 索道服务子类
│
├── repository/              # 数据访问层 (JPA)
│   └── HsOrderRepository.java
│
├── model/                   # 实体层 (数据库映射)
│   └── HsOrder.java
│
├── dto/                     # 数据传输对象
│   ├── ServiceRequest.java        # 服务请求DTO
│   ├── ServiceResponse.java       # 服务响应DTO
│   └── ApiResponse.java           # 统一响应格式
│
├── util/                    # 工具类
│   ├── InputSanitizer.java        # 输入清理
│   └── PriceConstants.java        # 价格常量
│
├── filter/                  # 过滤器
│   └── RateLimitFilter.java       # API限流
│
└── HuangshanTourApplication.java  # 启动类
```

#### 2. 多态设计核心代码

**抽象父类** (`HuangshanService.java`):
```java
public abstract class HuangshanService {
    protected String serviceId;
    protected String serviceName;

    public HuangshanService(String serviceId, String serviceName) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    // 抽象方法：由子类实现具体业务逻辑
    public abstract ServiceResponse process(ServiceRequest request) throws Exception;

    // 共享工具方法
    protected Integer getIntValue(Object value) {
        // 类型转换逻辑
    }
}
```

**子类实现** (`TicketService.java`):
```java
public class TicketService extends HuangshanService {
    public TicketService() {
        super("ticket", "门票预订");
    }

    @Override
    public ServiceResponse process(ServiceRequest request) throws Exception {
        // 门票业务逻辑：
        // 1. 解析请求参数（游客类型、日期、数量）
        // 2. 根据游客类型计算价格
        // 3. 生成订单号
        // 4. 保存订单到数据库
        // 5. 返回响应
    }
}
```

**多态调用** (`ServiceController.java`):
```java
@PostMapping("/service")
public ApiResponse<ServiceResponse> processService(@RequestBody ServiceRequest request) {
    String serviceType = request.getServiceType();

    // 多态体现：父类引用指向子类对象
    HuangshanService service;
    switch (serviceType) {
        case "ticket":
            service = new TicketService();    // 子类对象
            break;
        case "hotel":
            service = new HotelService();     // 子类对象
            break;
        case "cableCar":
            service = new CableCarService();  // 子类对象
            break;
        default:
            throw new IllegalArgumentException("不支持的服务类型");
    }

    // 统一调用父类方法，实际执行子类实现
    ServiceResponse response = service.process(request);

    return ApiResponse.success(response);
}
```

#### 3. 数据流转

```
HTTP Request
    ↓
Controller 接收请求
    ↓
根据 serviceType 创建对应子类实例 (多态)
    ↓
调用 service.process(request)
    ↓
子类执行具体业务逻辑
    ↓
Repository 保存订单到数据库
    ↓
返回 ServiceResponse
    ↓
Controller 包装为 ApiResponse
    ↓
HTTP Response (JSON)
```

### 前端架构详解

#### 1. 组件结构

```
frontend/src/
│
├── views/                   # 页面组件
│   ├── Home.vue            # 首页
│   │   ├── Banner轮播图 (4张黄山图片)
│   │   └── 3个服务按钮 (门票/酒店/索道)
│   │
│   ├── ServiceForm.vue     # 服务表单页 (动态表单)
│   │   ├── 门票表单 (游客类型/日期/数量)
│   │   ├── 酒店表单 (酒店类型/入住日期/离店日期/房间数)
│   │   └── 索道表单 (索道类型/数量)
│   │
│   ├── Result.vue          # 结果展示页
│   │   ├── 服务类型
│   │   ├── 订单号
│   │   ├── 订单明细
│   │   ├── 总价
│   │   └── 返回首页按钮
│   │
│   └── OrderQuery.vue      # 订单查询页
│       ├── 订单号输入框
│       └── 查询结果展示
│
├── router/                  # 路由配置
│   └── index.js
│       ├── / → Home
│       ├── /service/:type → ServiceForm
│       ├── /result → Result
│       └── /order-query → OrderQuery
│
├── utils/                   # 工具类
│   └── request.js          # Axios封装
│       ├── baseURL: /api
│       ├── 请求拦截器
│       └── 响应拦截器
│
├── App.vue                  # 根组件
└── main.js                  # 入口文件
```

#### 2. 路由流程

```
用户访问 http://localhost:3000
    ↓
Home.vue (首页)
    ↓ 点击"门票预订"
/service/ticket
    ↓
ServiceForm.vue (动态渲染门票表单)
    ↓ 填写表单并提交
Axios POST /api/huangshan/service
    ↓ 后端处理成功
router.push({ name: 'Result', state: { orderData } })
    ↓
Result.vue (展示订单结果)
    ↓ 点击"返回首页"
Home.vue
```

#### 3. 表单动态渲染逻辑

```javascript
// ServiceForm.vue
const route = useRoute()
const serviceType = route.params.type  // 从URL获取服务类型

// 根据 serviceType 显示不同表单
<div v-if="serviceType === 'ticket'">
  <!-- 门票表单 -->
</div>
<div v-else-if="serviceType === 'hotel'">
  <!-- 酒店表单 -->
</div>
<div v-else-if="serviceType === 'cableCar'">
  <!-- 索道表单 -->
</div>
```

#### 4. Axios 请求封装

```javascript
// request.js
const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message)
      return Promise.reject(new Error(res.message))
    }
    return res
  },
  error => {
    ElMessage.error('网络错误')
    return Promise.reject(error)
  }
)
```

### 数据库设计

#### hs_order 表结构

```sql
CREATE TABLE hs_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,      -- 订单号
    service_type VARCHAR(20) NOT NULL,         -- 服务类型
    service_name VARCHAR(50) NOT NULL,         -- 服务名称
    request_json TEXT,                         -- 请求参数JSON
    detail VARCHAR(500),                       -- 订单详情
    total_price DECIMAL(10,2),                 -- 总价
    status VARCHAR(20),                        -- 状态
    create_time DATETIME,                      -- 创建时间
    INDEX idx_order_no (order_no),
    INDEX idx_service_type (service_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

#### 订单号生成规则

```
格式: HS + yyyyMMddHHmmss + 6位UUID
示例: HS20260130123000A1B2C3
```

### 多态设计优势

1. **统一接口**: 所有服务通过同一个接口 `/api/huangshan/service` 处理
2. **易于扩展**: 新增服务类型只需添加新的子类，无需修改Controller
3. **代码复用**: 共享工具方法在父类中实现，子类直接继承
4. **符合开闭原则**: 对扩展开放，对修改关闭

### 安全特性

1. **输入清理**: `InputSanitizer` 防止XSS攻击
2. **API限流**: `RateLimitFilter` 限制每IP每分钟60次请求
3. **异常处理**: 统一异常处理，不暴露内部错误
4. **日志记录**: SLF4J记录所有关键操作
5. **环境变量**: 数据库密码支持环境变量配置

### 测试覆盖

- **单元测试**: 23个测试用例
  - TicketServiceTest: 8个测试
  - HotelServiceTest: 9个测试
  - CableCarServiceTest: 6个测试
- **测试覆盖**: 所有业务逻辑分支
- **测试框架**: JUnit 5 + Mockito

## 项目结构

```
huang-shan-tour/
├── backend/                    # Spring Boot 后端
│   ├── src/main/java/com/huangshan/
│   │   ├── service/           # 抽象类和三个子类（多态实现）
│   │   │   ├── HuangshanService.java      # 抽象父类
│   │   │   ├── TicketService.java         # 门票服务
│   │   │   ├── HotelService.java          # 酒店服务
│   │   │   └── CableCarService.java       # 索道服务
│   │   ├── controller/        # REST 控制器
│   │   ├── model/             # 数据库实体
│   │   ├── repository/        # JPA 仓库
│   │   └── dto/               # 数据传输对象
│   └── pom.xml
├── frontend/                   # Vue 3 前端
│   ├── src/
│   │   ├── views/             # 页面组件
│   │   │   ├── Home.vue       # 首页
│   │   │   ├── ServiceForm.vue # 服务表单页
│   │   │   ├── Result.vue     # 结果页
│   │   │   └── OrderQuery.vue # 订单查询页
│   │   ├── router/            # 路由配置
│   │   └── utils/             # 工具类（Axios 封装）
│   └── package.json
├── mysql/
│   └── init.sql               # 数据库初始化脚本（含种子数据）
├── docker-compose.yml         # Docker Compose 配置
└── README.md
```

## 技术细节

### 后端技术栈

#### 核心框架

- **Spring Boot 3.2.1**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Boot Starter Test

#### 数据库

- **MySQL 8.0**
  - Connector/J 驱动
  - utf8mb4 字符集
  - InnoDB 存储引擎

#### 工具库

- **Lombok**: 简化实体类代码
- **Jackson**: JSON序列化/反序列化
- **SLF4J + Logback**: 日志框架

#### 测试框架

- **JUnit 5**: 单元测试
- **23个测试用例**: 覆盖所有业务逻辑

### 前端技术栈

#### 核心框架

- **Vue 3.4.15**
  - Composition API
  - Reactive 响应式系统
  - 组件化开发

#### UI组件库

- **Element Plus 2.5.4**
  - 中文友好
  - 丰富的组件
  - 表单验证支持

#### 构建工具

- **Vite 5.0.11**
  - 快速冷启动
  - HMR热更新
  - 优化的构建输出

#### 路由

- **Vue Router 4.2.5**
  - 前端路由管理
  - 路由守卫

#### HTTP客户端

- **Axios 1.6.5**
  - Promise based
  - 请求/响应拦截器
  - 统一错误处理

### 数据库设计

#### hs_order 表结构

| 字段名       | 类型          | 说明         | 约束             |
| ------------ | ------------- | ------------ | ---------------- |
| id           | BIGINT        | 主键         | AUTO_INCREMENT   |
| order_no     | VARCHAR(50)   | 订单号       | UNIQUE, NOT NULL |
| service_type | VARCHAR(20)   | 服务类型     | NOT NULL, INDEX  |
| service_name | VARCHAR(50)   | 服务名称     | NOT NULL         |
| request_json | TEXT          | 请求参数JSON | -                |
| detail       | VARCHAR(500)  | 订单详情     | -                |
| total_price  | DECIMAL(10,2) | 总价         | -                |
| status       | VARCHAR(20)   | 状态         | -                |
| create_time  | DATETIME      | 创建时间     | -                |

**索引**:

- PRIMARY KEY (`id`)
- UNIQUE KEY `idx_order_no` (`order_no`)
- KEY `idx_service_type` (`service_type`)

## API 接口

### 1. 提交服务订单

**POST** `/api/huangshan/service`

请求体示例（门票）：

```json
{
	"serviceType": "ticket",
	"requestParam": {
		"visitorType": "student",
		"playDate": "2026-02-01",
		"quantity": 1
	}
}
```

响应示例：

```json
{
	"code": 200,
	"message": "操作成功",
	"data": {
		"serviceType": "ticket",
		"serviceName": "门票预订",
		"orderNo": "HS20260130123000123456",
		"detail": "学生票 1 张，单价 75 元，游玩日期 2026-02-01",
		"totalPrice": 75.0,
		"status": "成功"
	}
}
```

### 2. 查询订单

**GET** `/api/huangshan/order/query?orderNo=HS20260130123000123456`

响应示例：

```json
{
	"code": 200,
	"message": "操作成功",
	"data": {
		"orderNo": "HS20260130123000123456",
		"serviceType": "ticket",
		"serviceName": "门票预订",
		"detail": "学生票 1 张，单价 75 元，游玩日期 2026-02-01",
		"totalPrice": 75.0,
		"status": "成功",
		"createTime": "2026-01-30 12:30:00"
	}
}
```

## 价格规则

### 门票

- 成人票：150 元/张
- 学生票：75 元/张
- 老人票（70岁以上）：免费

### 酒店

- 经济型：300 元/晚/间
- 豪华型：800 元/晚/间

### 索道

- 上行：80 元/张
- 下行：70 元/张

## 表单验证

- 日期不能选择过去时间
- 数量必须 >= 1
- 离店日期必须大于入住日期
- 所有必填字段不能为空

## 数据库配置

- 主机: localhost
- 端口: 3306
- 数据库: huangshan_db
- 用户名: root
- 密码: 123456
- 字符集: utf8mb4

## 停止服务

```bash
docker-compose down
```

## 清理数据

```bash
docker-compose down -v
```

## 开发说明

### 本地开发（不使用 Docker）

#### 后端

1. 确保本地安装 MySQL 8，创建数据库 `huangshan_db`
2. 执行 `mysql/init.sql` 初始化数据
3. 进入 backend 目录
4. 运行 `mvn spring-boot:run`

#### 前端

1. 进入 frontend 目录
2. 运行 `npm install`
3. 运行 `npm run dev`
4. 访问 http://localhost:5173

## 验收要点

✅ 三类服务均可提交并返回正确订单结果与总价
✅ 订单写入 MySQL 8 成功
✅ 可通过订单号查询订单并展示详情
✅ 多态分发逻辑清晰（父类引用指向子类对象）
✅ 异常校验（日期、数量等）
✅ 前后端联调成功，API 调用正常
✅ 所有界面使用中文，无乱码
✅ 使用 Docker Compose 一键启动
✅ 包含完整的种子数据

## 许可证

MIT
