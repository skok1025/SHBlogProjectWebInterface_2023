package com.cafe24.shkim30.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ControllerCallLogAspect {

    @Before("execution(* *..controller.*.*(..))")
    public void beforeController(JoinPoint joinpoint) {
        String className = joinpoint.getTarget().getClass().getName();

        MethodSignature signature = (MethodSignature)joinpoint.getSignature();
        String methodName = signature.getMethod().getName();

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info("Controller Call Start: " + className + " :: " + methodName);
    }

    @After("execution(* *..controller.*.*(..))")
    public void afterExec(JoinPoint joinpoint) {
        String className = joinpoint.getTarget().getClass().getName();

        MethodSignature signature = (MethodSignature)joinpoint.getSignature();
        String methodName = signature.getMethod().getName();

        log.info("Controller Call End: " + className + " :: " + methodName);
    }

    @AfterThrowing(pointcut = "execution(* *..controller.*.*(..))", throwing = "e")
    public void afterExec(JoinPoint joinpoint, Exception e) throws Throwable{
        String className = joinpoint.getTarget().getClass().getName();

        MethodSignature signature = (MethodSignature)joinpoint.getSignature();
        String methodName = signature.getMethod().getName();

        log.error("Controller Call Error: " + className + " :: " + methodName);
    }
}
