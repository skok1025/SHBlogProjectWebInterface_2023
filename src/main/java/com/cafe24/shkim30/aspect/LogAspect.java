package com.cafe24.shkim30.aspect;

import com.cafe24.shkim30.library.LogTrace;
import com.cafe24.shkim30.library.trace.TraceStatus;
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

import javax.servlet.http.HttpSession;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final LogTrace trace;

    @Before("execution(* *..controller.*.*(..)) || execution(* *..service.*.*(..))")
    public void beforeController(JoinPoint joinpoint) {
        String className = joinpoint.getTarget().getClass().getName();

        MethodSignature signature = (MethodSignature)joinpoint.getSignature();
        String methodName = signature.getMethod().getName();

        TraceStatus logStatus = trace.begin(className + "::" + methodName);

        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

        session.setAttribute("logStatus::"+Thread.currentThread().toString() + "::" + className + "::" + methodName, logStatus);
    }

    @After("execution(* *..controller.*.*(..)) || execution(* *..service.*.*(..))")
    public void afterExec(JoinPoint joinpoint) {
        String className = joinpoint.getTarget().getClass().getName();

        MethodSignature signature = (MethodSignature)joinpoint.getSignature();
        String methodName = signature.getMethod().getName();

        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        TraceStatus logStatus = (TraceStatus) session.getAttribute("logStatus::"+Thread.currentThread().toString() + "::" + className + "::" + methodName);
        trace.end(logStatus);

        session.removeAttribute("logStatus::"+Thread.currentThread().toString() + "::" + className + "::" + methodName);
    }

    @AfterThrowing(pointcut = "execution(* *..controller.*.*(..)) || execution(* *..service.*.*(..))", throwing = "e")
    public void afterExec(JoinPoint joinpoint, Exception e) throws Throwable{
        String className = joinpoint.getTarget().getClass().getName();

        MethodSignature signature = (MethodSignature)joinpoint.getSignature();
        String methodName = signature.getMethod().getName();

        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        TraceStatus logStatus = (TraceStatus) session.getAttribute("logStatus::"+Thread.currentThread().toString() + "::" + className + "::" + methodName);

        trace.exception(logStatus, e);

        session.removeAttribute("logStatus::"+Thread.currentThread().toString() + "::" + className + "::" + methodName);
    }
}