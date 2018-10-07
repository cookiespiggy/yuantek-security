package com.yuantek.mi.web.async;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 模拟消息队列
 */
@Getter
@Setter
@Component
@Slf4j
public class MockQueue {

    @Setter(AccessLevel.NONE)
    private String placeOrder;

    private String completeOrder;

    public void setPlaceOrder(String placeOrder) {
        // 模拟应用2
        new Thread(() -> {
            log.info("接到下单请求," + placeOrder);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder;
            log.info("下单请求处理完毕," + placeOrder);
        }).start();
    }
}
