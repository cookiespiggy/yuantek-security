1. springboot默认如何处理异常
    BasicErrorController 默认做错误处理的控制器
    浏览器访问的错误页面可以通过自定义404页面
    客户端自定义异常可以通过@ControllerAdvice注解来捕获我们自定义的异常
2. Restful API 的拦截
    Filter 是j2ee规范提供的,它并不了解框架,所以不知道是什么方法去处理请求
    Interceptor 是spring提供的,但拿不到参数的值
        为什么拿不到? 看一下源码
            DispatcherServlet
                doService
                    doDispatch
                        if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                        	return;
                        }
                        // Actually invoke the handler.
                        // 此处才开始处理请求的参数,所以之前是拿不到参数的值的
                        mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
                         
    Aspect (切片)
        所谓切片就是一个类,要想让一个类成为切片,需要声明两个东西
            1. 切入点(我一般用注解声明)
                1. 在哪些方法上起作用
                2. 在什么时候起作用 是由你使用的注解决定的
            2. 增强(所谓的增强,就是一个方法)
                1. 在切片起作用的时候,所要执行的业务逻辑
    
3. 拦截顺序
    从外到里
        Filter -> Interceptor -> ControllerAdvice -> Aspect -> Controller
   抛出异常的顺序是从里到外的
4. 异步处理REST服务
    
    