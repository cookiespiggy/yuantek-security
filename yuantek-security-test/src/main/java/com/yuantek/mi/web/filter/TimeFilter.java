package com.yuantek.mi.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
import java.time.Instant;

@Slf4j
//@Component
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("time filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log. info("time filter start");
        long start = Instant.now().toEpochMilli();
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("time filter cost {} ms", Instant.now().toEpochMilli() - start);
        log.info("time filter finish");
    }

    @Override
    public void destroy() {
        log.info("time filter destroy");
    }
}
