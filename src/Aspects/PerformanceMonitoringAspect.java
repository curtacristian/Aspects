package Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class PerformanceMonitoringAspect {
	@Around("execution(* Repository.Repository.*.run())")
	public void logTime(ProceedingJoinPoint method) throws Throwable {
		long startTime = System.nanoTime();
		method.proceed();
		long endTime = System.nanoTime();
		System.out.println("Time elapsed for method " + method.getSignature() + ": " + (endTime - startTime));
	}
}
