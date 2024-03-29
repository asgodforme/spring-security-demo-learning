package com.jiangjie.security.core.validate.code;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class ValidateCodeController {
	
//	protected static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
//	
//	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
//
//	@Autowired
//	private ValidateCodeGenerator imageCodeGenerator;
//	
//	@Autowired
//	private ValidateCodeGenerator smsCodeGenerator;
//	
//	@Autowired
//	private SmsCodeSender smsCodeSender;
//	
//	@GetMapping("/code/image")
//	public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		ImageCode imageCode = (ImageCode) imageCodeGenerator.createImageCode(new ServletWebRequest(request));
//		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
//		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
//	}
//	
//	@GetMapping("/code/sms")
//	public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
//		ValidateCode smsCode = smsCodeGenerator.createImageCode(new ServletWebRequest(request));
//		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
//		String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
//		smsCodeSender.send(mobile, smsCode.getCode());
//	}
	
	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;
	
	@GetMapping("/code/{type}")
	public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
		validateCodeProcessors.get(type + "CodeProcessor").create(new ServletWebRequest(request, response));
	}
}
