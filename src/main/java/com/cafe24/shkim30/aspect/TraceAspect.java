package com.cafe24.shkim30.aspect;

import com.cafe24.shkim30.library.libTrace;
import com.cafe24.shkim30.library.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
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
public class TraceAspect {
    private final libTrace trace;

    @Before("execution(* *..controller.*.*(..))")
    public void beforeController(JoinPoint joinpoint) {
        String className = joinpoint.getTarget().getClass().getName();

        MethodSignature signature = (MethodSignature)joinpoint.getSignature();
        String methodName = signature.getMethod().getName();

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        TraceStatus status = trace.begin(className + " :: " + methodName);
        request.setAttribute("traceStatus", status);
    }

    @Before("execution(* *..service.*.*(..))")
    public void beforeService(JoinPoint joinpoint) {
        String className = joinpoint.getTarget().getClass().getName();

        MethodSignature signature = (MethodSignature)joinpoint.getSignature();
        String methodName = signature.getMethod().getName();

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        TraceStatus status = trace.beginSync(((TraceStatus) request.getAttribute("traceStatus")).getTraceId(), className + " :: " + methodName);
        request.setAttribute("traceStatus", status);
    }

    @After("execution(* *..controller.*.*(..))")
    public void afterController(JoinPoint joinpoint) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        TraceStatus status = (TraceStatus) request.getAttribute("traceStatus");
        trace.end(status);
    }

    @After("execution(* *..service.*.*(..))")
    public void afterService(JoinPoint joinpoint) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        TraceStatus status = (TraceStatus) request.getAttribute("traceStatus");
        trace.end(status);
    }
}
