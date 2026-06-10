package com.huangshan.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单的API限流过滤器
 * 基于IP地址进行限流，防止滥用
 */
@Component
public class RateLimitFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitFilter.class);

    // 每个IP每分钟最大请求数
    private static final int MAX_REQUESTS_PER_MINUTE = 60;

    // 存储每个IP的请求计数
    private final ConcurrentHashMap<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = getClientIp(httpRequest);
        String requestUri = httpRequest.getRequestURI();

        // 只对API接口进行限流
        if (requestUri.startsWith("/api/")) {
            RequestCounter counter = requestCounts.computeIfAbsent(clientIp, k -> new RequestCounter());

            if (counter.increment() > MAX_REQUESTS_PER_MINUTE) {
                logger.warn("Rate limit exceeded for IP: {}, URI: {}", clientIp, requestUri);
                // 与项目其他接口保持一致：HTTP 200，业务码在响应体中
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                httpResponse.setContentType("application/json;charset=UTF-8");
                httpResponse.getWriter().write("{\"code\":429,\"message\":\"请求过于频繁，请稍后再试\",\"data\":null}");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * 获取客户端真实IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    /**
     * 请求计数器
     * 每分钟重置一次
     */
    private static class RequestCounter {
        private final AtomicInteger count = new AtomicInteger(0);
        private long lastResetTime = System.currentTimeMillis();

        public int increment() {
            long currentTime = System.currentTimeMillis();
            // 如果超过1分钟，重置计数器
            if (currentTime - lastResetTime > 60000) {
                count.set(0);
                lastResetTime = currentTime;
            }
            return count.incrementAndGet();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Rate limit filter initialized: {} requests per minute", MAX_REQUESTS_PER_MINUTE);
    }

    @Override
    public void destroy() {
        requestCounts.clear();
    }
}
