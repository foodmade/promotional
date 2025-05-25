package com.seller66.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置类
 */
@Configuration
public class CorsConfig {

    /**
     * 配置跨域过滤器
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        // 创建CORS配置
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许所有来源
        config.addAllowedOriginPattern("*");
        
        // 允许所有请求头
        config.addAllowedHeader("*");
        
        // 允许所有方法
        config.addAllowedMethod("*");
        
        // 允许凭证
        config.setAllowCredentials(true);
        
        // 暴露响应头
        config.addExposedHeader("Authorization");
        
        // 设置预检请求的缓存时间（秒）
        config.setMaxAge(3600L);
        
        // 配置所有接口都支持跨域
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        // 返回配置好的CORS过滤器
        return new CorsFilter(source);
    }
} 