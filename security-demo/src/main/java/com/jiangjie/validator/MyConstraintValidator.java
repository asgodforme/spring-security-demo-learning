package com.jiangjie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jiangjie.service.HelloService;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint, String> {

	@Autowired
	private HelloService helloService;
	
	@Override
	public void initialize(MyConstraint constraintAnnotation) {
		System.out.println("my validator init");
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		System.out.println(value);
		helloService.greeting(value);
		return false;
	}

}
