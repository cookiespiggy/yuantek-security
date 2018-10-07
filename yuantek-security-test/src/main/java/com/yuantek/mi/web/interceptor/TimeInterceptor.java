package com.yuantek.mi.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

// 单独加这个注解并不能让这个拦截器起作用
@Component
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        System.out.println("preHandle");
        System.out.println(((HandlerMethod) handler).getBean().getClass().getName());
        System.out.println(((HandlerMethod) handler).getMethod().getName());
        httpServletRequest.setAttribute("startTime", Instant.now().toEpochMilli());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        long start = (Long) httpServletRequest.getAttribute("startTime");
        System.out.println("time interceptor cost " + (Instant.now().toEpochMilli() - start) + "ms");
    }

    /** @ControllerAdvice 处理是在 afterCompletion 之前的,所以此处拿不到e
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        System.out.println("afterCompletion");
        long start = (Long) httpServletRequest.getAttribute("startTime");
        System.out.println("time interceptor cost " + (Instant.now().toEpochMilli() - start) + "ms");
        System.out.println("ex is " + e);
    }
}
