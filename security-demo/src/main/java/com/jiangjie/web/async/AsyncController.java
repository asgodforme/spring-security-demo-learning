package com.jiangjie.web.async;

import java.util.concurrent.Callable;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AsyncController {
	
	@Autowired
	private MockQueue mockQueue;
	
	@Autowired
	private DeferredResultHolder deferredResultHolder;

	private Logger logger = LoggerFactory.getLogger(AsyncController.class);

	@RequestMapping("/order")
	public String order() throws InterruptedException {
		logger.info("主线程开始");
		Thread.sleep(1000);
		logger.info("主线程结束");
		return "success";
	}

	@RequestMapping("/order1")
	public Callable<String> order1() throws InterruptedException {
		logger.info("主线程1开始");
		Callable<String>  result = new Callable<String>() {
			@Override
			public String call() throws Exception {
				logger.info("副线程开始"); 
				Thread.sleep(1000);
				logger.info("副线程结束");
				return "success";
			}
		};
		logger.info("主线程1结束");
		return result;
	}
	
	@RequestMapping("/order2")
	public DeferredResult<String> order2() throws InterruptedException {
		logger.info("主线程开始");
		String orderNumber = RandomStringUtils.randomNumeric(8);
		mockQueue.setPlaceOrder(orderNumber);
		DeferredResult<String> result = new DeferredResult<String>();
		deferredResultHolder.getMap().put(orderNumber, result);
		logger.info("主线程结束");
		return result;
	}

}
