package com.jiangjie.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.jiangjie.security.core.properties.SecurityProperties;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class) // 让SecurityCoreConfig配置类生效
public class SecurityCoreConfig {
	
	

}
