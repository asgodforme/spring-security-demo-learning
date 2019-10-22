package com.jiangjie.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jiangjie.web.filter.TimeFilter;
import com.jiangjie.web.interceptor.TimeInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private TimeInterceptor timeInterceptor;
	
//	@Override
//	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//
//	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(timeInterceptor);
	}

//	@Bean
	public FilterRegistrationBean timeFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		TimeFilter timefilter = new TimeFilter();
		registrationBean.setFilter(timefilter);
		
		List<String> url = new ArrayList<>();
		url.add("/*");
		registrationBean.setUrlPatterns(url);
		return registrationBean;
	}
}
