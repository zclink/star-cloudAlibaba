package com.gupaoedu.sca.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSentinelController {


    //不要使用@GetMapping("/test")  test已经被占用了 post方法可以
    @GetMapping("/helloFlow")
    //sentinel 会默认给所有http端点提供限流埋点，这里也可以不加注解，加的话不要跟上面的路径名一样
    //不支持private方法
    //https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81
    @SentinelResource(value = "helloFlow")
    public String aaa() {

        return "hello sentinel !";
    }

    @GetMapping("/helloDegrade")
    @SentinelResource(value = "helloDegrade",blockHandler = "blockHandlerTest")
    public String degrade() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello degrade !";
    }

    //控制台的方式不与本地SPI扩展和数据源模式冲突
    @GetMapping("/helloDashboard")
    @SentinelResource(value = "helloDashboard",blockHandler = "blockHandlerTest")
    public String helloDashboard() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello dashboard !";
    }

    //用数据源的模式与本地SPI冲突  SPI优先 使用数据源就不能使用SPI 想测数据源可以将resource中的那个SPI扩展文件换个路径让SPI失效
    //要想同时生效需要再SPI里去注册数据源
    @GetMapping("/hellowDatasource")
    @SentinelResource(value = "hellowDatasource",blockHandler = "blockHandlerTest")
    public String hellowDatasource() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello datasource !";
    }



    //返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException
    public String blockHandlerTest(BlockException e) {
        System.out.println("降级 | " + e);
        return "降级 | " + e;
    }


}
