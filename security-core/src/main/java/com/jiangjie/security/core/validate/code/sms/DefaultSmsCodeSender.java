package com.jiangjie.security.core.validate.code.sms;

public class DefaultSmsCodeSender implements SmsCodeSender {

	@Override
	public void send(String mobile, String code) {
		System.out.println("向" + mobile + "手机发送验证码" + code);
	}

}
