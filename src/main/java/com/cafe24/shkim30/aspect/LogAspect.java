package com.cafe24.shkim30.aspect;

import com.cafe24.shkim30.library.LogTrace;
import com.cafe24.shkim30.library.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final LogTrace trace;

    @Around("execution(* *..controller.*.*(..)) || execution(* *..service.*.*(..)) || execution(* *..providor.*.*(..))")
    public Object setLogTrace(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String targetName = pjp.getTarget().getClass().getName() + " :: " + pjp.getSignature().getName() + "-" + request.getHeader("X-Forwarded-For");
        TraceStatus logStatus = trace.begin(targetName);

        Object result = null;

        try {
            result = pjp.proceed(); // method 실행
            trace.end(logStatus);
        } catch (Exception e) {
            trace.exception(logStatus, e);
        } finally {
            return result;
        }
    }
}
