package com.yuantek.mi.web.async;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 在两个线程之前传递DeferredResult对象
 */
@Component
public class DeferredResultHolder {

    /**
     * 每个订单号对应一个处理结果
     */
    @Getter
    @Setter
    private Map<String,DeferredResult<String>> map = new HashMap<>();

}
