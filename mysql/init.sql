-- 设置字符集
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS huangshan_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE huangshan_db;

-- 创建订单表
CREATE TABLE IF NOT EXISTS hs_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    service_type VARCHAR(20) NOT NULL,
    service_name VARCHAR(50) NOT NULL,
    request_json TEXT,
    detail VARCHAR(500),
    total_price DECIMAL(10,2),
    status VARCHAR(20),
    create_time DATETIME,
    INDEX idx_order_no (order_no),
    INDEX idx_service_type (service_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 插入种子数据
INSERT INTO hs_order (order_no, service_type, service_name, request_json, detail, total_price, status, create_time) VALUES
('HS20260130120000123456', 'ticket', '门票预订', '{"visitorType":"adult","playDate":"2026-02-01","quantity":2}', '成人票 2 张，单价 150 元，游玩日期 2026-02-01', 300.00, '成功', '2026-01-30 12:00:00'),
('HS20260130120100234567', 'ticket', '门票预订', '{"visitorType":"student","playDate":"2026-02-02","quantity":3}', '学生票 3 张，单价 75 元，游玩日期 2026-02-02', 225.00, '成功', '2026-01-30 12:01:00'),
('HS20260130120200345678', 'ticket', '门票预订', '{"visitorType":"senior","playDate":"2026-02-03","quantity":2}', '老人票（70岁以上免费） 2 张，单价 0 元，游玩日期 2026-02-03', 0.00, '成功', '2026-01-30 12:02:00'),
('HS20260130120300456789', 'hotel', '酒店预约', '{"hotelType":"economy","checkInDate":"2026-02-05","checkOutDate":"2026-02-07","rooms":2}', '经济型酒店 2 间，入住 2 晚，单价 300 元/晚/间，入住日期 2026-02-05，离店日期 2026-02-07', 1200.00, '成功', '2026-01-30 12:03:00'),
('HS20260130120400567890', 'hotel', '酒店预约', '{"hotelType":"luxury","checkInDate":"2026-02-10","checkOutDate":"2026-02-13","rooms":1}', '豪华型酒店 1 间，入住 3 晚，单价 800 元/晚/间，入住日期 2026-02-10，离店日期 2026-02-13', 2400.00, '成功', '2026-01-30 12:04:00'),
('HS20260130120500678901', 'hotel', '酒店预约', '{"hotelType":"luxury","checkInDate":"2026-02-15","checkOutDate":"2026-02-16","rooms":3}', '豪华型酒店 3 间，入住 1 晚，单价 800 元/晚/间，入住日期 2026-02-15，离店日期 2026-02-16', 2400.00, '成功', '2026-01-30 12:05:00'),
('HS20260130120600789012', 'cableCar', '索道购票', '{"cableType":"up","quantity":2}', '索道上行票 2 张，单价 80 元', 160.00, '成功', '2026-01-30 12:06:00'),
('HS20260130120700890123', 'cableCar', '索道购票', '{"cableType":"down","quantity":3}', '索道下行票 3 张，单价 70 元', 210.00, '成功', '2026-01-30 12:07:00'),
('HS20260130120800901234', 'cableCar', '索道购票', '{"cableType":"up","quantity":5}', '索道上行票 5 张，单价 80 元', 400.00, '成功', '2026-01-30 12:08:00'),
('HS20260130120900012345', 'ticket', '门票预订', '{"visitorType":"adult","playDate":"2026-02-20","quantity":4}', '成人票 4 张，单价 150 元，游玩日期 2026-02-20', 600.00, '成功', '2026-01-30 12:09:00');

-- 验证数据插入
SELECT COUNT(*) as total_orders FROM hs_order;
SELECT service_type, COUNT(*) as count FROM hs_order GROUP BY service_type;
