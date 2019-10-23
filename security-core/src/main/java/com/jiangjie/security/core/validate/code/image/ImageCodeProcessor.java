package com.jiangjie.security.core.validate.code.image;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.jiangjie.security.core.validate.code.ValidateCode;
import com.jiangjie.security.core.validate.code.impl.AbstractValidateCodeProcessor;

@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor {

	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		ImageIO.write(((ImageCode)validateCode).getImage(), "JPEG", request.getResponse().getOutputStream());		
	}

}
