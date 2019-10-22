package com.jiangjie.web.aspect;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class TimeAspect {

	@Around("execution(* com.jiangjie.web.controller.UserController.*(..))")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("time aspect start");
		long start = new Date().getTime();
		Object[] args = pjp.getArgs();
		Arrays.asList(args).stream().forEach(arg -> System.out.println("arg is :" + arg));
		Object obj = pjp.proceed();
		System.out.println("time aspect 耗时：" + (new Date().getTime() - start));
		System.out.println("time aspect end");
		return null;
	}
}
