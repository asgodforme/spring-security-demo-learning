package com.jiangjie.security.core.validate.code.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.jiangjie.security.core.validate.code.ValidateCode;
import com.jiangjie.security.core.validate.code.ValidateCodeException;
import com.jiangjie.security.core.validate.code.ValidateCodeGenerator;
import com.jiangjie.security.core.validate.code.ValidateCodeProcessor;
import com.jiangjie.security.core.validate.code.ValidateCodeType;

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
		String type = getValidateCodeType(request).toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return validateCodeGenerator.createImageCode(request);
	}
	
	private void save(ServletWebRequest request, ValidateCode validateCode) {
		sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
	}
	
	private String getSessionKey(ServletWebRequest request) {
		return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
	}
	
	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}
	 

	protected abstract void send(ServletWebRequest request, ValidateCode validateCode) throws Exception;
	
	@Override
	public void validate(ServletWebRequest request) {
		ValidateCodeType processType = getValidateCodeType(request);
		String sessionKey = getSessionKey(request);
		
		ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, sessionKey);
		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), processType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isEmpty(codeInRequest)) {
			throw new ValidateCodeException("验证码不能为空！");
		}

		if (codeInRequest == null) {
			throw new ValidateCodeException("验证码不存在！");
		}

		if (codeInSession.isExpried()) {
			sessionStrategy.removeAttribute(request, sessionKey);	
			throw new ValidateCodeException("验证码已过期！");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		sessionStrategy.removeAttribute(request, sessionKey);		
	}

}
