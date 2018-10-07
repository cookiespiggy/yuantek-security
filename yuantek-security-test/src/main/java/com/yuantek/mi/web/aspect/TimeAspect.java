package com.yuantek.mi.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static com.yuantek.mi.utils.ObjectUtil.pretty;

@Aspect
@Component
public class TimeAspect {

    // 详见官方文档的11.2.3
    // execution(* set*(..)) 这个也可以用,在容器中的类所有以set开始的方法
    @Around("execution(* com.yuantek.mi.web.controller.UserController.*(..))")
    // 包围的方式我要把结果返回回去,结果可能是任何类型
    // ProceedingJoinPoint 包含了你当前拦截的方法的一些信息,和拦截器的preHandle方法类似
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("time aspect start");
        pretty(pjp.getArgs());
        long start = Instant.now().toEpochMilli();
        Object obj = pjp.proceed(); // 和Filter的doFilter方法类型,是调用被拦截到的那个方法
        System.out.println("time aspect cost " + (Instant.now().toEpochMilli() - start) + "ms");
        System.out.println("time aspect end");
        return obj;
    }
}
