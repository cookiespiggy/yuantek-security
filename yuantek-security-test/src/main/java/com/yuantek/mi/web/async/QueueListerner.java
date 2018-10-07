package com.yuantek.mi.web.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 此种方式在controller里面是看不到有另外一个线程要怎么处理的
 * <p>
 * 此监听器的作用是监听虚拟消息队列,completeOrder被赋值之后,会用放到map里面的DeferredResult来最终返回一个结果给浏览器
 * <p>
 * 关键字：spring容器加载完毕做一件事情（利用ContextRefreshedEvent事件） 
 * 应用场景：很多时候我们想要在某个类加载完毕时干某件事情，但是使用了spring管理对象，我们这个类引用了其他类（可能是更复杂的关联），
 * 所以当我们去使用这个类做事情时发现包空指针错误，这是因为我们这个类有可能已经初始化完成，
 * 但是引用的其他类不一定初始化完成，所以发生了空指针错误，解决方案如下： 
 */
@Component
@Slf4j
public class QueueListerner implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 单开一个线程,防止主线程阻塞
        new Thread(() -> {
            // 相当于系统启动起来之后,我要做的事情
            // 我要监听模拟的消息队列里面的completeOrder的值
            // 模拟监听代码
            while (true) {
                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
                    String order = mockQueue.getCompleteOrder();
                    log.info("返回订单处理结果: " + order);
                    deferredResultHolder.getMap().get(order).setResult("place order success");

                    // 因为不是真正的消息队列,不会被消费掉
                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
