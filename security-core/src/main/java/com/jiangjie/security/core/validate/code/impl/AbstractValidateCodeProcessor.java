package com.jiangjie.security.core.validate.code.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import com.jiangjie.security.core.validate.code.ValidateCode;
import com.jiangjie.security.core.validate.code.ValidateCodeGenerator;
import com.jiangjie.security.core.validate.code.ValidateCodeProcessor;

public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor {
	
	/**
	 * session工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	@Override
	public void create(ServletWebRequest request) throws Exception {
		ValidateCode validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}
	
	private ValidateCode generate(ServletWebRequest request) {
		String type = getProcessorType(request);
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
		return validateCodeGenerator.createImageCode(request);
	}
	
	private void save(ServletWebRequest request, ValidateCode validateCode) {
		sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(), validateCode);
	}
	 

	protected abstract void send(ServletWebRequest request, ValidateCode validateCode) throws Exception;
	
	private String getProcessorType(ServletWebRequest request) {
		return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
	}

}
