package com.example
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MethodExecutionCalculationAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSS");
		LocalDateTime now = LocalDateTime.now();
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();

		final StopWatch stopWatch = new StopWatch();
		//Measure method execution time
		stopWatch.start();
		Object returnProceed = joinPoint.proceed();
		stopWatch.stop();

		logger.info(" [Method] '{}' [RETURN-TYPE] '{}' [START-AT] {} [EXEC-TIME] {} [CALLED-BY] {}", methodSignature.getName(), methodSignature.getReturnType().toString().substring(methodSignature.getReturnType().toString().lastIndexOf('.') + 1), formatter.format(now), stopWatch.getTotalTimeMillis() + " ms", joinPoint);

		return returnProceed;
	}
}
