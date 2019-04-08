package com.example.ipservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by jacob on 2019/4/3.
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Pointcut("@annotation(com.example.ipservice.aspect.anno.SysLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();

        long time = System.currentTimeMillis() - beginTime;
        log.info("{} {} cost time {} ms", point.getSignature().getName(), point.getArgs(), time);
        return result;
    }

}
