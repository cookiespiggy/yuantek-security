package com.yuantek.mi.web.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@Slf4j
public class AsyncController {

    // 同步处理
//    @RequestMapping("/order")
//    public String order() throws Exception {
//        log.info("主线程开始");
//        Thread.sleep(1000);
//        log.info("主线程返回");
//        return "success";
//    }
    // 异步处理方式一: 适合主线程调用副线程模式
//    @RequestMapping("/order")
//    public Callable<String> order() throws Exception {
//        log.info("主线程开始");
//        Callable<String> result = new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                log.info("副线程开始");
//                Thread.sleep(1000);
//                log.info("副线程返回");
//                return null;
//            }
//        };
//        log.info("主线程返回");
//        return result;
//    }
    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    // 异步处理方式二: 适合不同线程不同服务器间通过消息队列解耦模式
    @RequestMapping("/order")
    public DeferredResult<String> order() throws Exception {
        log.info("主线程开始");
        // 1. 模拟接收到订单号
        String orderNum = RandomStringUtils.randomNumeric(8);
        // 2. 模拟把订单号放到消息队列里面
        mockQueue.setPlaceOrder(orderNum);
        // 3. new DeferredResult 再放入map里面
        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNum, result);
        log.info("主线程返回");
        return result;
    }
}
