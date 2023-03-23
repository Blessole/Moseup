package project.moseup.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* project.moseup..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Start " + joinPoint.toString() + " ================");
    }

    @After("execution(* project.moseup..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("End " + joinPoint.toString() + " ================");
    }


}

