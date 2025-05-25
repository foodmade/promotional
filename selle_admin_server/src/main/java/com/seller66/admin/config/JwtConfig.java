package com.seller66.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    /**
     * JWT加解密使用的密钥
     */
    private String secret;
    
    /**
     * JWT的有效期（秒）
     */
    private long expiration;
    
    /**
     * Token前缀
     */
    private String tokenPrefix;
    
    /**
     * 存放Token的Header Key
     */
    private String header;
} 