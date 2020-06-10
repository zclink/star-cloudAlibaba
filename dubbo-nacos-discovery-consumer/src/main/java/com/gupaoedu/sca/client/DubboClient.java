package com.gupaoedu.sca.client;

import com.gupaoedu.sca.service.HelloWorldService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class DubboClient {

    //配置文档
    //http://dubbo.apache.org/zh-cn/docs/user/references/xml/dubbo-reference.html
    @Reference(check = false,timeout = 3000,cluster = "failfast")
    private HelloWorldService helloWorldService;


    public String helloWorld(String msg) {
        return helloWorldService.helloWorld(msg);
    }
}
