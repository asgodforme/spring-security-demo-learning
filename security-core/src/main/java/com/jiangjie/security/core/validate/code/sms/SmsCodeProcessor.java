package com.jiangjie.security.core.validate.code.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.jiangjie.security.core.validate.code.ValidateCode;
import com.jiangjie.security.core.validate.code.impl.AbstractValidateCodeProcessor;

@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor {
	
	@Autowired
	private SmsCodeSender smsCodeSender;

	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
		smsCodeSender.send(mobile, validateCode.getCode());
	}

}
